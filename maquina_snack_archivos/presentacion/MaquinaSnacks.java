package maquina_snack_archivos.presentacion;

import maquina_snack_archivos.dominio.Snack;
import maquina_snack_archivos.servicio.IServicioSnacks;
import maquina_snack_archivos.servicio.ServicioSnacksLista;

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
        //creamos el objeto para obtener el servicio de snacks(listas)
        IServicioSnacks servicioSnacks = new ServicioSnacksLista();


        //Vamos a crear la lista de productos de tipo Snacks, va a almacenar cada uno de los snack que vamos comprando
        List<Snack> productos = new ArrayList<>();
        System.out.println("***Máquina de Snack***");
        servicioSnacks.mostrarSnacks(); //Mostramos el inventario de Snacks disponibles
        while (!salir){
            try{
                var opcion = mostrarMenu(consola);
                salir =  ejecutarOpciones(opcion, consola, productos, servicioSnacks);

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
                3. Agregar Nuevo Snack
                4. Salir
                
                Elige una opción \s""");

        //Leemos y retornamos la opcion elegida por el usuario
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(int opcion, Scanner consola, List<Snack> productos){//Se pasa el objeto consola para no tener que definir el objeto nuevamente
        var salir = false;
        switch (opcion){
            case 1 -> comprarSnack(consola, productos);
            case 2 -> mostrarTicket(productos);
            case 3 -> agregarSnack(consola);
            case 4 -> {
                System.out.println("Regresa pronto");
                salir = true;
            }
            default -> System.out.println("**** Opción inválida "  + opcion + ", elija una opción entre 1 y 4 ****");
        }

        return salir;

    }

    private static void comprarSnack(Scanner consola, List<Snack> productos){
        //Se pasa el objeto consola para no tener que definir el objeto nuevamente

        //Recordar que la lista(arraylist) producto va a guardar los productos que el usuario piensa comprar

        System.out.println("Que snack quieres comprar (id)?");
        var idSnack = Integer.parseInt(consola.nextLine());

        //Validadr q el snack exista en la lista
        var snackEncontrado = false;
        for(var snack: servicioSnacks.getSnacks()){//recupera el arraylist con los productos(objetos) existentes con el método getSnacks

            if(idSnack == snack.getIdSnack()){
               //Agregamos el snack a la lista de productos
               productos.add(snack);
                System.out.println("Ok, Snack agregado + snack");
                snackEncontrado = true;
                break;
            }


        }
        if(!snackEncontrado){
            System.out.println("Id de snack no encontrado" + idSnack);
        }


    }

    private static void mostrarTicket(List<Snack> productos){
    var ticket = "*** Ticket de Venta ***";
    var total = 0.0;
    for(var producto: productos){
        ticket += "\n\t-" + producto.getNombre() + " - $" + producto.getPrecio();
        total += producto.getPrecio();
    }
        ticket += "\n\tTotal -> $" + total;
        System.out.println(ticket);

    }

    private static void agregarSnack(Scanner consola){
        //Se pasa el objeto consola para no tener que definir el objeto nuevamente
        System.out.print("Nombre del snack: ");
        var nombre = consola.nextLine();
        System.out.print("Precio del snack: ");
        var precio = Double.parseDouble(consola.nextLine());
        ServicioSnacksLista.agregarSnack(new Snack(nombre, precio));
        System.out.println("Tu snack se ha agregado correctamente");
        ServicioSnacksLista.mostrarSnack();
    }
}

