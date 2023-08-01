import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class updateform {
    private JTextField usertextField1;

    private JTextField newUsertextField2;
    private JPasswordField newPasswordField2;
    private JButton guardarCambiosButton;
    private JPanel updateroot;

    //variables sql
    public static final String DB_URL = "jdbc:mysql://localhost/sistemalogin";
    public static final String USER = "root";
    public static final String PASSWORD = "root_bas3";

    // variables form
    public String usuariojupdate;
    public String passwordjupdate;
    public String newusuariojupdate;
    public String newpasswordjupdate;

    public updateform() {
        guardarCambiosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuariojupdate = usertextField1.getText();
                newusuariojupdate = newUsertextField2.getText();
                newpasswordjupdate = new String(newPasswordField2.getPassword());

                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)){
                    String updateQuery = "UPDATE usuariosbd SET nombrebd = ?, passwordbd = ? WHERE nombrebd = ?";
                    PreparedStatement stmt = conn.prepareStatement(updateQuery);
                    stmt.setString(1, newusuariojupdate);
                    stmt.setString(2, newpasswordjupdate);
                    stmt.setString(3, usuariojupdate);
                    int rowupdate = stmt.executeUpdate();

                    if(rowupdate > 0){
                        JOptionPane.showMessageDialog(updateroot, "Actualizado con exito");
                    }else{

                        JOptionPane.showMessageDialog(updateroot, "No se encontro el usuario");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("updateform");
        frame.setContentPane(new updateform().updateroot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
