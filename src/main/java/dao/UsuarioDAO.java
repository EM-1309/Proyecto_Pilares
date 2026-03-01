/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import java.util.Optional;
import modelo.Usuario;

/**
 *
 * @author emmnavmoj
 */
public interface UsuarioDAO {
    // Método registra un nuevo usuario(admin, operario o mecánico)
    void insertar(Usuario u);
    
    // Método para actulizar los datos de un usuario ya existente
    void actualizar(Usuario u);
    
    // Método para eliminar un usuario mediante su ID
    void eliminar(int id);
    
    // Busca un usuario por su ID
    Optional<Usuario> buscarPorId(int id);
    
    // Lista todos los usuarios registrados en el sistema
    List<Usuario> listar();
    
    // Verifica las credenciales de accceso, que luego puede ser esencial
    Optional<Usuario> login(String email, String password);
}
