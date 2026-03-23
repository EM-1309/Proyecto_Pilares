package controlador;

import dao.*;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Usuario;
import vista.*;

public class UsuarioControlador {

    private UsuariosView uV;
    private UsuarioDAO uD;
    private AveriaDAO averiaD;
    private MaquinariaDAO maquinariaD;
    private Usuario usuarioActual;

    public UsuarioControlador(UsuariosView uV, Usuario usuarioActual){
        this.uV = uV;
        this.uD = new UsuarioDAOImpl();
        this.averiaD = new AveriaDAOImpl();
        this.maquinariaD = new MaquinariaDAOImpl();
        this.usuarioActual = usuarioActual;

        cargarTabla();

        this.uV.escucharBtnAgregar(e -> guardar());
        this.uV.escucharBtnEliminar(e -> eliminar());
        this.uV.setBtnLimpiarListener(e -> uV.limpiarFormulario());
        this.uV.escucharEditar(e -> editarV());
        this.uV.setBtnVolverListener(e -> volverAdmin());
    }

    private void cargarTabla(){
        uV.llenarTabla(uD.listar());
    }

    private void volverAdmin(){
        VistaAdmin admin = new VistaAdmin();
        new AdminControlador(admin, usuarioActual);
        admin.setVisible(true);
        uV.dispose();
    }

    private void guardar(){ /* tu código igual */ }

    private void eliminar(){ /* tu código igual */ }

    private void editarV(){
        EditarUsuario editarU = new EditarUsuario();
        new EditarUsuarioControlador(editarU, usuarioActual);
        editarU.setVisible(true);
        uV.dispose();
    }
}