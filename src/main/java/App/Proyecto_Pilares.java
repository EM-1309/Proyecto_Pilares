package App;

import controlador.LoginControlador;
import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import vista.LoginView;


/**
 * @author konatash
 */
public class Proyecto_Pilares {
    public static void main(String[] args) {
      javax.swing.SwingUtilities.invokeLater(() -> {
            LoginView loginView = new LoginView();

            UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
            AveriaDAO averiaDAO = new AveriaDAOImpl();
            MaquinariaDAO maquinariaDAO = new MaquinariaDAOImpl();

            new LoginControlador(loginView, usuarioDAO, averiaDAO, maquinariaDAO);

            loginView.setLocationRelativeTo(null);
            loginView.setVisible(true);
        });
    }
}