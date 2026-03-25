package controlador;

import dao.*;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Usuario;
import vista.*;

public class AdminControlador {

    private VistaAdmin vistaAd;
    private UsuarioDAO usuarioD;
    private AveriaDAO averiaD;
    private MaquinariaDAO maquinariaD;
    private Usuario usuarioActual;

    public AdminControlador(VistaAdmin vistaAd, Usuario usuarioActual){
        this.vistaAd = vistaAd;
        this.usuarioD = new UsuarioDAOImpl();
        this.averiaD = new AveriaDAOImpl();
        this.maquinariaD = new MaquinariaDAOImpl();
        this.usuarioActual = usuarioActual;

        this.vistaAd.esMenuUsuarios(e -> abrirUsuarios());
        this.vistaAd.esMenuAverias(e -> abrirAverias());
        this.vistaAd.esMenuMaquinas(e -> abrirMaquinas());
        this.vistaAd.esMenuPerfil(e -> abrirPerfil());
        this.vistaAd.setCerrarSesionListener(e -> cerrarSesion());
        this.vistaAd.setSalirListener(e -> salirAplicacion());
    }

    private void abrirUsuarios(){
        UsuariosView usuarioV = new UsuariosView();
        new UsuarioControlador(usuarioV, usuarioActual);
        usuarioV.setVisible(true);
        vistaAd.dispose();
    }

    private void abrirAverias(){
        AveriasView averiaV = new AveriasView();
        new AveriaControlador(averiaV, usuarioActual);
        averiaV.setVisible(true);
        vistaAd.dispose();
    }

    private void abrirMaquinas(){
        VistaMaquinas maquinaV = new VistaMaquinas();
        new MaquinaControlador(maquinaV, usuarioActual);
        maquinaV.setVisible(true);
        vistaAd.dispose();
    }

    private void abrirPerfil(){
        VistaPerfil perfilV = new VistaPerfil();
        new PerfilControlador(perfilV, usuarioActual);
        perfilV.setVisible(true);
        vistaAd.dispose();
    }

    private void cerrarSesion() {
        LoginView login = new LoginView();
        new LoginControlador(login, usuarioActual);
        login.setVisible(true);
        vistaAd.dispose();
    }

    private void salirAplicacion() {
        System.exit(0);
    }
}