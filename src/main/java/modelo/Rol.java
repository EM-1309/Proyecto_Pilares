/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emmanuel
 */
public class Rol {
    // Atributos
    private int codigoRol;
    private String descripcionRol;
    
    // Constructor vacío
    public Rol(){}
    
    // Getters y Setters
    public int getCodigoRol() {
        return codigoRol;
    }
    public void setCodigoRol(int codigoRol) {
        this.codigoRol = codigoRol;
    }
    public String getDescripcionRol() {
        return descripcionRol;
    }
    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }
}
