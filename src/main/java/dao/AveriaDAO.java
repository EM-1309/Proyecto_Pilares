/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import java.util.Optional;
import modelo.Averia;

/**
 *
 * @author emmnavmoj
 */
public interface AveriaDAO {
    // Método para crear el registro de una nueva avería
    void insertar(Averia a);
    
    // Método para actualizar el estado o detalles de una reparación
    void actualizar(Averia a);
    
    // Busca una avería específica por su id
    Optional<Averia> buscarPorId(int id);
    
    // Obtiene todas las averías
    List<Averia> listar();
    
    // Método para eliminar un registro
    void eliminar(int id);
}
