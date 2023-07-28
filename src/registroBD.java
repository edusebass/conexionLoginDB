import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class registroBD {
    //variables panel
    private JButton GUARDARButton;
    private JTextField userregistroField1;
    private JPanel registroroot;
    private JButton INICIARSESIONButton;
    private JPasswordField contraseñaregistroField1;

    //variables de guardado
    public String usuario;
    public String password;

    //variables sql
    public static final String DB_URL = "jdbc:mysql://localhost/estudiantes";
    public static final String USER = "root";
    public static final String PASSWORD = "root_bas3";

public registroBD() {
    GUARDARButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            usuario = userregistroField1.getText();
            password = contraseñaregistroField1.getText();
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {


            } catch (SQLException x) {
                throw new RuntimeException(x);
            }
        }
    });
}
    public static void main(String[] args) {
        JFrame frame = new JFrame("registroBD");
        frame.setContentPane(new registroBD().registroroot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
