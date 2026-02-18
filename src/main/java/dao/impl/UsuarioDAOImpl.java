/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.impl;

// Importamos las clases necesarias para la conexión y manejo de datos
import config.Conexion;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import modelo.Usuario;

/**
 * CLASE: UsuarioDAOImpl
 * OBJETIVO: Esta clase es el "cocinero". Es la que realmente hace el trabajo sucio
 * de hablar con la base de datos MySQL. Implementa las órdenes (métodos) que
 * definimos en la interfaz UsuarioDAO.
 */
public class UsuarioDAOImpl implements UsuarioDAO {

    // --- CONSTANTES SQL ---
    // Definimos las sentencias SQL aquí arriba para no tenerlas dispersas por el código.
    // Los signos de interrogación (?) son huecos que rellenaremos después.
    
    // SQL para guardar un nuevo usuario
    private static final String SQL_INSERT = "INSERT INTO usuario (nombre, apellido, codigoRolFK, telefono, email, password, intentos, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    // SQL para modificar un usuario existente
    private static final String SQL_UPDATE = "UPDATE usuario SET nombre=?, apellido=?, codigoRolFK=?, telefono=?, email=?, password=?, intentos=?, activo=? WHERE codigoUsuario=?";
    
    // SQL para borrar un usuario
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE codigoUsuario=?";
    
    // SQL para buscar un usuario por su clave primaria (ID)
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM usuario WHERE codigoUsuario=?";
    
    // SQL para traer todos los usuarios de la tabla
    private static final String SQL_SELECT_ALL = "SELECT * FROM usuario";
    
    // SQL IMPORTANTE: Para el Login. Busca un usuario que coincida en email, contraseña y que esté ACTIVO (1).
    private static final String SQL_LOGIN = "SELECT * FROM usuario WHERE email=? AND password=? AND activo=1";

    /**
     * MÉTODO INSERTAR
     * Recibe un objeto Usuario y lo guarda en la base de datos.
     */
    @Override
    public void insertar(Usuario u) {
        // Usamos "try-with-resources" (los paréntesis después del try).
        // Esto es muy importante: Cierra la conexión automáticamente al terminar, aunque haya error.
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            // Rellenamos los huecos (?) del SQL con los datos del objeto Usuario.
            // El número indica la posición del interrogante (1 es el primero, 2 el segundo...)
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setInt(3, u.getCodigoRolFK());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPassword());
            ps.setInt(7, u.getIntentos());
            ps.setBoolean(8, u.isActivo());

            // Ejecutamos la orden. executeUpdate() se usa para INSERT, UPDATE o DELETE.
            ps.executeUpdate(); 
            System.out.println("Usuario insertado correctamente.");

        } catch (SQLException e) {
            // Si algo falla (ej. base de datos apagada), mostramos el error.
            System.err.println("Error al insertar usuario: " + e.getMessage());
        }
    }

    /**
     * MÉTODO ACTUALIZAR
     * Modifica los datos de un usuario que ya existe.
     */
    @Override
    public void actualizar(Usuario u) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {

            // Rellenamos los datos nuevos
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setInt(3, u.getCodigoRolFK());
            ps.setString(4, u.getTelefono());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPassword());
            ps.setInt(7, u.getIntentos());
            ps.setBoolean(8, u.isActivo());
            
            // IMPORTANTE: El último interrogante es el ID para el WHERE (para saber a quién actualizar)
            ps.setInt(9, u.getCodigoUsuario());

            ps.executeUpdate(); // Enviamos los cambios a la BD
            System.out.println("Usuario actualizado correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }

    /**
     * MÉTODO ELIMINAR
     * Borra un usuario sabiendo su ID.
     */
    @Override
    public void eliminar(int id) {
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id); // Ponemos el ID en el hueco del WHERE
            ps.executeUpdate(); // Ejecutamos el borrado
            System.out.println("Usuario eliminado (ID: " + id + ")");

        } catch (SQLException e) {
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }
    }

    /**
     * MÉTODO BUSCAR POR ID
     * Devuelve un Optional (una cajita que puede tener un Usuario o estar vacía).
     */
    @Override
    public Optional<Usuario> buscarPorId(int id) {
        Usuario usuario = null; // Empezamos vacíos
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {

            ps.setInt(1, id);
            
            // executeQuery() se usa para SELECT (leer datos). Devuelve un ResultSet (rs).
            // ResultSet es como una tabla excel con los resultados.
            try (ResultSet rs = ps.executeQuery()) {
                // rs.next() mueve el cursor a la primera fila. Si devuelve true, es que encontró algo.
                if (rs.next()) {
                    // Convertimos esa fila de la BD en un objeto Java Usuario
                    usuario = mapResultSetToUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar usuario por ID: " + e.getMessage());
        }
        
        // Devolvemos la cajita (con usuario dentro o vacía si no se encontró)
        return Optional.ofNullable(usuario);
    }

    /**
     * MÉTODO LISTAR
     * Devuelve una lista con TODOS los usuarios.
     */
    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>(); // Preparamos la lista vacía
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // while(rs.next()) sirve para recorrer TODAS las filas que devolvió la consulta
            while (rs.next()) {
                // Por cada fila, creamos un objeto Usuario y lo añadimos a la lista
                lista.add(mapResultSetToUsuario(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista; // Devolvemos la lista llena
    }

    /**
     * MÉTODO LOGIN
     * Este es el que usarás en tu pantalla de acceso.
     */
    @Override
    public Optional<Usuario> login(String email, String password) {
        Usuario usuario = null;
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LOGIN)) {

            // Rellenamos email y password en la consulta SQL
            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                // Si rs.next() es true, significa que encontró un usuario con ese email y esa contraseña
                if (rs.next()) {
                    usuario = mapResultSetToUsuario(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en el login: " + e.getMessage());
        }
        
        // Devolvemos al usuario encontrado (o vacío si las credenciales eran malas)
        return Optional.ofNullable(usuario);
    }

    /**
     * MÉTODO AUXILIAR (Privado)
     * Sirve para no repetir código. Convierte una fila de la BD (ResultSet) en un objeto Java (Usuario).
     * Se llama desde buscarPorId, listar y login.
     */
    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        // Sacamos los datos de las columnas de la BD por su nombre y los metemos en el objeto
        u.setCodigoUsuario(rs.getInt("codigoUsuario"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setCodigoRolFK(rs.getInt("codigoRolFK"));
        u.setTelefono(rs.getString("telefono"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        u.setIntentos(rs.getInt("intentos"));
        u.setUltimoAcceso(rs.getTimestamp("ultimoAcceso"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}