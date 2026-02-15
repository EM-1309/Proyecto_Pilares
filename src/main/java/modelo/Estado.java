/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emmanuel
 */
public class Estado {
    // Atributos
    private int codigoEstado;
    private String descripcionEstado;
    
    // Constructor vacío
    public Estado(){}
    
    // Getters y Setters
    public int getCodigoEstado() {
        return codigoEstado;
    }
    public void setCodigoEstado(int codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
    public String getDescripcionEstado() {
        return descripcionEstado;
    }
    public void setDescripcionEstado(String descripcionEstado) {
        this.descripcionEstado = descripcionEstado;
    }
}
