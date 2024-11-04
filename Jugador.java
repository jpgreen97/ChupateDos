import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private ArrayList<Carta> mano;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Carta> getMano() {
        return mano;
    }

    public void tomarCarta(Carta carta) {
        mano.add(carta);
    }

    public void tomarCartas(ArrayList<Carta> cartas) {
        mano.addAll(cartas);
    }

    public boolean estaSinCartas() {
        return mano.isEmpty();
    }

    public void mostrarMano() {
        System.out.println("Mano de " + nombre + ": " + mano);
    }

    public void removerCarta(Carta carta) {
        mano.remove(carta);
    }
}
