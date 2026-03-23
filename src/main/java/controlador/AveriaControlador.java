package controlador;

import dao.*;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Averia;
import modelo.Usuario;
import vista.*;

public class AveriaControlador {

    private AveriasView aV;
    private AveriaDAO aD;
    private UsuarioDAO uD;
    private MaquinariaDAO mD;
    private Usuario usuarioActual;

    public AveriaControlador(AveriasView aV, Usuario usuarioActual){
        this.aV = aV;
        this.aD = new AveriaDAOImpl();
        this.uD = new UsuarioDAOImpl();
        this.mD = new MaquinariaDAOImpl();
        this.usuarioActual = usuarioActual;

        this.aV.escucharBtnReportar(e -> reportarFalla());
        this.aV.setBtnVolverListener(e -> volverAdmin());
    }

    private void reportarFalla(){
        try{
            Averia a = new Averia();
            a.setDescInicAveria(aV.getDescInic());
            a.setMaquinariaFK(aV.getMaquinariaID());
            a.setUsuarioReportaFK(aV.getOperarioID());
            a.setTipoAveriaFK(aV.getTipoAveriaID());

            aD.insertar(a);
            aV.mostrarMensaje("Avería reportada correctamente");
        }catch(Exception e){
            aV.mostrarError("Error: " + e.getMessage());
        }
    }

    private void volverAdmin(){
        VistaAdmin admin = new VistaAdmin();
        new AdminControlador(admin, usuarioActual);
        admin.setVisible(true);
        aV.dispose();
    }
}