/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.MaquinariaDAO;
import dao.impl.MaquinariaDAOImpl;
import modelo.Maquinaria;
import vista.VistaEditar;
import vista.VistaMaquinas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditarMaquina {

    private VistaEditar vista;
    private MaquinariaDAO dao;
    private int idMaquina;

    public EditarMaquina(VistaEditar vista, MaquinariaDAO dao, int idMaquina) {

        this.vista = vista;
        this.dao = dao;
        this.idMaquina = idMaquina;

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

            //  Obtener la máquina original
            Maquinaria m = dao.buscarPorId(idMaquina).orElse(new Maquinaria());

            //  Modificar solo lo necesario
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
        MaquinariaDAO daoM = new MaquinariaDAOImpl();

        new MaquinaControlador(vistaMaquinas, daoM);

        vistaMaquinas.setVisible(true);
        vista.dispose();

    }
}
