package controlador;

import dao.AveriaDAO;
import dao.impl.AveriaDAOImpl;
import modelo.Usuario;
import vista.EditarAveriasView;
import vista.ReportarAveriaView;
import vista.VistaAdmin;
import vista.VistaAverias;

public class AveriaControlador {

    private VistaAverias aV;
    private Usuario usuarioActual;
    private AveriaDAO aD;
    

    public AveriaControlador(VistaAverias aV, Usuario usuarioActual){
        this.aV = aV;
        this.usuarioActual = usuarioActual;
        this.aD = new AveriaDAOImpl();

        cargarTabla(); // 🔥 IMPORTANTE

        // BOTONES
        this.aV.getReportar().addActionListener(e -> abrirReportar());
        this.aV.getActualizar().addActionListener(e -> irEditar());
        this.aV.getVolver().addActionListener(e -> volverAdmin());
    }

    // 🔥 CARGAR AVERÍAS EN TABLA
    private void cargarTabla(){
        try {
            aV.llenarTabla(aD.listar()); // usa tu método DAO
        } catch(Exception e){
            aV.mostrarError("Error cargando averías: " + e.getMessage());
        }
    }

    
    
    private void irEditar(){
        try {
            int id = aV.getIdSeleccionado();

            if(id == -1){
                aV.mostrarError("Selecciona una avería primero");
                return;
            }

            EditarAveriasView vistaEditar = new EditarAveriasView();

            // ⚠️ AQUÍ TENÍAS EL ERROR (constructor mal)
            new EditarAveriasControlador(vistaEditar, usuarioActual, id);

            vistaEditar.setVisible(true);
            aV.dispose();

        } catch(Exception e){
            aV.mostrarError("Error al abrir editar: " + e.getMessage());
        }
    }

    private void abrirReportar() {
        ReportarAveriaView vista = new ReportarAveriaView();
        new AveriaControladorReportar(vista, usuarioActual);
        aV.dispose();
        vista.setVisible(true);
    }

    private void volverAdmin(){
        VistaAdmin admin = new VistaAdmin();
        new AdminControlador(admin, usuarioActual);
        admin.setVisible(true);
        aV.dispose();
    }
}