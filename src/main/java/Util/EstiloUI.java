/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

/**
 *
 * @author emmnavmoj
 */
public class EstiloUI {
    // Colores
    public static final Color COLOR_FONDO = new Color(245, 247, 250);
    public static final Color COLOR_PANEL = Color.WHITE;
    public static final Color COLOR_PRIMARIO = new Color(33, 74, 128);
    public static final Color COLOR_SECUNDARIO = new Color(52, 152, 219);
    public static final Color COLOR_EXITO = new Color(46, 204, 113);
    public static final Color COLOR_PELIGRO = new Color(231, 76, 60);
    public static final Color COLOR_TEXTO = new Color(44, 62, 80);

    // Tipo de letra
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);

    // Método para aplicar color en una ventana
    public static void aplicarVentana(JFrame frame) {
        frame.getContentPane().setBackground(COLOR_FONDO);
        frame.setLocationRelativeTo(null);
    }

    // Método para aplicar color en un panel
    public static void aplicarPanel(JPanel panel) {
        panel.setBackground(COLOR_PANEL);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    // Método para aplicar color y tipo de letra a un label (titulo)
    public static void aplicarTitulo(JLabel label) {
        label.setFont(FUENTE_TITULO);
        label.setForeground(COLOR_PRIMARIO);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Método aplicar color y tipo de letra a un label 
    public static void aplicarTexto(JLabel label) {
        label.setFont(FUENTE_NORMAL);
        label.setForeground(COLOR_TEXTO);
    }

    // Aplicar color y tipo de letra a un campo (JTextField)
    public static void aplicarCampo(JTextField field) {
        field.setFont(FUENTE_NORMAL);
        field.setForeground(COLOR_TEXTO);
    }

    // Métodos para aplicar color y tipo de letra a un botón
    public static void aplicarBotonPrimario(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    public static void aplicarBotonSecundario(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_SECUNDARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    public static void aplicarBotonExito(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_EXITO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    public static void aplicarBotonPeligro(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setBackground(COLOR_PELIGRO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }

    // Método para aplicar color y tipo de letra a una tabla
    public static void aplicarTabla(JTable tabla) {
        tabla.setFont(FUENTE_NORMAL);
        tabla.setRowHeight(28);
        tabla.setForeground(COLOR_TEXTO);
        tabla.setGridColor(new Color(220, 220, 220));
        tabla.setSelectionBackground(new Color(214, 234, 248));
        tabla.setSelectionForeground(COLOR_TEXTO);

        JTableHeader header = tabla.getTableHeader();
        header.setFont(FUENTE_BOTON);
        header.setBackground(COLOR_PRIMARIO);
        header.setForeground(Color.WHITE);
    }
    
    public static void aplicarTituloDashboard(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 26));
        label.setForeground(COLOR_PRIMARIO);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    public static void aplicarBotonMenu(JButton boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    
    public static void aplicarBotonCerrarSesion(JMenuItem boton) {
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(COLOR_PELIGRO);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    
    public static void aplicarPanelDashboard(JPanel panel) {
        panel.setBackground(COLOR_PANEL);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
    }
    
    public static void aplicarSubtitulo(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(COLOR_TEXTO);
    }
    
    public static void aplicarMenuBar(JMenuBar menuBar) {
        menuBar.setBackground(COLOR_PRIMARIO);
        menuBar.setForeground(Color.WHITE);
        menuBar.setPreferredSize(new Dimension(0, 35));
    }
    
    public static void aplicarMenu(JMenu menu) {
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menu.setForeground(Color.WHITE);
        menu.setOpaque(false);
    }
    
    public static void aplicarMenuItem(JMenuItem item) {
        item.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        item.setBackground(Color.WHITE);
        item.setForeground(COLOR_TEXTO);
        item.setPreferredSize(new Dimension(180, 28));
    }
    
    public static void aplicarComboBox(javax.swing.JComboBox<?> combo) {
        combo.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        combo.setBackground(java.awt.Color.WHITE);
        combo.setForeground(COLOR_TEXTO);
    }
}
