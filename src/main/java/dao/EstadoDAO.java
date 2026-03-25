/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Estado;

public interface EstadoDAO {
    public boolean insertar(Estado estado);
    public boolean eliminar(int id);
    public List<Estado> listar();
}