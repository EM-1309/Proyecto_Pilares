/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Timestamp;

/**
 *
 * @author Mario
 */
public class AveriaDetalle {
    // Atributos
    private int codigoAveria;
    private String descInicAveria;
    private Timestamp fechaInicioAver;
    private Timestamp fechaAsigTecnico;
    private Timestamp fechaAcepTecnico;
    private Timestamp fechaFinalizTecnico;
    private String procRealizadoTecnico;

    // Atributos FK
    private int usuarioReportaFK;
    private Integer usuarioTecnicoFK;
    private int maquinariaFK;
    private int tipoAveriaFK;

    // Atributos extras
    private String nombreMaquinaria;
    private String descripcionTipoAveria;
    private String nombreOperario;
    private String nombreTecnico;

    // Getters y Setters
    public int getCodigoAveria() {
        return codigoAveria;
    }
    public void setCodigoAveria(int codigoAveria) {
        this.codigoAveria = codigoAveria;
    }
    public String getDescInicAveria() {
        return descInicAveria;
    }
    public void setDescInicAveria(String descInicAveria) {
        this.descInicAveria = descInicAveria;
    }
    public Timestamp getFechaInicioAver() {
        return fechaInicioAver;
    }
    public void setFechaInicioAver(Timestamp fechaInicioAver) {
        this.fechaInicioAver = fechaInicioAver;
    }
    public Timestamp getFechaAsigTecnico() {
        return fechaAsigTecnico;
    }
    public void setFechaAsigTecnico(Timestamp fechaAsigTecnico) {
        this.fechaAsigTecnico = fechaAsigTecnico;
    }
    public Timestamp getFechaAcepTecnico() {
        return fechaAcepTecnico;
    }
    public void setFechaAcepTecnico(Timestamp fechaAcepTecnico) {
        this.fechaAcepTecnico = fechaAcepTecnico;
    }
    public Timestamp getFechaFinalizTecnico() {
        return fechaFinalizTecnico;
    }
    public void setFechaFinalizTecnico(Timestamp fechaFinalizTecnico) {
        this.fechaFinalizTecnico = fechaFinalizTecnico;
    }
    public String getProcRealizadoTecnico() {
        return procRealizadoTecnico;
    }
    public void setProcRealizadoTecnico(String procRealizadoTecnico) {
        this.procRealizadoTecnico = procRealizadoTecnico;
    }
    public int getUsuarioReportaFK() {
        return usuarioReportaFK;
    }
    public void setUsuarioReportaFK(int usuarioReportaFK) {
        this.usuarioReportaFK = usuarioReportaFK;
    }
    public Integer getUsuarioTecnicoFK() {
        return usuarioTecnicoFK;
    }
    public void setUsuarioTecnicoFK(Integer usuarioTecnicoFK) {
        this.usuarioTecnicoFK = usuarioTecnicoFK;
    }
    public int getMaquinariaFK() {
        return maquinariaFK;
    }
    public void setMaquinariaFK(int maquinariaFK) {
        this.maquinariaFK = maquinariaFK;
    }
    public int getTipoAveriaFK() {
        return tipoAveriaFK;
    }
    public void setTipoAveriaFK(int tipoAveriaFK) {
        this.tipoAveriaFK = tipoAveriaFK;
    }
    public String getNombreMaquinaria() {
        return nombreMaquinaria;
    }
    public void setNombreMaquinaria(String nombreMaquinaria) {
        this.nombreMaquinaria = nombreMaquinaria;
    }
    public String getDescripcionTipoAveria() {
        return descripcionTipoAveria;
    }
    public void setDescripcionTipoAveria(String descripcionTipoAveria) {
        this.descripcionTipoAveria = descripcionTipoAveria;
    }
    public String getNombreOperario() {
        return nombreOperario;
    }
    public void setNombreOperario(String nombreOperario) {
        this.nombreOperario = nombreOperario;
    }
    public String getNombreTecnico() {
        return nombreTecnico;
    }
    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }
}
