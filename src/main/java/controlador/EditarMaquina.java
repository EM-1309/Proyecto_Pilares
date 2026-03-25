package controlador;

import dao.*;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Maquinaria;
import modelo.Usuario;
import vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarMaquina {

    private VistaEditar vista;
    private MaquinariaDAO dao;
    private UsuarioDAO uD;
    private AveriaDAO aD;
    private Usuario usuarioActual;
    private int idMaquina;

    public EditarMaquina(VistaEditar vista, Usuario usuarioActual) {
        this.vista = vista;
        this.dao = new MaquinariaDAOImpl();
        this.uD = new UsuarioDAOImpl();
        this.aD = new AveriaDAOImpl();
        this.usuarioActual = usuarioActual;

        cargarDatos();

        vista.setBtnGuardarListener(new GuardarListener());
        vista.setBtnVolverListener(e -> volver());
    }

    private void cargarDatos() {
        dao.buscarPorId(idMaquina).ifPresent(m -> {
            vista.setTxtNombre(m.getNombre());
            vista.setTxtCodigo(String.valueOf(m.getCodigoMaquinaria()));
        });
    }

    class GuardarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vista.getTxtNombre();

                Maquinaria m = dao.buscarPorId(idMaquina).orElse(new Maquinaria());
                m.setNombre(nombre);

                dao.actualizar(m);

                vista.mostrarMensaje("Máquina actualizada correctamente");

                volver();

            } catch (Exception ex) {
                vista.mostrarError("Error al actualizar la máquina");
                ex.printStackTrace();
            }
        }
    }

    private void volver() {
        VistaMaquinas vistaMaquinas = new VistaMaquinas();
        new MaquinaControlador(vistaMaquinas, usuarioActual);
        vista.dispose();
        vistaMaquinas.setVisible(true); 
    }
}