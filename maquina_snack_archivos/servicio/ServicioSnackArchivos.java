package maquina_snack_archivos.servicio;

import maquina_snack_archivos.dominio.Snack;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServicioSnackArchivos implements IServicioSnacks{

    private final String NOMBRE_ARCHIVO ="snack.txt"; //COnstante que tiene el nombre del archivo, que tendrá
    //el inventario de snacks disponible en la aplicación

    //Ahora creamosla lista de snacks
    private  List<Snack> snacks =new ArrayList<>();

    //Creamos el constructor de la clase
    public ServicioSnackArchivos(){
        //Creamos el archivo si no existe
        var archivo = new File(NOMBRE_ARCHIVO);
        var existe = false;
        try{
            existe =archivo.exists();
            if(existe){
                this.snacks = obtenerSnacks();
            }
            else{// Si no existe, creamos el archivo que va a tener nuestros snacks.
                var salida = new PrintWriter((new FileWriter(archivo)));


            }
        } catch (Exception e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
    }


    @Override
    public void agregarSnack(Snack snack) {

    }

    @Override
    public void mostrarSnacks() {

    }

    @Override
    public List<Snack> getSnacks() {
        return List.of();
    }
}
