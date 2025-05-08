package maquina_snacks_archivos.servicio;

import maquina_snacks_archivos.dominio.Snack;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ServicioSnacksArchivos implements IServicioSnacks{
    private final String NOMBRE_ARCHIVO = "snacks.txt";
    // Crear la lista de snacks
    private List<Snack> snacks = new ArrayList<>();

    // Constructor clase
    public ServicioSnacksArchivos(){
        //Creamos el archivo si no existe
        var archivo = new File(NOMBRE_ARCHIVO);
        var existe = false;
        try{
            existe = archivo.exists();
            if(existe){
                //this.snacks = obtenerSnacks();
            }
            else{ // Creamos el archivo
                var salida = new PrintWriter(new FileWriter(archivo));
                salida.close();//Guarda el archivo en disco
                System.out.println("Se ha creado el archivo");
            }
        }catch(Exception e){
            System.out.println("Error al crear el archivo: " + e.getMessage());
        }
        // Si no existe, cargamos algunos snacks iniciales
        if(!existe)
            cargarSnacksIniciales();
    }
    private  void  cargarSnacksIniciales(){
        this.agregarSnack(new Snack("Papas", 70));
        this.agregarSnack(new Snack("Refresco", 50));
        this.agregarSnack(new Snack("Sandwich", 120));

    }
    @Override
    public void agregarSnack(Snack snack) {
        // Agregamos el nuevo snack,
        // Paso 1 Se guarda el snack a la lista en memoria

        //Recordar que snacks es la lista que se definio más arriba y que va a ir recibiendo los snack y
        //guardandolos en memoria y luego serán volcados al archivo

        this.snacks.add(snack);

        //Paso 2. Guardamos el nuevo snack en el archivo.

        this.agregarSnackArchivo(snack);


    }

    private void agregarSnackArchivo(Snack snack){

    }

    @Override
    public void mostrarSnacks() {

    }

    @Override
    public List<Snack> getSnacks() {
        return null;
    }
}
