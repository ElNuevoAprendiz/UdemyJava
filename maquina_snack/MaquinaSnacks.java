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
        while (!salir){
            try{
                var opcion = mostrarMenu(consola);
                salir =  ejecutarOpciones(opcion, consola, productos);

            } catch (Exception e) {
                System.out.println("Ocurrio un error:" + e.getMessage());
            }
            finally {
                System.out.println();//Imprime un alto de linea con cada iteración.
            }
        }


    }

    private static int mostrarMenu(Scanner consola){
        System.out.print("""
                Menu:
                1. Comprar snack
                2. Mostrar ticket
                Agregar Nuevo Snack
                4. Salir
                
                Elige una opción \s""");

        //Leemos y retornamos la opcion elegida por el usuario
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(int opcion, Scanner consola, List<Snack> productos){
        var salir = false;
        switch (opcion){
            case 1 -> comprarSnack(consola, productos);
        }

        return salir;

    };

    private static void comprarSnack(Scanner consola, List<Snack> productos){
        System.out.println("Que snack quieres comprar (id)?");
        var idSnack = Integer.parseInt(consola.nextLine());
        //Validadr q el snack exista en la lista
        var snackEncontrado = False;
        for(var snack: Snacks.getSnacks()){

        }


    }
}

