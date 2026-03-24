/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.EstadoDAO;
import dao.impl.EstadoDAOImpl;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Estado;
import vista.VistaAgregar;
import vista.VistaMaquinas;
import vista.VistaTipoEstados;
import modelo.Usuario;

/**
 *
 * @author Estudiant
 */
public class EstadoControlador {
    private VistaTipoEstados vistaE;
    private VistaAgregar vistaAgregar;
    private EstadoDAO estadoD;
    private Usuario usuarioActual;
    
    public EstadoControlador(VistaTipoEstados vistaE, VistaAgregar vistaAgregar){
        this.vistaE = vistaE;
        this.vistaAgregar = vistaAgregar;
        this.estadoD = new EstadoDAOImpl();
        
        this.vistaE.getAgregar().addActionListener(e -> abrirAgregar());
        this.vistaE.getEliminar().addActionListener(e -> eliminarEstado());
        this.vistaE.getVolver().addActionListener(e -> volverM());
        
        this.vistaAgregar.getGuardar().addActionListener(e -> guardar());
        
        refrescarTabla();
    }
    
    // Método para abrir la ventana Agregar
    private void abrirAgregar(){
        vistaAgregar.limpiarCampos();
        vistaAgregar.setLocationRelativeTo(vistaE);
        vistaAgregar.setVisible(true);
    }
    
    // Método para guardar el nuevo Estado
    private void guardar(){
        String descripcion = vistaAgregar.getDescripcion();
        
        if(descripcion.isEmpty()){
            vistaAgregar.mostrarError("La descripción no puede estar vacía");
                    return;
        }
        
        Estado estado = new Estado();
        estado.setDescripcionEstado(descripcion);
        
        if(estadoD.insertar(estado)){
            vistaAgregar.dispose();
            vistaE.mostrarMensaje("Estado agregado correctamente");
            refrescarTabla();
        }else{
            vistaAgregar.mostrarError("Error al guardar el estado");  
        }
    }
    
    private void eliminarEstado(){
        // Accedemos a la tabla para obtener la fila seleccionada
        int fila = vistaE.getTablaEstados().getSelectedRow();
        
        if(fila == -1){
            vistaE.mostrarError("Debe seleccionar un estado de la tabla");
        }
        
        // Obtenemos el ID de la columna 0
        int id = (int) vistaE.getTablaEstados().getValueAt(fila, 0);
        String nombreEstado = (String) vistaE.getTablaEstados().getValueAt(fila, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(vistaE, "Esta seguro de eliminar el estado: " + nombreEstado + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if(confirmacion == JOptionPane.YES_OPTION){
            boolean eliminado = estadoD.eliminar(id);

            if (eliminado) {
                vistaE.mostrarMensaje("Estado eliminado correctamente.");
                refrescarTabla();
            } else {
                // Este es el mensaje de error que pediste
                vistaE.mostrarError("No se puede eliminar el estado '" + nombreEstado + 
                    "' porque está siendo utilizado por una máquina o una avería.");
            }
        }
    }
    
    // Método para volver a la vista Maquina
    private void volverM(){
        VistaMaquinas vistaM = new VistaMaquinas();
        new MaquinaControlador(vistaM, usuarioActual);
        vistaE.dispose();
        vistaM.setVisible(true);
    }
    
    // Método para recargar la tabla
    private void refrescarTabla(){
        List<Estado> lista = estadoD.listar();
        vistaE.llenarTabla(lista);
    }
}
