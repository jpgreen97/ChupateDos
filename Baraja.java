import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

public class Baraja {
    private ArrayList<Carta> cartas;
    private ArrayList<Carta> cartasJugadas; // Lista para almacenar las cartas ya jugadas

    public Baraja() {
        cartas = new ArrayList<>();
        cartasJugadas = new ArrayList<>();
        String[] palos = {"Oros", "Copas", "Espadas", "Bastos"};

        // Uso de lambdas y Streams para inicializar la baraja
        for (String palo : palos) {
            IntStream.rangeClosed(1, 12).forEach(valor -> cartas.add(new Carta(palo, valor)));
        }

        Collections.shuffle(cartas);
    }

    public Carta repartirCarta() {
        if (cartas.isEmpty()) {
            reiniciarBaraja(); // Reiniciar la baraja cuando esté vacía
        }

        if (!cartas.isEmpty()) {
            return cartas.remove(0);
        }
        return null;
    }

    public ArrayList<Carta> repartirManoInicial() {
        ArrayList<Carta> mano = new ArrayList<>();

        IntStream.range(0, 5).forEach(i -> mano.add(repartirCarta()));

        return mano;
    }

    public void agregarCartaJugada(Carta carta) {
        cartasJugadas.add(carta);
    }

    private void reiniciarBaraja() {
        System.out.println("La baraja está vacía. Recogiendo cartas jugadas y mezclando de nuevo.");
        cartas.addAll(cartasJugadas);
        cartasJugadas.clear();
        Collections.shuffle(cartas);
    }
}
