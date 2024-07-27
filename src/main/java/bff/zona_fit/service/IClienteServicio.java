package bff.zona_fit.service;

import bff.zona_fit.model.Cliente;
import java.util.List;


public interface IClienteServicio {
    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Integer idCliente);
    public void guardarCliente(Cliente cliente);
    public void eliminarCliente(Cliente cliente);
}
