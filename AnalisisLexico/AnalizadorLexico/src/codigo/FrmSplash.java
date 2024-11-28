import javax.swing.*;
import java.awt.*;

public class FrmSplash{
    public static void main(String[] args) {
        // Create the splash screen
        JFrame splashFrame = new JFrame();
        splashFrame.setUndecorated(true); // No borde ni barra de título
        splashFrame.setSize(500, 300);
        splashFrame.setLocationRelativeTo(null);

        // Create a panel for the splash content
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(30, 30, 30)); // Fondo oscuro
        splashFrame.add(panel);

        // Add a logo or main image
        JLabel logo = new JLabel(new ImageIcon("logo.png")); // Cambia a tu logo o imagen
        logo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(logo, BorderLayout.CENTER);

        // Add a progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true); // Progreso continuo
        progressBar.setBackground(new Color(50, 50, 50));
        progressBar.setForeground(new Color(100, 200, 100)); // Color verde llamativo
        panel.add(progressBar, BorderLayout.SOUTH);

        // Add a title or slogan
        JLabel title = new JLabel("Bienvenido a Mi Aplicación", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        panel.add(title, BorderLayout.NORTH);

        // Show the splash screen
        splashFrame.setVisible(true);

        // Simulate loading process
        try {
            Thread.sleep(3000); // Simula 3 segundos de carga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Close the splash and launch the main application
        splashFrame.dispose();
        showMainApplication();
    }

    private static void showMainApplication() {
        // Main application window
        JFrame mainFrame = new JFrame("Mi Aplicación");
        mainFrame.setSize(800, 600);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JLabel welcome = new JLabel("¡Bienvenido a la Aplicación Principal!", JLabel.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 24));
        mainFrame.add(welcome);

        mainFrame.setVisible(true);
    }
}
