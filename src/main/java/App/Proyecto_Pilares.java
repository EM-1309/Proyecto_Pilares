package App;

import controlador.LoginControlador;
import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Usuario;
import vista.LoginView;


/**
 * @author konatash
 */
public class Proyecto_Pilares {
    public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();
            Usuario usuarioActual = new Usuario();

            new LoginControlador(loginView,usuarioActual);

            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        });
    }
}