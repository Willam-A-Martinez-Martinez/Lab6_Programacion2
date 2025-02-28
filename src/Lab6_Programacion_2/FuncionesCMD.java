/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lab6_Programacion_2;

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
    private Scanner scanner;
    private FuncionesCMD funciones;

    private File myFile = null;

    void setFile(String direccion) {
        myFile = new File(direccion);
    }

    public String info() {
        if (myFile.exists()) {
            StringBuilder info = new StringBuilder();
            info.append("\nNombre: ").append(myFile.getName())
                    .append("\nPath: ").append(myFile.getPath())
                    .append("\nAbsoluta: ").append(myFile.getAbsolutePath())
                    .append("\nBytes: ").append(myFile.length())
                    .append("\nModificado en: ").append(new Date(myFile.lastModified()))
                    .append("\nPadre: ").append(myFile.getAbsoluteFile().getParentFile().getName());

            if (myFile.isFile()) {
                info.append("\nES FILE");
            } else if (myFile.isDirectory()) {
                info.append("\nES FOLDER");
            }

            return info.toString();
        } else {
            return "NO EXISTE!";
        }
    }

    boolean crearArchivo() throws IOException {
        return new File(directorioActual).createNewFile();
    }

    boolean crearFolder() {
        return new File(directorioActual).mkdirs();
    }

    boolean borrar() {
        return myFile.delete();
    }

    public String dir() {
        if (myFile.isDirectory()) {
            StringBuilder info = new StringBuilder();
            info.append("Folder: ").append(myFile.getName()).append("\n");
            int dirs = 0, files = 0, bytes = 0;

            for (File child : myFile.listFiles()) {
                info.append(new Date(child.lastModified()));

                if (child.isDirectory()) {
                    info.append("\t<DIR>\t");
                    dirs++;
                }
                if (child.isFile()) {
                    info.append("\t     \t").append(child.length());
                    files++;
                    bytes += child.length();
                }

                info.append("\t").append(child.getName()).append("\n");
            }

            info.append("(").append(files).append(") files y (").append(dirs).append(") dirs\n");
            info.append("bytes: ").append(bytes);

            return info.toString();
        } else {
            return "Accion no permitida";
        }
    }

    private boolean borrarTodoH(int cont) {
        File[] files = myFile.listFiles();

        if (files == null || cont < 0) {
            return myFile.delete();
        }

        if (files[cont].delete()) {
            return borrarTodoH(cont - 1);
        }

        return false;
    }

    boolean borrarTodo() {
        if (!myFile.exists()) {
            return false;
        }

        File[] files = myFile.listFiles();
        if (files == null || files.length == 0) {
            return myFile.delete();
        }

        return borrarTodoH(files.length - 1) && myFile.delete();
    }

    public String escribir(String direccion, String texto) {
        String mensaje = "";
        try {
            FileWriter xd = new FileWriter(direccion, true);
            xd.write(texto + "\n");
            xd.close();
            mensaje = "TEXTO INGRESADO DE FORMA CORRECTA";
        } catch (IOException e) {
            mensaje = "ERROR. NO SE PUDO INGRESAR EL TEXTO";
        }
        return mensaje;
    }

   

    private static String retrocederRuta(File myFile) {
        String ruta = myFile.getAbsolutePath();
        int indice = ruta.lastIndexOf("/");

        if (indice > 0) {
            String nuevaRuta = ruta.substring(0, indice);
            if (!nuevaRuta.contains("/")) {
                return ruta; 
            }
            return nuevaRuta;
        }

        return ruta;
    }

    public String regresarCarpeta() {
        setFile(retrocederRuta(myFile));
        
        return retrocederRuta(myFile);
    }

    private void listarDirectorio() {
        funciones.setFile(directorioActual);
        funciones.dir();
    }

    private String obtenerFecha() {
        System.out.println("Entro a la funcion de obtener fecha");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "\nFecha actual: " + sdf.format(new Date());
    }

    private String obtenerHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return "\nHora actual: " + sdf.format(new Date());
    }

    public String imprimirmensaje(String direccion) {
        String mensaje = "";
        try {
            FileReader lector = new FileReader(direccion);
            int caracter;

            while ((caracter = lector.read()) != -1) {
                mensaje += (char) caracter;
            }

            lector.close();
            mensaje += "\n";
        } catch (IOException e) {
            mensaje = "Error al leer el archivo: " + e.getMessage();
        }
        return mensaje;
    }

    void reconocerComando(String comando, JTextArea a) {
        String texto = a.getText();
        String[] lineas = texto.split("\\n");

        if (lineas.length > 2) {
            String penultimaLinea = lineas[lineas.length - 1];
            String[] partes = penultimaLinea.split(" ", 2);
            String cmd = partes[0];
            String argumento = partes.length > 1 ? partes[1] : "";

            switch (cmd) {
                case "Mkdir":
                    if (crearFolder()) {
                        a.setText(a.getText() + "\nSe creó la carpeta en " + directorioActual);
                    } else {
                        a.setText(a.getText() + "\nError al crear la carpeta");
                    }
                    break;
                case "Mfile":
                    try {
                        if (crearArchivo()) {
                            a.setText(a.getText() + "\nSe creó el archivo en " + directorioActual);
                        } else {
                            a.setText(a.getText() + "\nError al crear el archivo");
                        }
                    } catch (IOException e) {
                        a.setText(a.getText() + "\nError: " + e.getMessage());
                    }
                    break;
                case "Rm":
                    setFile(argumento);
                    if (borrar()) {
                        a.setText(a.getText() + "\nArchivo o carpeta eliminados");
                    } else {
                        a.setText(a.getText() + "\nError al eliminar");
                    }
                    break;
                case "Cd":
                    setFile(argumento);
                    directorioActual = myFile.getAbsolutePath();
                    a.setText(a.getText() + "\nDirectorio cambiado a: " + directorioActual);
                    break;
                case "...":
                    String nuevaRuta = regresarCarpeta();
                    directorioActual = nuevaRuta;
                    a.setText(a.getText() + "\nDirectorio cambiado a: " + nuevaRuta);
                    break;
                case "Dir":
                    setFile(directorioActual);
                    a.setText(a.getText() + "\n" + dir());
                    break;
                case "Date":
                    a.setText(a.getText() + obtenerFecha());
                    break;
                case "Time":
                    a.setText(a.getText() + obtenerHora());
                    break;
                case "wr":
                    String[] wrParts = argumento.split(" ", 2);
                    if (wrParts.length == 2) {
                        a.setText(a.getText() + "\n" + escribir(wrParts[0], wrParts[1]));
                    } else {
                        a.setText(a.getText() + "\nError: Debe proporcionar archivo y texto");
                    }
                    break;
                case "rd":
                    a.setText(a.getText() + "\n" + imprimirmensaje(argumento));
                    break;
                default:
                    a.setText(a.getText() + "\nComando no reconocido");
                    break;
            }
        }
    }

}
