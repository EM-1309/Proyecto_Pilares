/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import config.Conexion;
import dao.AveriaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; // Importante para las fechas con hora
import java.sql.Types;     // Importante para los nulos (NULL)
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.Averia;

/**
 * CLASE: AveriaDAOImpl
 * Gestiona las operaciones CRUD para la tabla 'averia'.
 * Corregido para coincidir exactamente con la estructura de la base de datos (sin campo estado).
 */
public class AveriaDAOImpl implements AveriaDAO {

    // --- CONSTANTES SQL ---
    
    private static final String SQL_INSERT = "INSERT INTO averia (descInicAveria, fechaInicioAver, fechaAsigTecnico, fechaAcepTecnico, fechaFinalizTecnico, procRealizadoTecnico, usuarioReportaFK, usuarioTecnicoFK, maquinariaFK, tipoAveriaFK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String SQL_UPDATE = "UPDATE averia SET descInicAveria=?, fechaInicioAver=?, fechaAsigTecnico=?, fechaAcepTecnico=?, fechaFinalizTecnico=?, procRealizadoTecnico=?, usuarioReportaFK=?, usuarioTecnicoFK=?, maquinariaFK=?, tipoAveriaFK=? WHERE codigoAveria=?";
    
    private static final String SQL_DELETE = "DELETE FROM averia WHERE codigoAveria=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM averia WHERE codigoAveria=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM averia";

    @Override
    public void insertar(Averia a) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            // 1. Descripción
            ps.setString(1, a.getDescInicAveria());
            
            // 2. Fecha Inicio
            ps.setTimestamp(2, a.getFechaInicioAver());

            // 3. Fecha Asignación Técnico (Puede ser NULL)
            if (a.getFechaAsigTecnico() != null) {
                ps.setTimestamp(3, a.getFechaAsigTecnico());
            } else {
                ps.setNull(3, Types.TIMESTAMP);
            }

            // 4. Fecha Aceptación Técnico (Puede ser NULL)
            if (a.getFechaAcepTecnico() != null) {
                ps.setTimestamp(4, a.getFechaAcepTecnico());
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            // 5. Fecha Finalización (Puede ser NULL)
            if (a.getFechaFinalizTecnico() != null) {
                ps.setTimestamp(5, a.getFechaFinalizTecnico());
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            // 6. Procedimiento realizado (Puede ser NULL o texto)
            ps.setString(6, a.getProcRealizadoTecnico());

            // 7. Usuario que reporta (FK)
            ps.setInt(7, a.getUsuarioReportaFK());

            // 8. Usuario Técnico (FK - Puede ser NULL si nadie la ha cogido)
            if (a.getUsuarioTecnicoFK() > 0) {
                ps.setInt(8, a.getUsuarioTecnicoFK());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            // 9. Maquinaria (FK)
            ps.setInt(9, a.getMaquinariaFK());

            // 10. Tipo Avería (FK)
            ps.setInt(10, a.getTipoAveriaFK());

            ps.executeUpdate();
            System.out.println("Avería insertada correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar avería: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar(Averia a) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

            // Asignamos los mismos valores que en el insertar
            ps.setString(1, a.getDescInicAveria());
            ps.setTimestamp(2, a.getFechaInicioAver());

            if (a.getFechaAsigTecnico() != null) ps.setTimestamp(3, a.getFechaAsigTecnico());
            else ps.setNull(3, Types.TIMESTAMP);

            if (a.getFechaAcepTecnico() != null) ps.setTimestamp(4, a.getFechaAcepTecnico());
            else ps.setNull(4, Types.TIMESTAMP);

            if (a.getFechaFinalizTecnico() != null) ps.setTimestamp(5, a.getFechaFinalizTecnico());
            else ps.setNull(5, Types.TIMESTAMP);

            ps.setString(6, a.getProcRealizadoTecnico());
            ps.setInt(7, a.getUsuarioReportaFK());

            if (a.getUsuarioTecnicoFK() > 0) ps.setInt(8, a.getUsuarioTecnicoFK());
            else ps.setNull(8, Types.INTEGER);

            ps.setInt(9, a.getMaquinariaFK());
            ps.setInt(10, a.getTipoAveriaFK());

            // 11. El ID para el WHERE (muy importante)
            ps.setInt(11, a.getCodigoAveria());

            ps.executeUpdate();
            System.out.println("Avería actualizada correctamente ID: " + a.getCodigoAveria());

        } catch (SQLException e) {
            System.err.println("Error al actualizar avería: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Avería eliminada ID: " + id);

        } catch (SQLException e) {
            System.err.println("Error al eliminar avería: " + e.getMessage());
        }
    }

    @Override
    public Optional<Averia> buscarPorId(int id) {
        Averia averia = null;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    averia = mapResultSetToAveria(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando avería por ID: " + e.getMessage());
        }
        return Optional.ofNullable(averia);
    }

    @Override
    public List<Averia> listar() {
        List<Averia> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToAveria(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error listando averías: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Convierte una fila del ResultSet a un objeto Averia.
     * Mapea EXCLUSIVAMENTE las columnas que existen en tu imagen.
     */
    private Averia mapResultSetToAveria(ResultSet rs) throws SQLException {
        Averia a = new Averia();
        
        a.setCodigoAveria(rs.getInt("codigoAveria"));
        a.setDescInicAveria(rs.getString("descInicAveria"));
        
        // Timestamps (pueden venir nulos de la BD, pero JDBC lo maneja devolviendo null)
        a.setFechaInicioAver(rs.getTimestamp("fechaInicioAver"));
        a.setFechaAsigTecnico(rs.getTimestamp("fechaAsigTecnico"));
        a.setFechaAcepTecnico(rs.getTimestamp("fechaAcepTecnico"));
        a.setFechaFinalizTecnico(rs.getTimestamp("fechaFinalizTecnico"));
        
        a.setProcRealizadoTecnico(rs.getString("procRealizadoTecnico"));
        
        // Claves foráneas
        a.setUsuarioReportaFK(rs.getInt("usuarioReportaFK"));
        
        // Si el técnico es NULL en la BD, rs.getInt devuelve 0. 
        // Eso está bien para tu modelo Java.
        a.setUsuarioTecnicoFK(rs.getInt("usuarioTecnicoFK"));
        
        a.setMaquinariaFK(rs.getInt("maquinariaFK"));
        a.setTipoAveriaFK(rs.getInt("tipoAveriaFK"));
        
        return a;
    }
}