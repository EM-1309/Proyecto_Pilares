package controlador;

import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import java.util.Optional;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.LoginView;
import vista.VistaAdmin;

public class LoginControlador {

    private LoginView vistal;
    private UsuarioDAO uDao;
    private AveriaDAO aDao;
    private MaquinariaDAO mDao;
    private Usuario usuarioActual;

    public LoginControlador(LoginView vistal, Usuario usuarioActual) {
        this.vistal = vistal;
        this.uDao = new UsuarioDAOImpl();
        this.aDao = new AveriaDAOImpl();
        this.mDao = new MaquinariaDAOImpl();
        

        this.vistal.getBtnEntrar().addActionListener(e -> validarAcceso());
    }

    private void validarAcceso() {

        String email = vistal.getTxtEmail().getText().trim();
        String pas = new String(vistal.getPasswdContraseña().getPassword());

        if (email.isEmpty() || pas.isEmpty()) {
            JOptionPane.showMessageDialog(vistal, 
                "Por favor, rellene todos los campos", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Optional<Usuario> resultado = uDao.login(email, pas);

        if (resultado.isPresent()) {
            Usuario u = resultado.get();

            if (u.isActivo()) {

                vistal.dispose();

                if (u.getCodigoRolFK() == 1) {

                    VistaAdmin admin = new VistaAdmin();
                    admin.mostrarUsuario(u.getNombre());

                    // 🔥 AQUÍ ESTÁ LA CLAVE (pasar usuarioActual)
                    new AdminControlador(admin, u);

                    admin.setVisible(true);

                } else if (u.getCodigoRolFK() == 2) {
                    // Aquí iría la vista de operario
                    JOptionPane.showMessageDialog(null, 
                        "Vista de operario no implementada");
                } else {
                    JOptionPane.showMessageDialog(null, 
                        "Rol no reconocido", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(vistal, 
                    "Acceso denegado: Usuario inactivo", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(vistal, 
                "Credenciales incorrectas", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}