package dao.impl;

import config.Conexion;
import dao.AveriaDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.Averia;
import modelo.AveriaDetalle;
import modelo.TipoAveria;

public class AveriaDAOImpl implements AveriaDAO {

    private static final String SQL_INSERT =
        "INSERT INTO averia (descInicAveria, fechaInicioAver, fechaAsigTecnico, fechaAcepTecnico, fechaFinalizTecnico, procRealizadoTecnico, usuarioReportaFK, usuarioTecnicoFK, maquinariaFK, tipoAveriaFK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
        "UPDATE averia SET descInicAveria=?, fechaInicioAver=?, fechaAsigTecnico=?, fechaAcepTecnico=?, fechaFinalizTecnico=?, procRealizadoTecnico=?, usuarioReportaFK=?, usuarioTecnicoFK=?, maquinariaFK=?, tipoAveriaFK=? WHERE codigoAveria=?";

    private static final String SQL_DELETE =
        "DELETE FROM averia WHERE codigoAveria=?";

    private static final String SQL_SELECT_BY_ID =
        "SELECT * FROM averia WHERE codigoAveria=?";

    private static final String SQL_SELECT_ALL =
        "SELECT * FROM averia";

    private static final String SQL_SELECT_DETALLE =
        "SELECT a.*, " +
        "m.nombre AS nombreMaquinaria, " +
        "ta.descripcionTipoAv AS descripcionTipoAveria, " +
        "CONCAT(ur.nombre, ' ', ur.apellido) AS nombreOperario, " +
        "CONCAT(ut.nombre, ' ', ut.apellido) AS nombreTecnico " +
        "FROM averia a " +
        "JOIN maquinaria m ON a.maquinariaFK = m.codigoMaquinaria " +
        "JOIN tipo_averia ta ON a.tipoAveriaFK = ta.codigoTipoAveria " +
        "JOIN usuario ur ON a.usuarioReportaFK = ur.codigoUsuario " +
        "LEFT JOIN usuario ut ON a.usuarioTecnicoFK = ut.codigoUsuario";

    private static final String SQL_ASIGNAR_TECNICO =
        "UPDATE averia SET usuarioTecnicoFK=?, fechaAsigTecnico=NOW() WHERE codigoAveria=?";

    private static final String SQL_LISTAR_TIPOS =
        "SELECT * FROM tipo_averia";

    // ================= INSERT =================
    @Override
    public void insertar(Averia a) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setString(1, a.getDescInicAveria());
            ps.setTimestamp(2, a.getFechaInicioAver());

            // ⚠️ CLAVE → evitar crasheo por NULL
            ps.setNull(3, Types.TIMESTAMP);
            ps.setNull(4, Types.TIMESTAMP);
            ps.setNull(5, Types.TIMESTAMP);

            ps.setString(6, a.getProcRealizadoTecnico());
            ps.setInt(7, a.getUsuarioReportaFK());

            // 🔥 FIX DEFINITIVO DEL ERROR
            if (a.getUsuarioTecnicoFK() != null) {
                ps.setInt(8, a.getUsuarioTecnicoFK());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            ps.setInt(9, a.getMaquinariaFK());
            ps.setInt(10, a.getTipoAveriaFK());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // 👈 IMPORTANTE para ver el error real
        }
    }

    // ================= UPDATE =================
    @Override
    public void actualizar(Averia a) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

            ps.setString(1, a.getDescInicAveria());
            ps.setTimestamp(2, a.getFechaInicioAver());

            if (a.getFechaAsigTecnico() != null)
                ps.setTimestamp(3, a.getFechaAsigTecnico());
            else
                ps.setNull(3, Types.TIMESTAMP);

            if (a.getFechaAcepTecnico() != null)
                ps.setTimestamp(4, a.getFechaAcepTecnico());
            else
                ps.setNull(4, Types.TIMESTAMP);

            if (a.getFechaFinalizTecnico() != null)
                ps.setTimestamp(5, a.getFechaFinalizTecnico());
            else
                ps.setNull(5, Types.TIMESTAMP);

            ps.setString(6, a.getProcRealizadoTecnico());
            ps.setInt(7, a.getUsuarioReportaFK());

            if (a.getUsuarioTecnicoFK() != null)
                ps.setInt(8, a.getUsuarioTecnicoFK());
            else
                ps.setNull(8, Types.INTEGER);

            ps.setInt(9, a.getMaquinariaFK());
            ps.setInt(10, a.getTipoAveriaFK());
            ps.setInt(11, a.getCodigoAveria());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ================= LISTAR =================
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
            e.printStackTrace();
        }

        return lista;
    }

    // ================= MAP =================
    private Averia mapResultSetToAveria(ResultSet rs) throws SQLException {
        Averia a = new Averia();

        a.setCodigoAveria(rs.getInt("codigoAveria"));
        a.setDescInicAveria(rs.getString("descInicAveria"));
        a.setFechaInicioAver(rs.getTimestamp("fechaInicioAver"));
        a.setFechaAsigTecnico(rs.getTimestamp("fechaAsigTecnico"));
        a.setFechaAcepTecnico(rs.getTimestamp("fechaAcepTecnico"));
        a.setFechaFinalizTecnico(rs.getTimestamp("fechaFinalizTecnico"));
        a.setProcRealizadoTecnico(rs.getString("procRealizadoTecnico"));
        a.setUsuarioReportaFK(rs.getInt("usuarioReportaFK"));

        // 🔥 CLAVE PARA NO PETAR
        int tecnico = rs.getInt("usuarioTecnicoFK");
        if (rs.wasNull()) {
            a.setUsuarioTecnicoFK(null);
        } else {
            a.setUsuarioTecnicoFK(tecnico);
        }

        a.setMaquinariaFK(rs.getInt("maquinariaFK"));
        a.setTipoAveriaFK(rs.getInt("tipoAveriaFK"));

        return a;
    }

    // ================= OTROS =================
    @Override public void eliminar(int id) {}
    @Override public Optional<Averia> buscarPorId(int id) { return Optional.empty(); }
    @Override public List<AveriaDetalle> listarConDetalle() { return new ArrayList<>(); }
    @Override public void asignarTecnico(int c1, int c2) {}
    @Override public List<TipoAveria> listarTiposAveria() { return new ArrayList<>(); }
}