package codigo;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class FrmPrincipal extends JFrame {

    private JTextField txtEntrada;
    private JTextArea txtResultado;

    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Panel principal con layout BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30)); // Tema oscuro

        // Barra superior (header)
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topBar.setBackground(new Color(45, 45, 45)); // Fondo negro
        topBar.setPreferredSize(new Dimension(0, 50));

        JButton btnAnalizar = new JButton("Analizar");
        JButton btnBorrar = new JButton("Borrar");
        JButton btnSalir = new JButton("Salir");
        styleButton(btnAnalizar);
        styleButton(btnBorrar);
        styleButton(btnSalir);

        // Añadiendo eventos a los botones
        btnAnalizar.addActionListener(evt -> btnAnalizarActionPerformed(evt));
        btnBorrar.addActionListener(evt -> borrarTexto());
        btnSalir.addActionListener(evt -> salirAplicacion());

        topBar.add(btnAnalizar);
        topBar.add(btnBorrar);
        topBar.add(btnSalir);

        // Panel lateral izquierdo (decorativo)
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(45, 45, 45));
        sidePanel.setPreferredSize(new Dimension(50, 0)); // Ancho del panel izquierdo

        // Panel central (contenedor para cuadros de texto)
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(new Color(30, 30, 30));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        txtEntrada = new JTextField();
        styleTextField(txtEntrada);

        txtResultado = new JTextArea();
        txtResultado.setRows(5);
        txtResultado.setEditable(false);
        styleTextArea(txtResultado);

        JScrollPane scrollResultado = new JScrollPane(txtResultado);
        scrollResultado.setBorder(BorderFactory.createEmptyBorder()); // Sin bordes adicionales

        centerPanel.add(txtEntrada);
        centerPanel.add(scrollResultado);

        // Panel inferior (margin bottom decorativo)
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(new Color(45, 45, 45));
        bottomBar.setPreferredSize(new Dimension(0, 50));

        // Añadir componentes al panel principal
        mainPanel.add(topBar, BorderLayout.NORTH);
        mainPanel.add(sidePanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomBar, BorderLayout.SOUTH);

        // Configurar la ventana principal
        this.setContentPane(mainPanel);
        this.setSize(900, 650); // Tamaño más grande
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void styleButton(JButton button) {
        button.setFocusPainted(false);
        button.setBackground(new Color(70, 70, 70)); // Fondo gris oscuro
        button.setForeground(Color.WHITE); // Texto blanco
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleTextField(JTextField textField) {
        textField.setBackground(new Color(40, 40, 40)); // Fondo gris oscuro
        textField.setForeground(Color.WHITE); // Texto blanco
        textField.setFont(new Font("Consolas", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(75, 75, 75), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void styleTextArea(JTextArea textArea) {
        textArea.setBackground(new Color(40, 40, 40)); // Fondo gris oscuro
        textArea.setForeground(Color.WHITE); // Texto blanco
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(75, 75, 75), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    // Métodos de funcionalidad
    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {
        // Lógica del botón Analizar
        File archivo = new File("archivo.txt");
        PrintWriter escribir;
        try {
            escribir = new PrintWriter(archivo);
            escribir.print(txtEntrada.getText());
            escribir.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        try {
            Reader lector = new BufferedReader(new FileReader("archivo.txt"));
            Lexer lexer = new Lexer(lector);
            String resultado = "";
            while (true) {
                Tokens tokens = lexer.yylex();
                if (tokens == null) {
                    resultado += "FIN";
                    txtResultado.setText(resultado);
                    return;
                }
                switch (tokens) {
                    case ERROR:
                        resultado += "Símbolo no definido\n";
                        break;
                    case Identificador: case Numero: case Reservadas:
                        resultado += lexer.lexeme + ": Es un " + tokens + "\n";
                        break;
                    default:
                        resultado += "Token: " + tokens + "\n";
                        break;
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void borrarTexto() {
        txtEntrada.setText("");
        txtResultado.setText("");
    }

    private void salirAplicacion() {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FrmPrincipal().setVisible(true));
    }
}
