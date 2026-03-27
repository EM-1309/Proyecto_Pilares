/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Usuario;
import vista.ReportarAveriaView;


/**
 *
 * @author konatasht
 */
public class AveriaControladorReportar {
    private ReportarAveriaView rA;
    private Usuario usuarioActual;
    
    public AveriaControladorReportar(ReportarAveriaView rA, Usuario usuarioActual){
        this.rA = rA;
        this.usuarioActual = usuarioActual;
    }
    
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author konatasht
 */
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

    private void cargarDatosIniciales() {
         List<Maquinaria> maquinas = maquinariaDAO.listar();

        TipoAveria t1 = new TipoAveria();
        t1.setCodigoTipoAveria(1);
        t1.setDescripcionTipoAv("Mecánica");
        t1.setTiempoPromRepar(0);

        TipoAveria t2 = new TipoAveria();
        t2.setCodigoTipoAveria(2);
        t2.setDescripcionTipoAv("Eléctrica");
        t2.setTiempoPromRepar(0);

        TipoAveria t3 = new TipoAveria();
        t3.setCodigoTipoAveria(3);
        t3.setDescripcionTipoAv("Hidráulica");
        t3.setTiempoPromRepar(0);

        List<TipoAveria> tipos = java.util.Arrays.asList(t1, t2, t3);

        vista.cargarMaquinas(maquinas);
        vista.cargarTiposAveria(tipos);
    }

    private class ReportarListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String descripcion = vista.getDescInic();

                if (descripcion == null || descripcion.isBlank()) {
                    vista.mostrarError("Debes introducir una descripción de la avería.");
                    return;
                }

                Averia a = new Averia();
                a.setDescInicAveria(descripcion);
                a.setFechaInicioAver(new Timestamp(System.currentTimeMillis()));
                a.setFechaAsigTecnico(null);
                a.setFechaAcepTecnico(null);
                a.setFechaFinalizTecnico(null);
                a.setProcRealizadoTecnico(null);

                a.setUsuarioReportaFK(usuarioActual.getCodigoUsuario());
                a.setUsuarioTecnicoFK(null);
                a.setMaquinariaFK(vista.getMaquinariaID());
                a.setTipoAveriaFK(vista.getTipoAveriaID());

                averiaDAO.insertar(a);

                vista.mostrarMensaje("Avería reportada correctamente.");
                vista.limpiarFormulario();

            } catch (Exception ex) {
                vista.mostrarError("Error al reportar la avería: " + ex.getMessage());
            }
        }
    }

    private class VolverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            vista.dispose();
            VistaAdmin vAdmin = new VistaAdmin();
            new AdminControlador(vAdmin, usuarioActual);
        }
    }
}
