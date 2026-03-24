/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import config.Conexion;
import dao.TipoMaquinaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.TipoMaquinaria;

/**
 *
 * @author Estudiant
 */
public class TipoMaquinaDAOImpl implements TipoMaquinaDAO {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    
    @Override
    public boolean insertar(TipoMaquinaria tipo) {
        String sql = "INSERT INTO estados (descripcionEstado) VALUES (?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, tipo.getDescripcionMaq());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar estado: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM tipo_maquinaria WHERE codigoTipoMaquinaria = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // Si el tipo está asignado a una máquina, el motor de BD lanzará este error
            if (e.getErrorCode() == 1451) {
                return false; 
            }
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    @Override
    public List<TipoMaquinaria> listar() {
        List<TipoMaquinaria> lista = new ArrayList<>();
        String sql = "SELECT * FROM estados";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TipoMaquinaria t = new TipoMaquinaria();
                t.setCodigoTipoMaquinaria(rs.getInt("codigoTipoMaquinaria"));
                t.setDescripcionMaq(rs.getString("descripcionMaq"));
                lista.add(t);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estados: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }
    
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
}
