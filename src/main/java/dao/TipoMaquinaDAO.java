/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.util.List;
import modelo.TipoMaquinaria;

/**
 *
 * @author Estudiant
 */
public interface TipoMaquinaDAO {
    public boolean insertar(TipoMaquinaria tipo);
    public boolean eliminar(int id);
    public List<TipoMaquinaria> listar();
}