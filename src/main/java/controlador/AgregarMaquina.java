package controlador;

import dao.MaquinariaDAO;
import dao.impl.MaquinariaDAOImpl;
import modelo.Maquinaria;
import vista.VistaAgregarMaquina;
import vista.VistaMaquinas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

public class AgregarMaquina {

    private VistaAgregarMaquina vista;
    private MaquinariaDAO dao;

    public AgregarMaquina(VistaAgregarMaquina vistaAgregar, MaquinariaDAO dao) {
        this.vista = vistaAgregar;
        this.dao = dao;

        vista.getBtnCrear().addActionListener(new CrearListener());
        vista.getBtnVolver().addActionListener(new VolverListener());
    }

    // LISTENER PARA CREAR MAQUINA
    class CrearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                String nombre = vista.getTxtNombre();
                String codigoTexto = vista.getTxtCodigo();
                String tipoTexto = vista.getTipoMaquina();

                if (nombre.isEmpty() || codigoTexto.isEmpty()) {
                    vista.mostrarError("Debe completar los campos obligatorios");
                    return;
                }

                int tipoMaquina = convertirTipo(tipoTexto);

                Maquinaria m = new Maquinaria();

                m.setNombre(nombre);
                m.setCodigoEstadoFK(1); // activo
                m.setTipoMaquinariaFK(tipoMaquina);
                m.setFechaAlta(new Date(System.currentTimeMillis()));

                dao.insertar(m);

                vista.mostrarMensaje("Máquina creada correctamente");

                // VOLVER A LA TABLA AUTOMATICAMENTE
                abrirVistaMaquinas();

            } catch (Exception ex) {

                vista.mostrarError("Error al crear la máquina");
                ex.printStackTrace();

            }
        }
    }

    // LISTENER PARA VOLVER
    class VolverListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            abrirVistaMaquinas();

        }
    }

    // METODO PARA ABRIR LA VISTA DE MAQUINAS
    private void abrirVistaMaquinas() {

        VistaMaquinas vistaMaquinas = new VistaMaquinas();
        MaquinariaDAO daoMaquina = new MaquinariaDAOImpl();

        new MaquinaControlador(vistaMaquinas, daoMaquina);

        vistaMaquinas.setVisible(true);
        vista.dispose();
    }

    // CONVERTIR TEXTO A ID DE TIPO
    private int convertirTipo(String tipo) {

        switch (tipo) {

            case "Torno CNC":
                return 1;

            case "Fresadora":
                return 2;

            case "Prensa Hidráulica":
                return 3;

            case "Compresor Industrial":
                return 4;

            default:
                return 1;

        }

    }

}