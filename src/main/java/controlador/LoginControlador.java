/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.AveriaDAO;
import dao.UsuarioDAO;
import java.util.Optional;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.LoginView;
import vista.VistaAdmin;

/**
 *
 * @author emmnavmoj
 */
public class LoginControlador {
    // Llamamos a las clases
    private LoginView vistal;
    private UsuarioDAO uDao;
    private AveriaDAO aDao;
    
    // Constructor
    public LoginControlador(LoginView vistal, UsuarioDAO uDao, AveriaDAO aDao){
        this.vistal = vistal;
        this.uDao = uDao;
        this.aDao = aDao;
        
        // Escuchamos el botón
        this.vistal.getBtnEntrar().addActionListener(e -> validarAcceso()); 
    }
    
    // Método para validar el acceso del usuario
    private void validarAcceso(){
        // Capturamos datos de los campos del login
        String email = vistal.getTxtEmail().getText();
        String pas = new String(vistal.getPasswdContraseña().getPassword());
        
        // Comprobamos que los campos no esten vacios
        if(email.isEmpty() || pas.isEmpty()){
            JOptionPane.showMessageDialog(vistal, "Por favor, rellene todos los campos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Buscamos al usuario en la base de datos
        Optional<Usuario> resultado = uDao.login(email, pas);
        
        if(resultado.isPresent()){
            Usuario u = resultado.get();
            
            // Verificamos que el usuario este activo
            if(u.isActivo()){
                // Si lo esta, cerramos login
                vistal.dispose();
                VistaAdmin admin = new VistaAdmin();
                
                // Conectamos el administrador con sus funciones
                new AdminControlador(admin, this.uDao, this.aDao);
                
                // Abrimos la vistaAdmin
                admin.setVisible(true);
            }else{
                // Si no esta activo avisamos al usuario
                JOptionPane.showMessageDialog(vistal, "Acceso denegado: Usuario inactivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(vistal, "Correo o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
