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
            var entrada = new BufferedReader(new FileReader(archivo));//BufferedReader envuelve a FileReader, ya que le permite leer varios caracteres juntos y dejarlo en el buffer ya que filereader solo lee un caracter a la vez

            //Leemos el archivo linea a linea con el metodo readLine de BufferedReader

            var linea = entrada.readLine();

            while (linea != null){
                System.out.println(linea);
                //antes de terminar el ciclo nos movemos a la siguiente linea

                linea = entrada.readLine();

            }

            entrada.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo " + e);

        }

    }
}
