/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Usuario;
import vista.ReportarAveriaView;


/**
 *
 * @author konatasht
 */
public class AveriaControladorReportar {
    private ReportarAveriaView rA;
    private Usuario usuarioActual;
    
    public AveriaControladorReportar(ReportarAveriaView rA, Usuario usuarioActual){
        this.rA = rA;
        this.usuarioActual = usuarioActual;
    }
    
}
