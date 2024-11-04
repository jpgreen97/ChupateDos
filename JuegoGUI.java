import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class JuegoGUI extends JFrame {
    private ChupateDosGUI juego;
    private JPanel panelMesa;
    private JLabel cartaEnMesaLabel;
    private JPanel panelJugador;
    private JButton botonJugarCarta;
    private JButton botonRobarCarta;
    private JPanel panelJugadores;
    private ArrayList<JLabel> etiquetasJugadores;

    public JuegoGUI() {
        setTitle("Chúpate Dos - Juego de Cartas");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        inicializarComponentes();
        juego = new ChupateDosGUI(this);
        inicializarPanelJugadores(juego.getNombresJugadores());

        botonJugarCarta.addActionListener(e -> juego.jugarCarta());
        botonRobarCarta.addActionListener(e -> juego.robarCarta());

        setVisible(true);

        SwingUtilities.invokeLater(() -> {
            if (juego.getCartaEnMesa() != null && juego.getJugadorActual() != null) {
                actualizarInterfaz(juego.getCartaEnMesa(), juego.getJugadorActual().getMano(), juego.getIndiceJugadorActual());
            }
        });
    }

    private void inicializarComponentes() {
        panelMesa = new JPanel();
        cartaEnMesaLabel = new JLabel("Carta en mesa: ");
        panelMesa.add(cartaEnMesaLabel);
        add(panelMesa, BorderLayout.NORTH);

        panelJugador = new JPanel(new FlowLayout());
        add(panelJugador, BorderLayout.CENTER);

        JPanel panelAcciones = new JPanel();
        botonJugarCarta = new JButton("Jugar Carta");
        botonRobarCarta = new JButton("Robar Carta");
        panelAcciones.add(botonJugarCarta);
        panelAcciones.add(botonRobarCarta);
        add(panelAcciones, BorderLayout.SOUTH);

        panelJugadores = new JPanel(new GridLayout(0, 1));
        etiquetasJugadores = new ArrayList<>();
        add(panelJugadores, BorderLayout.WEST);
    }

    public void actualizarInterfaz(Carta cartaEnMesa, ArrayList<Carta> cartasJugador, int jugadorActual) {
        if (cartaEnMesaLabel != null) {
            cartaEnMesaLabel.setIcon(cargarImagenCarta(cartaEnMesa));
        }

        panelJugador.removeAll();
        cartasJugador.forEach(carta -> {
            JButton botonCarta = new JButton();
            botonCarta.setIcon(cargarImagenCarta(carta));
            botonCarta.addActionListener(e -> juego.seleccionarCarta(carta));
            panelJugador.add(botonCarta);
        });
        panelJugador.revalidate();
        panelJugador.repaint();

        for (int i = 0; i < etiquetasJugadores.size(); i++) {
            if (i == jugadorActual) {
                etiquetasJugadores.get(i).setForeground(Color.RED);
                etiquetasJugadores.get(i).setFont(new Font("Arial", Font.BOLD, 14));
            } else {
                etiquetasJugadores.get(i).setForeground(Color.BLACK);
                etiquetasJugadores.get(i).setFont(new Font("Arial", Font.PLAIN, 12));
            }
        }
    }

    private ImageIcon cargarImagenCarta(Carta carta) {
        String nombreArchivo = carta.getPalo().toLowerCase() + "_" + carta.getValor() + ".png";
        String rutaArchivo = "/cartas/" + nombreArchivo;

        java.net.URL imageUrl = getClass().getResource(rutaArchivo);
        if (imageUrl == null) {
            System.out.println("No se pudo encontrar la imagen en la ruta: " + rutaArchivo);
            return new ImageIcon(getClass().getResource("/cartas/back.png"));
        }

        ImageIcon icono = new ImageIcon(imageUrl);
        Image imagenEscalada = icono.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    public void inicializarPanelJugadores(ArrayList<String> nombresJugadores) {
        etiquetasJugadores.clear();
        panelJugadores.removeAll();

        // forEach para agregar etiquetas de jugadores
        nombresJugadores.forEach(nombre -> {
            JLabel etiquetaJugador = new JLabel(nombre);
            etiquetasJugadores.add(etiquetaJugador);
            panelJugadores.add(etiquetaJugador);
        });

        panelJugadores.revalidate();
        panelJugadores.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(JuegoGUI::new);
    }
}
