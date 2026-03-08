/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.*;
import modelo.Averia;
import vista.*;

/**
 *
 * @author Navarro
 */
public class AveriaControlador {
    private AveriasView aV;
    private AveriaDAO aD;
    private UsuarioDAO uD;
    private MaquinariaDAO mD;
    
    // Constructor
    public AveriaControlador(AveriasView aV, AveriaDAO aD){
        this.aV = aV;
        this.aD = aD;
        
        // Enlazamos los botones con sus acciones
        this.aV.escucharBtnReportar(e -> reportarFalla());
        this.aV.setBtnVolverListener(e -> volverAdmin());
    }
    
    // Método para reportar avería
    private void reportarFalla(){
        try{
            if(aV.getDescInic().isEmpty()){
                aV.mostrarError("La descripción es obligatoria");
                return;
            }
            
            Averia a = new Averia();
            a.setDescInicAveria(aV.getDescInic());
            a.setMaquinariaFK(aV.getMaquinariaID());
            a.setUsuarioReportaFK(aV.getOperarioID());
            a.setTipoAveriaFK(aV.getTipoAveriaID());
            
            aD.insertar(a);
            aV.mostrarMensaje("Averia reportada correctamente");
        }catch(NumberFormatException e){
            aV.mostrarError("Los IDs deben ser números válidos");
        }catch(Exception e){
            aV.mostrarError("Error: " + e.getMessage());
        }
    }
    
    // Método para volver el menú "Principal"
    private void volverAdmin(){
        VistaAdmin admin = new VistaAdmin();
        
        new AdminControlador(admin, uD, aD, mD);
        
        admin.setVisible(true);
        
        aV.dispose();
    }
}
