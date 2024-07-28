package bff.zona_fit.gui;

import bff.zona_fit.model.Cliente;
import bff.zona_fit.service.ClienteServicio;
import bff.zona_fit.service.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ZonaFitForma extends JFrame {
    private JPanel PrincipalPanel;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private DefaultTableModel tablaModeloClientes;
    IClienteServicio clienteServicio;
    private Integer idCliente;

    @Autowired
    public ZonaFitForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> {
            guardarCliente();
        });
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                eliminarClienteSeleccionado();
            }
        });
        limpiarButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limpiarFormulario();

            }
        });
    }



    private void iniciarForma(){
        setContentPane(PrincipalPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0, 4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //upload customer list
        listarClientes();
    }

    private void listarClientes() {
        this.tablaModeloClientes.setRowCount(0);
        var clientes = this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] renglonCliente = {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tablaModeloClientes.addRow(renglonCliente);
        });
    }

    private void guardarCliente() {
        if(nombreTexto.getText().equals("")){
            mostrarMnesaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if(membresiaTexto.getText().equals("")){
            mostrarMnesaje("Proporciona una membresia");
            membresiaTexto.requestFocusInWindow();
            return;
        }
        //recuperamos los valores del formulario
        var cliente = new Cliente(this.idCliente,
                nombreTexto.getText(),
                apellidoTexto.getText(),
                Integer.parseInt(membresiaTexto.getText()));

        this.clienteServicio.guardarCliente(cliente);//insert or update
        if(this.idCliente == null){
            mostrarMnesaje("Se agregó un nuevo cliente");
        } else {
            mostrarMnesaje("Se actualizó un cliente existente");
        }
        limpiarFormulario();
        listarClientes();
    }

    private  void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }

    private void eliminarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if (renglon != -1) {
            var id = Integer.parseInt(clientesTabla.getModel().getValueAt(renglon, 0).toString());
            this.idCliente = id;
            //recuperamos los valores del formulario
            var cliente = new Cliente();
            cliente.setId(this.idCliente);
            this.clienteServicio.eliminarCliente(cliente);//insert or update
            if(this.idCliente != null){
                mostrarMnesaje("Se eliminó el cliente");
            } else {
                mostrarMnesaje("No se eliminó el cliente");
            }
            limpiarFormulario();
            listarClientes();
        } else {
            mostrarMnesaje("Debe seleccionar un cliente a eliminar");
        }
    }

    private void limpiarFormulario() {
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        this.idCliente = null;
        this.clientesTabla.getSelectionModel().clearSelection();
    }

    private void mostrarMnesaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
