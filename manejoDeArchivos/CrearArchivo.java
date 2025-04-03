package manejoDeArchivos;

import java.io.File;
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

        if(archivo.exists()){
            System.out.println("El archivo existe");
        }
        else{
            //Creamos el archivo
            var salida = new PrintWriter()
        }
    }
}
