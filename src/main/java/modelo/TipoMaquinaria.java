/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emmanuel
 */
public class TipoMaquinaria {
    // Atributos
    private int codigoTipoMaquinaria;
    private String descripcionMaq;
    
    // Constructor vacío
    public TipoMaquinaria(){}
    
    // Getters y Setters
    public int getCodigoTipoMaquinaria() {
        return codigoTipoMaquinaria;
    }
    public void setCodigoTipoMaquinaria(int codigoTipoMaquinaria) {
        this.codigoTipoMaquinaria = codigoTipoMaquinaria;
    }
    public String getDescripcionMaq() {
        return descripcionMaq;
    }
    public void setDescripcionMaq(String descripcionMaq) {
        this.descripcionMaq = descripcionMaq;
    }
}
