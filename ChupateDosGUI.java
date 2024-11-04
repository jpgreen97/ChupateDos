import javax.swing.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChupateDosGUI {
    private ChupateDos juego;
    private JuegoGUI interfaz;
    private Jugador jugadorActual;
    private Carta cartaSeleccionada;

    public ChupateDosGUI(JuegoGUI interfaz) {
        this.juego = new ChupateDos();
        this.interfaz = interfaz;
        this.jugadorActual = juego.getJugadorActual();
        actualizarInterfaz();
    }

    public ArrayList<String> getNombresJugadores() {
        //lambdas y streams para mapear los nombres de los jugadores
        return juego.getJugadores().stream()
                .map(Jugador::getNombre)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void actualizarInterfaz() {
        this.jugadorActual = juego.getJugadorActual();
        int indiceJugadorActual = juego.getIndiceJugadorActual();
        interfaz.actualizarInterfaz(juego.getCartaEnMesa(), jugadorActual.getMano(), indiceJugadorActual);
    }

    public Carta getCartaEnMesa() {
        return juego.getCartaEnMesa();
    }

    public Jugador getJugadorActual() {
        return juego.getJugadorActual();
    }

    public int getIndiceJugadorActual() {
        return juego.getIndiceJugadorActual();
    }

    public void jugarCarta() {
        if (cartaSeleccionada != null && juego.esJugadaValida(cartaSeleccionada)) {
            juego.jugarCarta(cartaSeleccionada);
            cartaSeleccionada = null;
            actualizarInterfaz();
        } else {
            JOptionPane.showMessageDialog(interfaz, "Selecciona una carta válida.");
        }
    }

    public void robarCarta() {
        juego.robarCarta(jugadorActual);
        actualizarInterfaz();
    }

    public void seleccionarCarta(Carta carta) {
        cartaSeleccionada = carta;
    }
}
