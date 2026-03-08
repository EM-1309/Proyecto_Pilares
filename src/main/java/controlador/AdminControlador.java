/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.*;
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
    
    // Constructor
    public AdminControlador(VistaAdmin vistaAd, UsuarioDAO usuarioD, AveriaDAO averiaD, MaquinariaDAO maquinariaD){
        this.vistaAd = vistaAd;
        this.usuarioD = usuarioD;
        this.averiaD = averiaD;
        this.maquinariaD = maquinariaD;
        
        // Asignamos las funciones a los botones del menú de gestión
        this.vistaAd.esMenuUsuarios(e -> abrirUsuarios());
        this.vistaAd.esMenuAverias(e -> abrirAverias());
    }
    
    // Instancia la vista de usuarios, le inyecta su controlador y la muestra.
    private void abrirUsuarios(){
        UsuariosView usuarioV = new UsuariosView();
        
        // Creamos su controlador y le pasamos la visata y el DAO
        new UsuarioControlador(usuarioV, usuarioD);
        
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
}
