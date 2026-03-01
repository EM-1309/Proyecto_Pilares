/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.*;
import modelo.*;
import vista.*;

/**
 *
 * @author Navarro
 * 
 * Controlador para la gestión de usuarios (CRUD).
 * Maneja las acciones de la ventana UsuariosView.
 */
public class UsuarioControlador {
    // Llamamos a las clases
    private UsuariosView uV;
    private UsuarioDAO uD;
    
    // Constructor y enlazamos los botones con sus acciones
    public UsuarioControlador(UsuariosView uV, UsuarioDAO uD){
        this.uV = uV;
        this.uD = uD;
        
        // Enlazamos los botones con sus acciones
        this.uV.escucharBtnAgregar(e -> guardar());
        this.uV.escucharBtnEliminar(e -> eliminar());
    }
    
    // Método para capturar los datos de la interfaz, construye el modelo y lo envia al DAO
    private void guardar(){
        try{
            // Se crea el objeto modelo para transpotar los datos
            Usuario u = new Usuario();
            u.setNombre(uV.getNombre());
            u.setApellido(uV.getApellido());
            u.setEmail(uV.getEmail());
            u.setPassword(uV.getPassword());
            u.setCodigoRolFK(uV.getRolId());
            u.setActivo(uV.isActivo());
            
            // Llamamos al método de insertar
            uD.insertar(u);
            
          // Mostramos un mensaje para informarle al usuario que la operación ha sido éxitosa
            uV.mostrarMensaje("Usuario guardado con éxito");
        }catch(Exception e){
            uV.mostrarError("Error al guardar: " + e.getMessage());
        }
    }
    
    private void eliminar(){
    }
}
