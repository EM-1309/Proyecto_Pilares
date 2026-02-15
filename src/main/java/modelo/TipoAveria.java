/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emmanuel
 */
public class TipoAveria {
    // Atributos
    private int codigoTipoAveria;
    private String descripcionTipoAv;
    private int tiempoPromRepar;
    
    // Constructor vacío
    public TipoAveria(){}
    
    // Getters y Setters
    public int getCodigoTipoAveria() {
        return codigoTipoAveria;
    }
    public void setCodigoTipoAveria(int codigoTipoAveria) {
        this.codigoTipoAveria = codigoTipoAveria;
    }
    public String getDescripcionTipoAv() {
        return descripcionTipoAv;
    }
    public void setDescripcionTipoAv(String descripcionTipoAv) {
        this.descripcionTipoAv = descripcionTipoAv;
    }
    public int getTiempoPromRepar() {
        return tiempoPromRepar;
    }
    public void setTiempoPromRepar(int tiempoPromRepar) {
        this.tiempoPromRepar = tiempoPromRepar;
    } 
}
