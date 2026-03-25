

import modelo.Usuario;
import vista.EditarAveriasView;

public class EditarAveriaControlador {

    private EditarAveriasView vista;
    private Usuario usuarioActual;
    private int idAveria;

    public EditarAveriaControlador(EditarAveriasView vista, Usuario usuarioActual, int idAveria){
        this.vista = vista;
        this.usuarioActual = usuarioActual;
        this.idAveria = idAveria;

        // luego aquí cargas datos si quieres
    }
}