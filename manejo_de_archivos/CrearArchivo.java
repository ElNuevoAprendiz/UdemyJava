package manejo_de_archivos;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class CrearArchivo {
    public static void main(String[] args) {
        var nombreArchivo = "mi_archivo.txt"; //Nombre del archivo que vamos a crear
        var archivo = new File(nombreArchivo);//Con esta instrucción se crea el objeto FILE;
        if(archivo.exists()){//Consultamos si el archivo existe
            System.out.println("El archivo ya existe");
        }
        else{
            //creamos el archivo
            var salida = new PrintWriter((new FileWriter(archivo)));
        }
    }
}
