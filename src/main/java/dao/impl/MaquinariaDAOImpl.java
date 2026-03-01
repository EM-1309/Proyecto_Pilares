/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

import config.Conexion;
import dao.MaquinariaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types; // Necesario para manejar fechas nulas
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.Maquinaria;

/**
 * CLASE: MaquinariaDAOImpl
 * Implementación de las operaciones CRUD para la tabla 'maquinaria'.
 */
public class MaquinariaDAOImpl implements MaquinariaDAO {

    // --- CONSTANTES SQL ---
    // Asumimos que codigoMaquinaria es AUTO_INCREMENT, por eso no lo ponemos en el INSERT
    private static final String SQL_INSERT = "INSERT INTO maquinaria (nombre, codigoEstadoFK, fechaAlta, fechaBaja, tipoMaquinariaFK) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE maquinaria SET nombre=?, codigoEstadoFK=?, fechaAlta=?, fechaBaja=?, tipoMaquinariaFK=? WHERE codigoMaquinaria=?";
    private static final String SQL_DELETE = "DELETE FROM maquinaria WHERE codigoMaquinaria=?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM maquinaria WHERE codigoMaquinaria=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM maquinaria";

    @Override
    public void insertar(Maquinaria m) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getCodigoEstadoFK());
            ps.setDate(3, m.getFechaAlta()); // java.sql.Date

            // MANEJO DE FECHA DE BAJA (Puede ser NULL)
            // Si la máquina es nueva, seguramente no tenga fecha de baja, así que hay que insertar un NULL en la BD.
            if (m.getFechaBaja() != null) {
                ps.setDate(4, m.getFechaBaja());
            } else {
                ps.setNull(4, Types.DATE); // Insertamos un NULL de SQL
            }

            ps.setInt(5, m.getTipoMaquinariaFK());

            ps.executeUpdate();
            System.out.println("Maquinaria insertada correctamente: " + m.getNombre());

        } catch (SQLException e) {
            System.err.println("Error al insertar maquinaria: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Maquinaria m) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getCodigoEstadoFK());
            ps.setDate(3, m.getFechaAlta());

            // Revisamos si hay fecha de baja o no
            if (m.getFechaBaja() != null) {
                ps.setDate(4, m.getFechaBaja());
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setInt(5, m.getTipoMaquinariaFK());
            
            // EL WHERE (ID) va al final
            ps.setInt(6, m.getCodigoMaquinaria());

            ps.executeUpdate();
            System.out.println("Maquinaria actualizada correctamente ID: " + m.getCodigoMaquinaria());

        } catch (SQLException e) {
            System.err.println("Error al actualizar maquinaria: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Maquinaria eliminada ID: " + id);

        } catch (SQLException e) {
            System.err.println("Error al eliminar maquinaria: " + e.getMessage());
        }
    }

    @Override
    public Optional<Maquinaria> buscarPorId(int id) {
        Maquinaria m = null;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    m = mapResultSetToMaquinaria(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error buscando maquinaria por ID: " + e.getMessage());
        }
        return Optional.ofNullable(m);
    }

    @Override
    public List<Maquinaria> listar() {
        List<Maquinaria> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToMaquinaria(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error listando maquinaria: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Convierte una fila de la BD (ResultSet) a un objeto Maquinaria.
     */
    private Maquinaria mapResultSetToMaquinaria(ResultSet rs) throws SQLException {
        Maquinaria m = new Maquinaria();
        m.setCodigoMaquinaria(rs.getInt("codigoMaquinaria"));
        m.setNombre(rs.getString("nombre"));
        m.setCodigoEstadoFK(rs.getInt("codigoEstadoFK"));
        m.setFechaAlta(rs.getDate("fechaAlta"));
        m.setFechaBaja(rs.getDate("fechaBaja")); // Si en BD es NULL, aquí llega null
        m.setTipoMaquinariaFK(rs.getInt("tipoMaquinariaFK"));
        return m;
    }
}