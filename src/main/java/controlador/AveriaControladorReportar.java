package controlador;

import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;
import modelo.Averia;
import modelo.Maquinaria;
import modelo.TipoAveria;
import modelo.Usuario;
import vista.ReportarAveriaView;
import vista.VistaAdmin;

public class AveriaControladorReportar {

    private final ReportarAveriaView vista;
    private final Usuario usuarioActual;
    private final AveriaDAO averiaDAO;
    private final MaquinariaDAO maquinariaDAO;

    public AveriaControladorReportar(ReportarAveriaView vista, Usuario usuarioActual) {
        this.vista = vista;
        this.usuarioActual = usuarioActual;
        this.averiaDAO = new AveriaDAOImpl();
        this.maquinariaDAO = new MaquinariaDAOImpl();

        cargarDatosIniciales();

        this.vista.escucharBtnReportar(new ReportarListener());
        this.vista.setBtnVolverListener(new VolverListener());

        this.vista.setVisible(true);
    }

    // ================= CARGA INICIAL =================
    private void cargarDatosIniciales() {
        List<Maquinaria> maquinas = maquinariaDAO.listar();

        TipoAveria t1 = new TipoAveria();
        t1.setCodigoTipoAveria(1);
        t1.setDescripcionTipoAv("Mecánica");

        TipoAveria t2 = new TipoAveria();
        t2.setCodigoTipoAveria(2);
        t2.setDescripcionTipoAv("Eléctrica");

        TipoAveria t3 = new TipoAveria();
        t3.setCodigoTipoAveria(3);
        t3.setDescripcionTipoAv("Hidráulica");

        List<TipoAveria> tipos = java.util.Arrays.asList(t1, t2, t3);

        vista.cargarMaquinas(maquinas);
        vista.cargarTiposAveria(tipos);
    }

    // ================= BOTÓN ACEPTAR =================
    private class ReportarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // 🔴 VALIDACIÓN
                if (!vista.datosValidos()) return;

                String descripcion = vista.getDescInic();

                Averia a = new Averia();
                a.setDescInicAveria(descripcion);
                a.setFechaInicioAver(new Timestamp(System.currentTimeMillis()));
                a.setUsuarioReportaFK(usuarioActual.getCodigoUsuario());

                int maquinaID = vista.getMaquinariaID();
                int tipoID = vista.getTipoAveriaID();

                // 🔴 SEGURIDAD EXTRA
                if (maquinaID == -1 || tipoID == -1) {
                    vista.mostrarError("Debes seleccionar máquina y tipo de avería.");
                    return;
                }

                a.setMaquinariaFK(maquinaID);
                a.setTipoAveriaFK(tipoID);

                // 🔥 CLAVE → evitar crash
                a.setUsuarioTecnicoFK(null);

                averiaDAO.insertar(a);

                vista.mostrarMensaje("Avería reportada correctamente.");
                vista.limpiarFormulario();

            } catch (Exception ex) {
                ex.printStackTrace(); // 👈 MUY IMPORTANTE para debug
                 ex.printStackTrace();
                vista.mostrarError("Error: " + ex.getMessage());
            }
        }
    }

    // ================= BOTÓN VOLVER =================
    private class VolverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
            VistaAdmin vAdmin = new VistaAdmin();
            new AdminControlador(vAdmin, usuarioActual);
        }
    }
}