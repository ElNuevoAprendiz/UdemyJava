import java.io.Serializable;

public class Snack implements Serializable {
    private static int contadorSnack = 0;
    private int idSnack;
    private String nombre;
    private double precio;

    public Snack(){
        this.idSnack = ++Snack.contadorSnack;
    }

    public Snack(String nombre, double precio){
        //mandamos a llamar al constructor vacio para no tener que repetir lo que hace este
        //para hacerlo, siempre como primera linea escribimos this(), sin nada en el paréntesis
        // ya q este constructor no recibe parámetros. Siempre la llamada a un constructor interno
        // debe ser la primera linea.
        this();
        this.nombre = nombre;
        this.precio = precio;
    }

}
