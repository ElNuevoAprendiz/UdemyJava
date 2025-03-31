package maquina_snack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaquinaSnacks {
    public static void main(String[] args) {
        maquinaSnacks();

    }
    public static void maquinaSnacks(){
        var salir = false;
        var consola = new Scanner(System.in);
        //Vamos a crear la lista de productos de tipo Snacks, va a almacenar cada uno de los snack que vamos comprando
        List<Snack> productos = new ArrayList<>();
        System.out.println("***Máquina de Snack***");
        Snacks.mostrarSnack(); //Mostramos el inventario

        // y luego escribimos un bloque estático para inicializar
        // los atributos estáticos.


    }
}
