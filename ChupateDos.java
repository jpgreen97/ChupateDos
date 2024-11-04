import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ChupateDos {
    private Baraja baraja;
    private ArrayList<Jugador> jugadores;
    private int jugadorActual;
    private Carta cartaEnMesa;
    private boolean direccionNormal = true;

    public ChupateDos() {
        this.baraja = new Baraja();
        this.jugadores = new ArrayList<>();
        inicializarJugadores();
        if (!jugadores.isEmpty()) {
            this.cartaEnMesa = baraja.repartirCarta();
        }
    }

    public void inicializarJugadores() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de jugadores (2-4): ");
        int numJugadores = scanner.nextInt();

        while (numJugadores < 2 || numJugadores > 4) {
            System.out.println("Número de jugadores no válido. Ingrese un número entre 2 y 4:");
            numJugadores = scanner.nextInt();
        }

        // IntStream para simplificar la inicialización de jugadores
        IntStream.range(0, numJugadores).forEach(i -> {
            Jugador jugador = new Jugador("Jugador " + (i + 1));
            jugador.tomarCartas(baraja.repartirManoInicial());
            jugadores.add(jugador);
        });
    }

    public Carta getCartaEnMesa() {
        return cartaEnMesa;
    }

    public Jugador getJugadorActual() {
        if (!jugadores.isEmpty()) {
            return jugadores.get(jugadorActual);
        } else {
            throw new IllegalStateException("La lista de jugadores está vacía.");
        }
    }

    public int getIndiceJugadorActual() {
        return jugadorActual;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public boolean esJugadaValida(Carta carta) {
        return carta.getPalo().equals(cartaEnMesa.getPalo()) || carta.getValor() == cartaEnMesa.getValor();
    }

    public void jugarCarta(Carta carta) {
        if (esJugadaValida(carta)) {
            if (cartaEnMesa != null) {
                baraja.agregarCartaJugada(cartaEnMesa);
            }

            cartaEnMesa = carta;
            getJugadorActual().removerCarta(carta);

            if (carta.getValor() == 1) { // As
                System.out.println("¡As! " + getJugadorActual().getNombre() + " juega otra vez.");
                aplicarReglaEspecial(carta);
            } else if (carta.getValor() == 10 && jugadores.size() == 2) { // Sota en juego de 2 jugadores
                System.out.println("¡Sota! " + getJugadorActual().getNombre() + " salta el turno del otro jugador y juega otra vez.");
                aplicarReglaEspecial(carta);
            } else {
                aplicarReglaEspecial(carta);
                if (!getJugadorActual().estaSinCartas()) {
                    cambiarTurno();
                }
            }

            if (getJugadorActual().estaSinCartas()) {
                System.out.println(getJugadorActual().getNombre() + " ha ganado el juego!");
                System.exit(0);
            }
        } else {
            System.out.println("Jugada inválida. Intenta otra carta.");
        }
    }

    public void robarCarta(Jugador jugador) {
        Carta nuevaCarta = baraja.repartirCarta();
        if (nuevaCarta != null) {
            jugador.tomarCarta(nuevaCarta);
            cambiarTurno();
        } else {
            System.out.println("La baraja está vacía y no se puede robar una carta.");
        }
    }

    private void aplicarReglaEspecial(Carta carta) {
        int siguienteJugador;
        switch (carta.getValor()) {
            case 1:
                System.out.println("¡As! " + getJugadorActual().getNombre() + " juega otra vez.");
                break;
            case 2:
                siguienteJugador = (jugadorActual + 1) % jugadores.size();
                IntStream.range(0, 2).forEach(i -> jugadores.get(siguienteJugador).tomarCarta(baraja.repartirCarta()));
                System.out.println(jugadores.get(siguienteJugador).getNombre() + " toma dos cartas.");
                break;
            case 3:
                siguienteJugador = (jugadorActual + 1) % jugadores.size();
                IntStream.range(0, 4).forEach(i -> jugadores.get(siguienteJugador).tomarCarta(baraja.repartirCarta()));
                System.out.println(jugadores.get(siguienteJugador).getNombre() + " toma cuatro cartas.");
                break;
            case 10:
                if (jugadores.size() == 2) {
                    System.out.println("¡Sota! " + getJugadorActual().getNombre() + " salta el turno del otro jugador y juega otra vez.");
                } else {
                    direccionNormal = !direccionNormal;
                    cambiarTurno();
                    System.out.println("¡Sota! Se cambia la dirección del juego.");
                }
                break;
            case 12:
                System.out.println("¡Rey! " + getJugadorActual().getNombre() + " puede jugar cualquier carta.");
                seleccionarNuevoPaloParaRey();
                break;
        }
    }

    private void seleccionarNuevoPaloParaRey() {
        String[] palos = {"Oros", "Copas", "Espadas", "Bastos"};
        String nuevoPalo = (String) JOptionPane.showInputDialog(
                null,
                "Selecciona el palo al que deseas cambiar:",
                "Cambio de Palo",
                JOptionPane.QUESTION_MESSAGE,
                null,
                palos,
                palos[0]
        );

        if (nuevoPalo != null) {
            cartaEnMesa = new Carta(nuevoPalo, cartaEnMesa.getValor());
            System.out.println("El jugador ha cambiado el palo a: " + nuevoPalo);
        }
    }

    private void cambiarTurno() {
        jugadorActual = direccionNormal
                ? (jugadorActual + 1) % jugadores.size()
                : (jugadorActual - 1 + jugadores.size()) % jugadores.size();
    }
}
