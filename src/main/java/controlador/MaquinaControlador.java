/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import vista.VistaAdmin;
import vista.VistaMaquinas;

/**
 *
 * @author Mario
 */
public class MaquinaControlador {
    private VistaMaquinas vistaM;
    private MaquinariaDAO maquinariaD;
    private UsuarioDAO usuarioD;
    private AveriaDAO averiaD;

   // Constructor
    public MaquinaControlador(VistaMaquinas vistaM, MaquinariaDAO maquinariaD) {
        this.vistaM = vistaM;
        this.maquinariaD = maquinariaD;

        cargarTabla();

        this.vistaM.setBtnEliminarListener(e -> eliminar());
        this.vistaM.setBtnVolverListener(e -> volverAdmin());

    }

    // Método para cargar la tabla
    private void cargarTabla() {
        vistaM.llenarTabla(maquinariaD.listar());
    }

    // Método para volver al menu "Principal"
    private void volverAdmin() {
        VistaAdmin admin = new VistaAdmin();

        new AdminControlador(admin, usuarioD, averiaD, maquinariaD);

        admin.setVisible(true);

        vistaM.dispose();
    }

    // Método para eliminar maquinas
    private void eliminar() {
        int idSeleccionado = vistaM.getIdSeleccionado();

        if (idSeleccionado == -1) {
            vistaM.mostrarError("Debe seleccionar una máquina de la tabla.");
            return;
        }

        int respuesta = javax.swing.JOptionPane.showConfirmDialog(
            vistaM,
            "¿Está seguro de que desea eliminar esta máquina?",
            "Confirmar eliminación",
            javax.swing.JOptionPane.YES_NO_OPTION
        );

        if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
            try {
                maquinariaD.eliminar(idSeleccionado);
                vistaM.mostrarMensaje("Maquinaria eliminada correctamente.");
                cargarTabla();

            } catch (Exception e) {
                vistaM.mostrarError("Error al eliminar maquinaria: " + e.getMessage());
            }
        }
    } 
}
