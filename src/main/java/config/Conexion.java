/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package config;

import java.sql.Connection; 
import java.sql.DriverManager; 
import java.sql.SQLException; 

/**
 *
 * @author konatasht
 */
public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_averias_lospilares?serverTimezone=UTC"; 
    // USUARIO por defecto en XAMPP/WAMP suele ser "root" 
    private static final String USER = "root";
    private static final String PASSWORD = "";  
    public static Connection getConexion() { 
        Connection con = null; 
        try { 
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("¡Conexión exitosa a gestion_averias_lospilares!");
        } catch (ClassNotFoundException e) { 
            System.out.println("Error: No se encontró el driver de MySQL."); 
            e.printStackTrace(); 
        } catch (SQLException e) { 
            System.out.println("Error de SQL al conectar."); 
            e.printStackTrace(); 
        } 
        return con; 
    } 
}
