/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import vista.VistaAdmin;
import vista.VistaPerfil;
import modelo.Usuario;

/**
 *
 * @author Navarro
 */
public class PerfilControlador {
    private VistaPerfil vistaP;
    private UsuarioDAO usuarioD;
    private AveriaDAO averiaD;
    private MaquinariaDAO maquinariaD;
    private Usuario usuarioActual;

    public PerfilControlador(VistaPerfil vistaP, UsuarioDAO usuarioD, AveriaDAO averiaD, MaquinariaDAO maquinariaD, Usuario usuarioActual) {
        this.vistaP = vistaP;
        this.usuarioD = usuarioD;
        this.averiaD = averiaD;
        this.maquinariaD = maquinariaD;
        this.usuarioActual = usuarioActual;

        vistaP.cargarDatosUsuario(usuarioActual);
        vistaP.bloquearCampos();

        vistaP.setBtnEditarListener(e -> vistaP.habilitarCampos());
        vistaP.setBtnGuardarListener(e -> guardarCambios());
        vistaP.setBtnVolverListener(e -> volverAdmin());
    }

    private void guardarCambios() {
        try {
            usuarioActual.setNombre(vistaP.getNombre());
            usuarioActual.setApellido(vistaP.getApellido());
            usuarioActual.setTelefono(vistaP.getTelefono());
            usuarioActual.setEmail(vistaP.getEmail());
            usuarioActual.setPassword(vistaP.getPassword());

            usuarioD.actualizar(usuarioActual);

            vistaP.mostrarMensaje("Datos actualizados correctamente.");
            vistaP.bloquearCampos();

        } catch (Exception e) {
            vistaP.mostrarError("Error al actualizar perfil: " + e.getMessage());
        }
    }

    private void volverAdmin() {
        VistaAdmin admin = new VistaAdmin();
        // admin.mostrarUsuario(usuarioActual.getNombre(), usuarioActual.getApellido());

        // new AdminControlador(admin, usuarioD, averiaD, maquinariaD, usuarioActual);
        admin.setVisible(true);

        vistaP.dispose();
    }
}
