/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Estado;
import config.Conexion; // Ajusta a tu paquete de conexión
import dao.EstadoDAO;

public class EstadoDAOImpl implements EstadoDAO {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public boolean insertar(Estado estado) {
        String sql = "INSERT INTO estados (descripcionEstado) VALUES (?)";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado.getDescripcionEstado());
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
        String sql = "DELETE FROM estados WHERE codigoEstado = ?";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            // El código 1451 es el error de MySQL para "ForeignKey Constraint"
            if (e.getErrorCode() == 1451) {
                System.err.println("No se puede eliminar: El estado está en uso.");
                // Lanzamos una excepción personalizada o retornamos false
            }
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public List<Estado> listar() {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT * FROM estados";
        try {
            con = Conexion.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Estado e = new Estado();
                e.setCodigoEstado(rs.getInt("codigoEstado"));
                e.setDescripcionEstado(rs.getString("descripcionEstado"));
                lista.add(e);
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

