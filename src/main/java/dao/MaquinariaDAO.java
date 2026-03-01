/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import java.util.Optional;
import modelo.Maquinaria;

/**
 *
 * @author emmnavmoj
 */
public interface MaquinariaDAO {
    // Método para agregar una nueva máquina
    void insertar(Maquinaria m);
    
    // Método para modificar la información de una máquina
    void actualizar(Maquinaria m);
    
    // Método para eliminar una máquina mediante su ID
    void eliminar(int id);
    
    // Obtiene una máquina específica mediante su clave primaria
    Optional<Maquinaria> buscarPorId(int id);
    
    // Devuelve la lista completa de máquinas
    List<Maquinaria> listar();
}
