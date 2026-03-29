package controlador;

import dao.*;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.TipoMaquinaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Maquinaria;
import modelo.Usuario;
import vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.List;
import modelo.TipoMaquinaria;

public class AgregarMaquina {

    private VistaAgregarMaquina vista;
    private MaquinariaDAO dao;
    private TipoMaquinaDAO tipoDao;
    private Usuario usuarioActual;

    public AgregarMaquina(VistaAgregarMaquina vistaAgregar, Usuario usuarioActual) {
        this.vista = vistaAgregar;
        this.dao = new MaquinariaDAOImpl();
        this.tipoDao = new TipoMaquinaDAOImpl();
        this.usuarioActual = usuarioActual;
        
        cargarTiposMaquina();

        vista.getBtnCrear().addActionListener(new CrearListener());
        vista.getBtnVolver().addActionListener(new VolverListener());
        
        vista.setCodigoAutomatico();
    }

    class CrearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vista.getTxtNombre().trim();
                String descripcion = vista.getTxtDescripcion().trim();
                String tipoTexto = vista.getTipoMaquina();

                if (nombre.isBlank()) {
                vista.mostrarError("Debe introducir el nombre de la máquina.");
                return;
            }
            if (nombre.length() < 3) {
                vista.mostrarError("El nombre debe tener al menos 3 caracteres.");
                return;
            }
            if (nombre.length() > 100) {
                vista.mostrarError("El nombre es demasiado largo.");
                return;
            }
            if (!nombre.matches("[\\p{L}0-9 .\\-_/]+")) {
                vista.mostrarError("El nombre contiene caracteres no válidos.");
                return;
            }
            if (tipoTexto == null || tipoTexto.isBlank()) {
                vista.mostrarError("Debe seleccionar un tipo de máquina.");
                return;
            }
            if (descripcion.isBlank()) {
                vista.mostrarError("Debe introducir una descripción.");
                return;
            }
            if (descripcion.length() < 5) {
                vista.mostrarError("La descripción es demasiado corta.");
                return;
            }
            if (descripcion.length() > 255) {
                vista.mostrarError("La descripción es demasiado larga.");
                return;
            }

            int tipoMaquinaId = vista.getTipoMaquinaSeleccionadoId();

            if (tipoMaquinaId == -1) {
                vista.mostrarError("Debe seleccionar un tipo de máquina válido.");
                return;
            }

            Maquinaria m = new Maquinaria();
            m.setNombre(nombre);
            m.setCodigoEstadoFK(1);
            m.setTipoMaquinariaFK(tipoMaquinaId);
            m.setFechaAlta(new Date(System.currentTimeMillis()));

            dao.insertar(m);

            vista.mostrarMensaje("Máquina creada correctamente.");
            abrirVistaMaquinas();

            }catch (Exception ex) {
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
    
    private void cargarTiposMaquina() {
        List<TipoMaquinaria> tipos = tipoDao.listar();
        vista.cargarTiposMaquina(tipos);
    }
}