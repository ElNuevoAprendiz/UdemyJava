package manejoDeArchivos;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LecturaDeArchivoCompleto {
    public static void main(String[] args) {
        var nombreArchivo ="mi_archivo.txt";
        try{
            //Leemos todas las listas de una sola vez, el objeto nos devuelve una lista

            List<String> lineas = Files.readAllLines(Paths.get(nombreArchivo));

            System.out.println("Contenido del archivo");
            for(var linea : lineas){
                System.out.println(linea);
            }

        } catch (Exception e) {
            System.out.println("Error al leer el archivo" + e.getMessage());
            e.printStackTrace();

        }
    }
}
