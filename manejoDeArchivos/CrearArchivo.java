package manejoDeArchivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CrearArchivo {
    public static void main(String[] args) {

        var nombreArchivo = "mi_archivo.txt";
        /*************************************************************************************************
        Creación de un objeto File:

        Un objeto File representa una ruta de acceso a un archivo o directorio en el sistema de archivos.
        Para crear un objeto File, puedes proporcionar la ruta como una cadena.
        **************************************************************************************************/

        var archivo = new File(nombreArchivo);

        try {
            if (archivo.exists()) {
                System.out.println("El archivo existe");
            } else {
                //Creamos el archivo
                var salida = new PrintWriter(new FileWriter(archivo));
                salida.close();
                System.out.println("Se creo el archivo");
            }
        } catch(IOException e) {
            System.out.println("Error al crear el archivo " + e.getMessage());
            e.printStackTrace();
        }
    }
}
