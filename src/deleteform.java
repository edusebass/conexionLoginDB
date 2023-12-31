import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class deleteform {
    private JButton eliminarUsuarioButton;
    private JTextField deleteUserTextField1;
    private JPasswordField deletePasswordField1;
    public JPanel deleteroot;
    private JButton regresarButton;

    public String usuarioj;
    public String passwordj;

    //variables sql
    public static final String DB_URL = "jdbc:mysql://localhost/sistemalogin";
    public static final String USER = "root";
    public static final String PASSWORD = "root_bas3";
    public deleteform() {
        eliminarUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuarioj = deleteUserTextField1.getText();
                passwordj = new String(deletePasswordField1.getPassword());

                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    String QUERY = "DELETE FROM usuariosbd WHERE nombrebd = ? AND passwordbd = ?";
                    PreparedStatement stmt = conn.prepareStatement(QUERY);
                    // Close the current window
                    Component component = (Component) e.getSource();
                    JFrame currentFrame = (JFrame) SwingUtilities.getRoot(component);
                    currentFrame.dispose();

                    int rowsafeccted = stmt.executeUpdate();

                    if (rowsafeccted > 0) {
                        deleteUserTextField1.setText("");
                        deletePasswordField1.setText("");
                        JOptionPane.showMessageDialog(deleteroot, "Usuario eliminado");
                    } else {
                        JOptionPane.showMessageDialog(deleteroot, "Usuario no encontrado o contraseña incorrecta para eliminar.");
                    }
                } catch (SQLException x) {
                    throw new RuntimeException(x);
                }
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                Component component = (Component) e.getSource();
                JFrame currentFrame = (JFrame) SwingUtilities.getRoot(component);
                currentFrame.dispose();

                // Open the new window
                JFrame frame = new JFrame("updateform");
                frame.setContentPane(new main().mainroot);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);

            }
        });
    }
    // Método para mostrar un mensaje con JOptionPane.showMessageDialog
    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(deleteroot, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("deleteform");
        frame.setContentPane(new deleteform().deleteroot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
