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
import java.sql.Date;

public class AgregarMaquina {

    private VistaAgregarMaquina vista;
    private MaquinariaDAO dao;
    private UsuarioDAO uD;
    private AveriaDAO aD;
    private Usuario usuarioActual;

    public AgregarMaquina(VistaAgregarMaquina vistaAgregar, Usuario usuarioActual) {
        this.vista = vistaAgregar;
        this.dao = new MaquinariaDAOImpl();
        this.uD = new UsuarioDAOImpl();
        this.aD = new AveriaDAOImpl();
        this.usuarioActual = usuarioActual;

        vista.getBtnCrear().addActionListener(new CrearListener());
        vista.getBtnVolver().addActionListener(new VolverListener());
    }

    class CrearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vista.getTxtNombre();
                String codigoTexto = vista.getTxtCodigo();
                String tipoTexto = vista.getTipoMaquina();

                if (nombre.isEmpty() || codigoTexto.isEmpty()) {
                    vista.mostrarError("Debe completar los campos obligatorios");
                    return;
                }

                int tipoMaquina = convertirTipo(tipoTexto);

                Maquinaria m = new Maquinaria();
                m.setNombre(nombre);
                m.setCodigoEstadoFK(1);
                m.setTipoMaquinariaFK(tipoMaquina);
                m.setFechaAlta(new Date(System.currentTimeMillis()));

                dao.insertar(m);

                vista.mostrarMensaje("Máquina creada correctamente");

                abrirVistaMaquinas();

            } catch (Exception ex) {
                vista.mostrarError("Error al crear la máquina");
                ex.printStackTrace();
            }
        }
    }

    class VolverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            abrirVistaMaquinas();
        }
    }

    private void abrirVistaMaquinas() {
        VistaMaquinas vistaMaquinas = new VistaMaquinas();

        // 👇 USAMOS LOS MISMOS DAOs (NO CREAR NUEVOS)
        new MaquinaControlador(vistaMaquinas, usuarioActual);

        vistaMaquinas.setVisible(true);
        vista.dispose();
    }

    private int convertirTipo(String tipo) {
        switch (tipo) {
            case "Torno CNC": return 1;
            case "Fresadora": return 2;
            case "Prensa Hidráulica": return 3;
            case "Compresor Industrial": return 4;
            default: return 1;
        }
    }
}