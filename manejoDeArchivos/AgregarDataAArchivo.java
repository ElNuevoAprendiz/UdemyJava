package manejoDeArchivos;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class AgregarDataAArchivo {
    public static void main(String[] args) {
       boolean anexar = false;//Chequea que el archivo exista, para no sobre escribirlo sino anexar info.
       var nombreArchivo = "mi_archivo.txt";
       var archivo = new File(nombreArchivo);

       try{
           //Revisa q exista el acrhivo

           anexar = archivo.exists();

           // Si existe cambia el valor de la variable anexar a verdadero
           //asi se agrega info.
           var salida = new PrintWriter(new FileWriter(archivo, anexar));
           var nuevoContenido = "Nuevo\ncontenido";
           salida.println(nuevoContenido);
           salida.close();



       } catch (Exception e) {
           System.out.println("Error al imprimir el archivo" + e.getMessage());
       }

    }
}
