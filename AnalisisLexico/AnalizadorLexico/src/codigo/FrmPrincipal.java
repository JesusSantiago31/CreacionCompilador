package codigo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

public class FrmPrincipal extends javax.swing.JFrame {

    public FrmPrincipal() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        txtEntrada = new JTextField();
        btnAnalizar = new CustomButton("Analizar");
        jScrollPane1 = new JScrollPane();
        txtResultado = new JTextArea();
        jButton1 = new CustomButton("Borrar");
        jButton2 = new CustomButton("Salir");

        // Crear un panel para el fondo negro
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE); // Fondo blanco

        // Configurar el JTextField
        txtEntrada.setBackground(Color.WHITE); // Fondo blanco
        txtEntrada.setBorder(BorderFactory.createLineBorder(new Color(63,81,181), 2)); // Borde azul
        txtEntrada.setFont(new Font("Tahoma", 0, 30));

        // Configurar el JTextArea
        txtResultado.setBackground(Color.WHITE); // Fondo blanco
        txtResultado.setBorder(BorderFactory.createLineBorder(new Color(63,81,181), 2)); // Borde azul
        txtResultado.setColumns(20);
        txtResultado.setRows(5);
        jScrollPane1.setViewportView(txtResultado);

        btnAnalizar.setFont(new Font("Tahoma", Font.BOLD, 16));
        jButton1.setFont(new Font("Tahoma", Font.BOLD, 16));
        jButton2.setFont(new Font("Tahoma", Font.BOLD, 16));

        // Agregar ActionListener a los botones
        btnAnalizar.addActionListener(evt -> btnAnalizarActionPerformed(evt));
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));
        jButton2.addActionListener(evt -> jButton2ActionPerformed(evt));

        // Establecer layout del panel superior
        topPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(txtEntrada, gbc);
        
        // Configurar insets para los márgenes de los botones
        gbc.insets = new Insets(0, 10, 0, 10); // Margen horizontal entre botones
        gbc.weightx = 0.0; // No expandir
        gbc.gridx = 1; // Botón Analizar
        topPanel.add(btnAnalizar, gbc);
        
        gbc.gridx = 2; // Botón Borrar
        topPanel.add(jButton1, gbc);
        
        gbc.gridx = 3; // Botón Salir
        topPanel.add(jButton2, gbc);

        // Establecer layout de la ventana principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH); // Panel superior
        getContentPane().add(jScrollPane1, BorderLayout.CENTER); // Panel central con el JTextArea
        getContentPane().add(bottomPanel, BorderLayout.SOUTH); // Panel inferior

        // Aumentar el margen superior y inferior
        topPanel.setPreferredSize(new Dimension(0, 70)); // Aumentar el tamaño del panel superior
        bottomPanel.setPreferredSize(new Dimension(0, 40)); // Aumentar el tamaño del panel inferior

        // Establecer tamaño de la ventana
        setSize(800, 500); // Tamaño más grande de la ventana

        pack();
    }

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Lógica del botón Borrar
        txtEntrada.setText("");
        txtResultado.setText("");
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // Cerrar la aplicación
        System.exit(0);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FrmPrincipal().setVisible(true);
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtEntrada;
    private javax.swing.JTextArea txtResultado;
}

class CustomButton extends JButton {
    private boolean isHovered = false;

    public CustomButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color topColor = isHovered ? new Color(0, 120, 215) : new Color(33, 97, 140);
        Color bottomColor = isHovered ? new Color(0, 85, 170) : new Color(0, 51, 102);

        GradientPaint gradient = new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor);
        g2d.setPaint(gradient);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2d.setColor(new Color(0, 0, 0, 50));
        g2d.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 30, 30);

        super.paintComponent(g2d);
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        // No se dibuja el borde
    }
}
