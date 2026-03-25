package controlador;

import dao.AveriaDAO;
import dao.impl.AveriaDAOImpl;
import modelo.Averia;
import modelo.Usuario;
import vista.EditarAveriasView;
import vista.VistaAverias;

public class EditarAveriasControlador {

    private EditarAveriasView vista;
    private Usuario usuarioActual;
    private int idAveria;
    private AveriaDAO dao;

    public EditarAveriasControlador(EditarAveriasView vista, Usuario usuarioActual, int idAveria) {
        this.vista = vista;
        this.usuarioActual = usuarioActual;
        this.idAveria = idAveria;
        this.dao = new AveriaDAOImpl();

        cargarTabla();
        cargarAveriaSeleccionada();

        this.vista.setBtnVolverListener(e -> volver());

        // ✅ FIX listener tabla (evita doble disparo)
        this.vista.setTablaListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarAveria();
            }
        });

        this.vista.setBtnActualizarListener(e -> actualizar());
    }

    private void cargarAveriaSeleccionada() {
        try {
            dao.buscarPorId(idAveria).ifPresent(a -> {
                vista.setDatosAveria(a);
            });
        } catch (Exception e) {
            vista.mostrarError("Error cargando avería: " + e.getMessage());
        }
    }

    private void actualizar() {
        try {
            Averia a = vista.getDatosFormulario(); // 👈 importante que exista
            a.setCodigoAveria(idAveria); // 🔥 clave

            dao.actualizar(a);

            vista.mostrarMensaje("Avería actualizada");

            cargarTabla();

            // ✅ FIX importante
            vista.limpiarFormulario();

        } catch (Exception e) {
            vista.mostrarError("Error al actualizar: " + e.getMessage());
        }
    }

    private void seleccionarAveria() {
        try {
            int id = vista.getIdSeleccionado();
            if (id == -1) return;

            this.idAveria = id; // 🔥 clave

            dao.buscarPorId(id).ifPresent(a -> {
                vista.setDatosAveria(a);
            });

        } catch (Exception e) {
            vista.mostrarError("Error seleccionando avería: " + e.getMessage());
        }
    }

    private void cargarTabla() {
        try {
            vista.llenarTabla(dao.listar());
        } catch (Exception e) {
            vista.mostrarError("Error cargando averías: " + e.getMessage());
        }
    }

    private void volver() {
        VistaAverias v = new VistaAverias();
        new AveriaControlador(v, usuarioActual);
        v.setVisible(true);
        vista.dispose();
    }
}