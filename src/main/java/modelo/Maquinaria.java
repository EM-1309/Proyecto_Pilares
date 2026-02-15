/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Emmanuel
 */
public class Maquinaria {
   // Atributos
    private int codigoMaquinaria;
    private String nombre;
    private int codigoEstadoFK;
    private Date fechaAlta;
    private Date fechaBaja; // Puede ser null
    private int tipoMaquinariaFK;
   
   // Constructor vacío
   public Maquinaria(){}
   
   // Getters y Setters
    public int getCodigoMaquinaria() {
        return codigoMaquinaria;
    }
    public void setCodigoMaquinaria(int codigoMaquinaria) {
        this.codigoMaquinaria = codigoMaquinaria;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getCodigoEstadoFK() {
        return codigoEstadoFK;
    }
    public void setCodigoEstadoFK(int codigoEstadoFK) {
        this.codigoEstadoFK = codigoEstadoFK;
    }
    public Date getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public Date getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    public int getTipoMaquinariaFK() {
        return tipoMaquinariaFK;
    }
    public void setTipoMaquinariaFK(int tipoMaquinariaFK) {
        this.tipoMaquinariaFK = tipoMaquinariaFK;
    }
}
