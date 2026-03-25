package controlador;

import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;

import java.util.Optional;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import modelo.Usuario;
import vista.LoginView;
import vista.VistaAdmin;

public class LoginControlador {

    private LoginView vistal;
    private UsuarioDAO uDao;
    private AveriaDAO aDao;
    private MaquinariaDAO mDao;

    private Preferences prefs;

    public LoginControlador(LoginView vistal, Usuario usuarioActual) {
        this.vistal = vistal;

        this.uDao = new UsuarioDAOImpl();
        this.aDao = new AveriaDAOImpl();
        this.mDao = new MaquinariaDAOImpl();

        // 🔥 Inicializar preferencias
        prefs = Preferences.userRoot().node(this.getClass().getName());

        // 🔥 Cargar datos guardados
        cargarRecordar();

        // 🔥 Listener botón login
        this.vistal.getBtnEntrar().addActionListener(e -> {
            try {
                validarAcceso();
            } catch (BackingStoreException ex) {
                System.getLogger(LoginControlador.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        // 🔥 Listener checkbox (clave para borrar)
        this.vistal.getCheckRecordar().addActionListener(e -> {
            if (!vistal.getCheckRecordar().isSelected()) {
                prefs.remove("email");
            }
        });
    }

    private void cargarRecordar() {
        String emailGuardado = prefs.get("email", "");

        if (!emailGuardado.isEmpty()) {
            vistal.getTxtEmail().setText(emailGuardado);
            vistal.getCheckRecordar().setSelected(true);
        } else {
            vistal.getCheckRecordar().setSelected(false);
        }
    }

    private void validarAcceso() throws BackingStoreException {

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

                // 🔥 GUARDAR O BORRAR EMAIL
                if (vistal.getCheckRecordar().isSelected()) {
                    prefs.put("email", email);
                    prefs.flush(); // 🔥 guarda en disco
                } else {
                    prefs.remove("email");
                }

                vistal.dispose();

                if (u.getCodigoRolFK() == 1) {
                    VistaAdmin admin = new VistaAdmin();
                    admin.mostrarUsuario(u.getNombre());

                    new AdminControlador(admin, u);

                    admin.setVisible(true);

                } else if (u.getCodigoRolFK() == 2) {

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