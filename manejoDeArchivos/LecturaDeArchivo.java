package manejoDeArchivos;

import java.io.*;

public class LecturaDeArchivo {
    public static void main(String[] args) {
        var nombreArchivo ="mi_archivo.txt";
        // Usamos el objeto File para apuntar al archivo con el que vamos a trabajar (indica el camino a el)
        var archivo = new File(nombreArchivo); // no hy camino pq está en la misma carpeta.
        try{

            System.out.println("Contenido del archivo");
            //Abrimos el archivo para lectura
            var entrada = new BufferedReader(new FileReader(archivo));

        } catch (Exception e) {
            System.out.println("Error al leer el archivo " + e);

        }
    }
}
