/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab6_Programacion_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import javax.swing.JTextArea;

/**
 *
 * @author DELL
 */
public class FuncionesCMD {

    private String directorioActual;
    private File myFile = null;

    public String getDirectorioActual() {
        return directorioActual;
    }

    
    
    public void setFile(String direccion) {
        System.out.println("Recibido: " + direccion);

        if (direccion == null || direccion.trim().isEmpty()) {
            System.out.println("Error: Dirección no válida");
            return;
        }

        myFile = new File(direccion); // Se asigna la ruta sin importar si existe o no
        directorioActual = myFile.getAbsolutePath(); // Se guarda la ruta como actual
        System.out.println("Ruta establecida: " + directorioActual);
    }

    public String info() {
        if (myFile != null && myFile.exists()) {
            try {
                StringBuilder info = new StringBuilder();
                info.append("\nNombre: ").append(myFile.getName())
                        .append("\nPath: ").append(myFile.getPath())
                        .append("\nAbsoluta: ").append(myFile.getAbsolutePath())
                        .append("\nBytes: ").append(myFile.length())
                        .append("\nModificado en: ").append(new Date(myFile.lastModified()))
                        .append("\nPadre: ").append(myFile.getAbsoluteFile().getParent());

                if (myFile.isFile()) {
                    info.append("\nES FILE");
                } else if (myFile.isDirectory()) {
                    info.append("\nES FOLDER");
                }
                return info.toString();
            } catch (Exception e) {
                return "Error obteniendo información: " + e.getMessage();
            }
        }
        return "NO EXISTE!";
    }

    public boolean crearArchivo() {
        if (myFile == null) {
            System.out.println("Error: No se ha especificado un archivo.");
            return false;
        }

        try {
            if (!myFile.exists()) {  // Solo intentamos crear si no existe
                return myFile.createNewFile();
            } else {
                System.out.println("Error: El archivo ya existe.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear archivo: " + e.getMessage());
        }
        return false;
    }
    
    public void retrocederRuta() {
        if (myFile == null) {
            System.out.println("Error: No hay una ruta establecida.");
            return;
        }

        File padre = myFile.getParentFile(); // Obtiene el directorio padre

        if (padre != null) {
            myFile = padre; // Actualiza la referencia a la ruta padre
            directorioActual = myFile.getAbsolutePath();
            System.out.println("Ruta actualizada a: " + directorioActual);
        } else {
            System.out.println("Error: No se puede retroceder más.");
        }
    }
    
    public boolean crearFolder() {
        if (myFile == null) {
            System.out.println("Error: No se ha especificado una carpeta.");
            return false;
        }

        if (!myFile.exists()) { // Solo creamos si no existe
            return myFile.mkdirs();
        } else {
            System.out.println("Error: La carpeta ya existe.");
        }
        return false;
    }

    public boolean borrar() {
        if (myFile == null || !myFile.exists()) {
            System.out.println("Error: No se ha especificado un archivo o carpeta válida.");
            return false;
        }

        return eliminarRecursivo(myFile);
    }

    private boolean eliminarRecursivo(File file) {
        if (file.isDirectory()) {
            File[] archivos = file.listFiles();
            if (archivos != null) {
                for (File archivo : archivos) {
                    eliminarRecursivo(archivo); // Llamada recursiva para borrar contenido
                }
            }
        }
        return file.delete(); // Elimina el archivo o carpeta vacía
    }

    public String dir() {
        if (myFile != null && myFile.isDirectory()) {
            try {
                StringBuilder info = new StringBuilder();
                File[] archivos = myFile.listFiles();
                if (archivos != null) {
                    int dirs = 0, files = 0;
                    long bytes = 0;
                    for (File child : archivos) {
                        info.append(new Date(child.lastModified()));
                        if (child.isDirectory()) {
                            info.append("\t<DIR>\t");
                            dirs++;
                        } else {
                            info.append("\t     \t").append(child.length());
                            files++;
                            bytes += child.length();
                        }
                        info.append("\t").append(child.getName()).append("\n");
                    }
                    info.append("(").append(files).append(") files y (").append(dirs).append(") dirs\n");
                    info.append("bytes: ").append(bytes);
                }
                return info.toString();
            } catch (Exception e) {
                return "Error al listar directorio: " + e.getMessage();
            }
        }
        return "Acción no permitida";
    }

    public String escribir(String direccion, String texto) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return "ERROR: Dirección del archivo no válida.";
        }

        File archivo = new File(direccion);

        try (FileWriter writer = new FileWriter(archivo, true); BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            bufferedWriter.write(texto);
            bufferedWriter.newLine();  // Añade un salto de línea después del texto
            bufferedWriter.flush();     // Asegura que los datos se guarden

            return "TEXTO INGRESADO DE FORMA CORRECTA";
        } catch (IOException e) {
            return "ERROR. NO SE PUDO INGRESAR EL TEXTO: " + e.getMessage();
        }
    }

    public String imprimirmensaje(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return "ERROR: Dirección del archivo no válida.";
        }

        File archivo = new File(direccion);
        if (!archivo.exists() || !archivo.isFile()) {
            return "ERROR: El archivo no existe o no es un archivo válido.";
        }

        StringBuilder mensaje = new StringBuilder();
        try (FileReader lector = new FileReader(archivo); BufferedReader br = new BufferedReader(lector)) {

            String linea;
            while ((linea = br.readLine()) != null) {
                mensaje.append(linea).append(System.lineSeparator());
            }
        } catch (IOException e) {
            return "Error al leer el archivo: " + e.getMessage();
        }
        return mensaje.toString();
    }

    public String obtenerFecha() {
        return "\nFecha actual: " + new SimpleDateFormat("dd/MM/yyyy").format(new Date());
    }

    public String obtenerHora() {
        return "\nHora actual: " + new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    public void reconocerComando(String comando, JTextArea a) {
        if (comando == null || comando.trim().isEmpty()) {
            a.append("\nError: Comando vacío o inválido.");
            return;
        }

        String[] partes = comando.split(" ", 2);
        String cmd = partes[0];
        String argumento = partes.length > 1 ? partes[1] : "";

        switch (cmd) {
            case "Mkdir":
                setFile(argumento);
                a.append(crearFolder() ? "\nSe creó la carpeta" : "\nError al crear la carpeta");
                break;
            case "Mfile":
                setFile(argumento);
                a.append(crearArchivo() ? "\nSe creó el archivo" : "\nError al crear el archivo");
                break;
            case "Rm":
                setFile(argumento);
                a.append(borrar() ? "\nArchivo o carpeta eliminados" : "\nError al eliminar");
                break;
            case "Cd":
                setFile(argumento);
                directorioActual = myFile.getAbsolutePath(); // Se guarda la ruta sin validar si existe
                a.append("\nDirectorio cambiado a: " + directorioActual);
                break;
            case "Dir":
                a.append("\n" + dir());
                break;
            case "Date":
                a.append(obtenerFecha());
                break;
            case "Time":
                a.append(obtenerHora());
                break;
            case "wr":
                if (myFile == null || !myFile.exists() || myFile.isDirectory()) {
                    a.append("\nError: No hay un archivo válido seleccionado. Use 'Cd' para elegir uno.");
                } else {
                    String textoAEscribir = argumento.trim();
                    if (textoAEscribir.isEmpty()) {
                        a.append("\nError: Debe proporcionar texto para escribir en el archivo.");
                    } else {
                        a.append("\n" + escribir(myFile.getAbsolutePath(), textoAEscribir));
                    }
                }
                break;
            case "rd":
                if (myFile == null || !myFile.exists() || myFile.isDirectory()) {
                    a.append("\nError: No hay un archivo válido seleccionado. Use 'Cd' para elegir uno.");
                } else {
                    a.append("\n" + imprimirmensaje(myFile.getAbsolutePath()));
                }
                break;
            case "...":
                retrocederRuta();
                a.append("\nRuta retrocedida a: " + directorioActual);
                break;
            default:
                a.append("\nComando no reconocido");
                break;
        }
    }
}
