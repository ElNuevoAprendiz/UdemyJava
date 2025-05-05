package maquina_snack_archivos.servicio;

import maquina_snack_archivos.dominio.Snack;

import java.util.ArrayList;
import java.util.List;

public class ServicioSnackArchivos implements IServicioSnacks{

    private final String NOMBRE_ARCHIVO ="snack.txt"; //COnstante que tiene el nombre del archivo, que tendrá
    //el inventario de snacks disponible en la aplicación

    //Ahora creamosla lista de snacks
    private  List<Snack> snack =new ArrayList<>();


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
