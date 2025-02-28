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

    void info() {
        if (myFile.exists()) {
            System.out.println("\nNombre: " + myFile.getName());
            System.out.println("Path: " + myFile.getPath());
            System.out.println("Absoluta: " + myFile.getAbsolutePath());
            System.out.println("Bytes: " + myFile.length());
            System.out.println("Modificado en: " + new Date(myFile.lastModified()));
            System.out.println("Padre: " + myFile.getAbsoluteFile().getParentFile().getName());
            if (myFile.isFile()) {
                System.out.println("ES FILE");
            } else if (myFile.isDirectory()) {
                System.out.println("ES FOLDER");
            }
        } else {
            System.out.println("NO EXISTE!");
        }
    }

    boolean crearArchivo() throws IOException {
        return myFile.mkdirs();
    }

    boolean crearFolder() {
        return myFile.mkdirs();
    }

    boolean borrar() {
        return myFile.delete();
    }

    void dir() {
        if (myFile.isDirectory()) {
            System.out.println("Folder: " + myFile.getName());
            int dirs = 0, files = 0, bytes = 0;

            for (File child : myFile.listFiles()) {
                System.out.print(new Date(child.lastModified()));
                if (child.isDirectory()) {
                    System.out.print("\t<DIR>\t");
                    dirs++;
                }
                if (child.isFile()) {
                    System.out.print("\t     \t");
                    System.out.print(child.length());
                    files++;
                    bytes += child.length();
                }

                System.out.println("\t" + child.getName());
            }
            System.out.println("(" + files + ") files y (" + dirs + ") dirs");
            System.out.println("bytes: " + bytes);
        } else {
            System.out.println("Accion no permitida");
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
    
      private void regresarCarpeta() {
        try {
            File dirActual = new File(directorioActual);
            File dirPadre = dirActual.getParentFile();
            
            if (dirPadre != null) {
                directorioActual = dirPadre.getCanonicalPath();
                funciones.setFile(directorioActual);
            } else {
                System.out.println("Ya estás en el directorio raíz.");
            }
        } catch (IOException e) {
            System.out.println("Error al regresar de carpeta: " + e.getMessage());
        }
    }

    private void listarDirectorio() {
        funciones.setFile(directorioActual);
        funciones.dir();
    }

    private void mostrarFecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Fecha actual: " + sdf.format(new Date()));
    }

    private void mostrarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("Hora actual: " + sdf.format(new Date()));
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

}
