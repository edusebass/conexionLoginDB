import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginBD {
    private JPanel rootLogin;
    private JButton INGRESARButton;
    private JTextField usuarioField1;
    private JPasswordField passwordField1;
    private JButton REGISTRARSEButton;

    public String usuario;
    public String password;

    public static final String DB_URL = "jdbc:mysql://localhost/sistemalogin";
    public static final String USER = "root";
    public static final String PASSWORD = "root_bas3";

    public loginBD() {
        INGRESARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario = usuarioField1.getText();
                password = passwordField1.getText();

                try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
                    String QUERY = "SELECT nombre FROM usuarios WHERE nombre = ? AND password = ?";
                    PreparedStatement stmt = conn.prepareStatement(QUERY);
                    stmt.setString(1, usuario);
                    stmt.setString(2, password);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        JOptionPane.showMessageDialog(rootLogin, "Credenciales correctas");
                    } else {
                        JOptionPane.showMessageDialog(rootLogin, "Credenciales incorrectas.");
                    }
                } catch (SQLException x) {
                    throw new RuntimeException(x);
                }
            }
        });
        REGISTRARSEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("loginBD");
        frame.setContentPane(new loginBD().rootLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
