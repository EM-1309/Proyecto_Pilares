/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.AveriaDAO;
import dao.MaquinariaDAO;
import dao.UsuarioDAO;
import dao.impl.AveriaDAOImpl;
import dao.impl.MaquinariaDAOImpl;
import dao.impl.UsuarioDAOImpl;
import java.util.Optional;
import modelo.Usuario;
import vista.EditarUsuario;
import vista.UsuariosView;

/**
 *
 * @author emmnavmoj
 */
public class EditarUsuarioControlador {
    // Llamamos a las clases
    private EditarUsuario vistaE;
    private UsuarioDAO usuarioD;
    private AveriaDAO averiaD;
    private MaquinariaDAO maquinariaD;
    private Usuario usuarioActual;
    
    // Constructor
    public EditarUsuarioControlador(EditarUsuario vistaE, Usuario usuarioActual) {
        this.vistaE = vistaE;
        this.usuarioD = new UsuarioDAOImpl();
        this.averiaD = new AveriaDAOImpl();
        this.maquinariaD = new MaquinariaDAOImpl();
        this.usuarioActual = usuarioActual;

        cargarTabla();
        
        this.vistaE.escucharGuardar(e -> guardarCambios());
        this.vistaE.setBtnVolverListener(e -> volverUsuarios());
        this.vistaE.setTablaMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cargarUsuarioSeleccionado();
            }
        });
    }
    
    // Cargamos la tabla
    private void cargarTabla() {
        vistaE.llenarTabla(usuarioD.listar());
    } 
    
    // Cargamos al usuario seleccionado
    private void cargarUsuarioSeleccionado() {
        int id = vistaE.getIdSeleccionado();

        if (id == -1) {
            return;
        }

        Optional<Usuario> usuarioOpt = usuarioD.buscarPorId(id);

        if (usuarioOpt.isPresent()) {
            vistaE.cargarUsuarioEnFormulario(usuarioOpt.get());
        } else {
            vistaE.mostrarError("No se pudo cargar el usuario seleccionado.");
        }
    }
    
    // Método para guardar los cambios realizados
    private void guardarCambios() {
        try {
            int id = vistaE.getIdSeleccionado();

            if (id == -1) {
                vistaE.mostrarError("Debe seleccionar un usuario de la tabla.");
                return;
            }

            Optional<Usuario> usuarioOpt = usuarioD.buscarPorId(id);

            if (!usuarioOpt.isPresent()) {
                vistaE.mostrarError("No se encontró el usuario a editar.");
                return;
            }

            Usuario u = usuarioOpt.get();

            u.setNombre(vistaE.getNombre());
            u.setApellido(vistaE.getApellido());
            u.setTelefono(vistaE.getTelefono());
            u.setEmail(vistaE.getEmail());
            u.setPassword(vistaE.getPassword());
            u.setCodigoRolFK(vistaE.getRolId());

            usuarioD.actualizar(u);

            vistaE.mostrarMensaje("Usuario actualizado correctamente.");
            vistaE.limpiarFormulario();
            cargarTabla();

        } catch (Exception e) {
            e.printStackTrace();
            vistaE.mostrarError("Error al guardar cambios: " + e.getMessage());
        }
    }
    
    // Método para volver a la vista Usuario
    private void volverUsuarios() {
        UsuariosView usuariosV = new UsuariosView();
        new UsuarioControlador(usuariosV, usuarioActual);
        usuariosV.setVisible(true);
        vistaE.dispose();
    }
}
