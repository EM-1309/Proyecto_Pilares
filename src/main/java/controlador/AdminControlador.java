/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.*;
import modelo.Usuario;
import vista.*;

/**
 *
 * @author Navarro
 * 
 * Controlador de navegación principal.
 * Escucha las opciones del menú de VistaAdmin para abrir nuevas ventanas.
 */
public class AdminControlador {
    // Llamamos a las clases
    private VistaAdmin vistaAd;
    private UsuarioDAO usuarioD;
    private AveriaDAO averiaD;
    private MaquinariaDAO maquinariaD;
    private Usuario usuarioActual;

    // Constructor
    public AdminControlador(VistaAdmin vistaAd, UsuarioDAO usuarioD, AveriaDAO averiaD, MaquinariaDAO maquinariaD){
        this.vistaAd = vistaAd;
        this.usuarioD = usuarioD;
        this.averiaD = averiaD;
        this.maquinariaD = maquinariaD;
        
        // Asignamos las funciones a los botones del menú de gestión
        this.vistaAd.esMenuUsuarios(e -> abrirUsuarios());
        this.vistaAd.esMenuAverias(e -> abrirAverias());
        this.vistaAd.esMenuMaquinas(e -> abrirMaquinas());
        this.vistaAd.esMenuPerfil(e -> abrirPerfil());
        this.vistaAd.setCerrarSesionListener(e -> cerrarSesion());
        this.vistaAd.setSalirListener(e -> salirAplicacion());
    }
    
    // Instancia la vista de usuarios, le inyecta su controlador y la muestra.
    private void abrirUsuarios(){
        UsuariosView usuarioV = new UsuariosView();
        
        // Creamos su controlador y le pasamos la visata y el DAO
        new UsuarioControlador(usuarioV, usuarioD, averiaD, maquinariaD, usuarioActual);
        
        // Hacemos visible la vista usuario
        usuarioV.setVisible(true);
        
        // Cerramos la vistaAdmin, cuando se abra la vista usuario
        vistaAd.dispose();
    }
    
    // Instancia la vista de averías, le inyecta su controlador y la muestra.
    private void abrirAverias(){
        AveriasView averiaV = new AveriasView();
        
        // Creamos su controlador y le pasamos la vista y el DAO
        new AveriaControlador(averiaV, averiaD);
        
        // Hacemos visible la vista averia
        averiaV.setVisible(true);
        
        // Cerramos vistaAdmin, cuando se abra la vista averia
        vistaAd.dispose();
    }
    
    // Instancia la vista de Maquinas, le inyacta su controlador y la muestra
    private void abrirMaquinas(){
        VistaMaquinas maquinaV = new VistaMaquinas();
        
        // Creamos su controlador y le pasamos la vista y el DAO
        new MaquinaControlador(maquinaV, maquinariaD);
        
        // Hacemos visible la vista
        maquinaV.setVisible(true);
        
        // Cerramos la vista admin
        vistaAd.dispose();
    }
    
    // Instancia de la vista perfil
    private void abrirPerfil(){
        VistaPerfil perfilV = new VistaPerfil();
        
        // Hacemos visible la vista
        perfilV.setVisible(true);
        
        // Cerramos la vista admin
        vistaAd.dispose();
    }
    
    // Método para cerrar la sesión y volver al log in
    private void cerrarSesion() {
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            vistaAd,
            "¿Desea cerrar sesión y volver al inicio de sesión?",
            "Cerrar sesión",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            LoginView login = new LoginView();
            new LoginControlador(login, usuarioD, averiaD, maquinariaD);
            login.setVisible(true);
            vistaAd.dispose();
        }
    }
    
    // Método para salir de aplicación
    private void salirAplicacion() {
        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            vistaAd,
            "¿Desea salir de la aplicación?",
            "Salir",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
