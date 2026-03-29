package controlador;

import dao.*;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import modelo.Maquinaria;
import modelo.Usuario;
import vista.*;

public class MaquinaControlador {

    private VistaMaquinas mV;
    private MaquinariaDAO mD;
    private UsuarioDAO uD;
    private AveriaDAO aD;
    private Usuario usuarioActual;

    public MaquinaControlador(VistaMaquinas mV, Usuario usuarioActual){
        this.mV = mV;
        this.mD = new MaquinariaDAOImpl();
        this.uD = new UsuarioDAOImpl();
        this.aD = new AveriaDAOImpl();
        this.usuarioActual = usuarioActual;

        cargarTabla();

        this.mV.setBtnAgregarListener(e -> abrirAgregar());
        this.mV.setBtnEliminarListener(e -> eliminar());
        this.mV.setBtnVolverListener(e -> volverAdmin());
        this.mV.setBtnMaquinaria(e -> abrirTipoMaquina());
        this.mV.setBtnEstados(e -> abrirTipoEstados());
        this.mV.setBtnEditarListener(e -> abrirEditarMaquina());
    }

    private void cargarTabla(){
        mV.llenarTabla(mD.listar());
    }

    private void abrirAgregar(){
        VistaAgregarMaquina vistaA = new VistaAgregarMaquina();
        new AgregarMaquina(vistaA, usuarioActual);
        mV.dispose();
        vistaA.setVisible(true);
    }
    
    private void abrirTipoMaquina(){
        VistaTipoMaquina vistaT = new VistaTipoMaquina();
        VistaAgregarTipo vistaA = new VistaAgregarTipo();
        new TipoMaquinaControlador(vistaT, vistaA, usuarioActual);
        mV.dispose();
        vistaT.setVisible(true);
    }
    
    private void abrirTipoEstados(){
        VistaTipoEstados vistaE = new VistaTipoEstados();
        VistaAgregar vistaA = new VistaAgregar();
        new EstadoControlador(vistaE, vistaA);
        mV.dispose();
        vistaE.setVisible(true);
    }
    
    private void abrirEditarMaquina(){
        VistaEditar vistaEd = new VistaEditar();
        new EditarMaquina(vistaEd, usuarioActual);
        mV.dispose();
        vistaEd.setVisible(true);
    }

    private void eliminar(){
        int id = mV.getIdSeleccionado();

        if(id == -1){
            mV.mostrarError("Selecciona una máquina");
            return;
        }

        try{
            mD.eliminar(id);
            mV.mostrarMensaje("Máquina eliminada");
            cargarTabla();
        }catch(Exception e){
            mV.mostrarError("Error: " + e.getMessage());
        }
    }

    private void volverAdmin(){
        VistaAdmin admin = new VistaAdmin();
        new AdminControlador(admin, usuarioActual);
        admin.setVisible(true);
        mV.dispose();
    }
}