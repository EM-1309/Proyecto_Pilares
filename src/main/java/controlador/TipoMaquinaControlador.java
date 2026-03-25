/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.TipoMaquinaDAO;
import dao.impl.TipoMaquinaDAOImpl;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.TipoMaquinaria;
import modelo.Usuario;
import vista.VistaAgregarTipo;
import vista.VistaMaquinas;
import vista.VistaTipoMaquina;

/**
 *
 * @author Estudiant
 */
public class TipoMaquinaControlador {
    private VistaTipoMaquina vista;
    private VistaAgregarTipo vistaA;
    private TipoMaquinaDAO tipoD;
    private Usuario usuarioActual;
    
    public TipoMaquinaControlador(VistaTipoMaquina vista, VistaAgregarTipo vistaA, Usuario usuarioActual){
        this.vista = vista;
        this.vistaA = vistaA;
        this.tipoD = new TipoMaquinaDAOImpl();
        this.usuarioActual = usuarioActual;
        
        this.vista.getAgregar().addActionListener(e -> abrirVista());
        this.vista.getEliminar().addActionListener(e -> eliminarTipo());
        this.vista.getVolver().addActionListener(e -> volverM());
        this.vistaA.getGuardar().addActionListener(e -> guardar());
        
        refrescarTabla();
    }
    
    // Método para guardar un tipo de maquina
    private void guardar(){
        String descripcion = vistaA.getDescripcion();
        
        if(descripcion.isEmpty()){
            vistaA.mostrarError("La descripción no puede estar vacía");
                    return;
        }
        
        TipoMaquinaria tm = new TipoMaquinaria();
        tm.setDescripcionMaq(descripcion);
        
        if(tipoD.insertar(tm)){
            vistaA.dispose();
            vista.mostrarMensaje("Estado agregado correctamente");
            refrescarTabla();
        }else{
            vistaA.mostrarError("Error al guardar el estado");  
        }
    }
    
    private void eliminarTipo(){
        int fila = vista.getTablaMaquina().getSelectedRow();
        if(fila == -1){
            JOptionPane.showMessageDialog(vista, "Seleccione un tipo de máquina.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        int id = (int) vista.getTablaMaquina().getValueAt(fila, 0);
        String nombre = (String) vista.getTablaMaquina().getValueAt(fila, 1);
        
        if(tipoD.eliminar(id)){
            JOptionPane.showMessageDialog(vista, "Eliminado correctamente.");
            refrescarTabla();  
        }else {
            // Restricción: No se borra si hay máquinas vinculadas
            JOptionPane.showMessageDialog(vista, 
                "No se puede eliminar '" + nombre + "' porque existen máquinas de este tipo registradas.", 
                "Error de Integridad", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void abrirVista(){
        vistaA.limpiarCampos();
        vistaA.setLocationRelativeTo(vista);
        vistaA.setVisible(true);
    }
    
    private void volverM(){
        VistaMaquinas vistaM = new VistaMaquinas();
        new MaquinaControlador(vistaM, usuarioActual);
        vista.dispose();
        vistaM.setVisible(true);
    }
    
    private void refrescarTabla() {
        List<TipoMaquinaria> lista = tipoD.listar();
        vista.llenarTabla(lista);
    }
}
