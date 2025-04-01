package maquina_snack;

import java.util.ArrayList;
import java.util.List;

public class Snacks {
    private static final List<Snack> snacks;

    //Bloque de tipo estático inicalizador
    //
    // para iniciar elementos estáticos en vez de usar un constructor
    //Cuando se cargue la clase se van a crear las elementos estáticos y se van a inicializar al ejecutar este
    //bloque estático.

    static{
        //Ya que estamos en un bloque estático solo usamos el nombre del atributo snack, en vez de Snacks.snacks.
        //Como el atributo era Final, no se va a poder cambiar la lista pero si su contenido.

        snacks = new ArrayList<>();
        snacks.add(new Snack("Papas", 70)); //Se usa la instrucción new pq estamos agregando un nuevo objeto snack a la lista.
        snacks.add(new Snack("Refresco", 50));
        snacks.add(new Snack("Sandwich", 120));
    }

    //Definimos ahora los métodos estáticos de la clase

    public static void agregarSnack(Snack snack){
        snacks.add(snack);
    }

    public static void mostrarSnack(){
        //Muestra el inventario disponible de Snacks
        var inventarioSnacks = "";
        for(var snack: snacks){

            // Usamos el método toString() que definimos en la clase Snack, que indica que va a imprimir de cada objeto como un String.

            /*Esto es lo que retornaba el método toString() de la clase Snack

                    return "maquina_snack.Snack{" +
                                    "idSnack=" + idSnack +
                                    ", nombre='" + nombre + '\\'' +
                                    ", precio=" + precio +
                                    '}';
                    */
            inventarioSnacks += snack.toString() + "\n";


        }
        System.out.println("-------------Snack en el inventario--------");
        System.out.println(inventarioSnacks);
    }

    public static List<Snack> getSnacks(){
        return snacks;
    }


}
