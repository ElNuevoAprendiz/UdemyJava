package maquina_snack_archivos.servicio;

import maquina_snack_archivos.dominio.Snack;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServicioSnacksArchivos implements IServicioSnacks{

    private final String NOMBRE_ARCHIVO ="snack.txt"; //COnstante que tiene el nombre del archivo, que tendrá
    //el inventario de snacks disponible en la aplicación

    //Ahora creamosla lista de snacks
    private  List<Snack> snacks =new ArrayList<>();

    //Creamos el constructor de la clase
    public ServicioSnacksArchivos(){
        //Creamos el archivo si no existe
        var archivo = new File(NOMBRE_ARCHIVO);
        var existe = false;
        try{
            existe =archivo.exists();
            if(existe){
                //this.snacks = obtenerSnacks();
            }
            else{// Si no existe, creamos el archivo que va a tener nuestros snacks.
                var salida = new PrintWriter(new FileWriter(archivo));
                salida.close(); //se guarda el archivo en disco pq sino solo se encuentra en memoria
                System.out.println("Se ha ccrreado el archivo");
            }
        } catch (Exception e) {
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
        //Si no existe, cargamos algunos snack iniciales
        if(!existe)
            cargarSnacksIniciales();
    }

    private void cargarSnacksIniciales(){

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
