package manejoDeArchivos;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CrearArchivo {
    public static void main(String[] args) {



        ///***********************************************************************************************
        ///Creación de un objeto File:
        ///Un objeto File representa una ruta de acceso a un archivo o directorio en el sistema de archivos.
        ///Para crear un objeto File, puedes proporcionar la ruta como una cadena.
        var nombreArchivo = "mi_archivo.txt";

        var archivo = new File(nombreArchivo);

        try {
            if (archivo.exists()) {
                System.out.println("El archivo existe");
            } else {
                //Creamos el archivo
                var salida = new PrintWriter(new FileWriter(archivo));//Ojo se está usando el constructor
                // FileWriter(String fileName): Crea un FileWriter asociado al archivo especificado.
                // Si el archivo no existe, se crea. Si existe, su contenido se sobrescribe.
                // Se debe usar el otro constructor para agregar datos al archivo.

                salida.close();
                //Cierre del Flujo: Es crucial cerrar el FileWriter una vez que se ha terminado de escribir.
                // Esto asegura que todos los datos en el búfer interno se escriban al disco y que se liberen
                // los recursos del sistema asociados al archivo.
                // Se utiliza el método close() para esto.


                System.out.println("Se creo el archivo");
            }
        } catch(IOException e) {
            System.out.println("Error al crear el archivo " + e.getMessage());
            e.printStackTrace();
        }
    }
}
