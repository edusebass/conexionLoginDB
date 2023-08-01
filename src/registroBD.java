import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class registroBD {
    //variables panel
    private JButton GUARDARButton;
    private JTextField userregistroField1;
    public JPanel registroroot;
    private JButton REGRESARButton;
    private JPasswordField contraseñaregistroField1;

    //variables de guardado
    public String usuarioj;
    public String passwordj;

    //variables sql
    public static final String DB_URL = "jdbc:mysql://localhost/sistemalogin";
    public static final String USER = "root";
    public static final String PASSWORD = "root_bas3";

    public registroBD() {
        GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuarioj = userregistroField1.getText();
                passwordj = new String(contraseñaregistroField1.getPassword());
                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    // Verificar si el usuario ya existe en la base de datos
                    String queryCheckUser = "SELECT COUNT(*) FROM usuariosbd WHERE nombrebd = ?";
                    try (PreparedStatement stmtCheckUser = conn.prepareStatement(queryCheckUser)) {
                        stmtCheckUser.setString(1, usuarioj);
                        ResultSet rs = stmtCheckUser.executeQuery();
                        rs.next();
                        int count = rs.getInt(1);
                        if (count > 0) {
                            mostrarMensaje("El usuario ya existe. Intente con otras credenciales.");
                        } else {
                            // Insertar el usuario y la contraseña en la base de datos
                            String queryInsertUser = "INSERT INTO usuariosbd (nombrebd, passwordbd) VALUES (?, ?)";
                            try (PreparedStatement stmtInsertUser = conn.prepareStatement(queryInsertUser)) {
                                stmtInsertUser.setString(1, usuarioj);
                                stmtInsertUser.setString(2, passwordj);
                                stmtInsertUser.executeUpdate();
                                mostrarMensaje("Datos ingresados con éxito en la base de datos.");
                                userregistroField1.setText("");
                                contraseñaregistroField1.setText("");
                            } catch (SQLException ex) {
                                throw new RuntimeException("Error al ejecutar la consulta SQL de inserción", ex);
                            }
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException("Error al ejecutar la consulta SQL de verificación", ex);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException("Error al conectar a la base de datos", ex);
                }
            }
        });
        REGRESARButton.addActionListener(new ActionListener() {
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
        JOptionPane.showMessageDialog(registroroot, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("registroBD");
        frame.setContentPane(new registroBD().registroroot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
