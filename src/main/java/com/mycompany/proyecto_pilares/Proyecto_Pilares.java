package com.mycompany.proyecto_pilares;

import config.Conexion;
import java.sql.Connection;

/**
 * @author konatash
 */
public class Proyecto_Pilares {

    public static void main(String[] args) {
        // Probar la conexión a la base de datos
        Connection conexion = Conexion.getConexion();
        
        if (conexion != null) {
            System.out.println("¡Conexión establecida correctamente!");
            // Aquí puedes agregar más lógica de tu aplicación
        } else {
            System.out.println("Error: No se pudo establecer la conexión.");
        }
        
        // Recuerda cerrar la conexión cuando ya no la necesites
        try {
            if (conexion != null && !conexion.isClosed()) {
                //conexion.close();
                //System.out.println("Conexión cerrada.");
            }
        } catch (Exception e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}