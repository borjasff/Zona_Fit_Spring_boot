package bff.zona_fit;

import bff.zona_fit.model.Cliente;
import bff.zona_fit.service.IClienteServicio;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


//@SpringBootApplication
public class ZonaFitApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);
	String nl= System.lineSeparator();

	public static void main(String[] args) {
		logger.info( "Iniciando la aplicación" );
		//Inicia Spring
		SpringApplication.run(ZonaFitApplication.class, args);
		logger.info("Finalizando la aplicación" );
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();

	}

	private void zonaFitApp(){
		var consola = new Scanner(System.in);
		var salir = false;
		logger.info(nl + "**** Aplicación Zona Fit ****" + nl);
		while (!salir){
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info("");

		}

	}
	private int mostrarMenu(Scanner consola){
		logger.info(nl);
		logger.info("""
						**** Zona Fit Menu ****
						1.Listar Clientes
						2.Buscar Cliente
						3.Agregar Cliente
						4.Modificar Cliente
						5.Eliminar Cliente
						6.Salir
						Selecciona una opcion:\s""" + nl);
		var opcion = Integer.parseInt(consola.nextLine());
		return opcion;
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion){
		var salir = false;
		switch (opcion){
			case 1 -> {
				logger.info(nl + "Listado de clientes: "+ nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
			}
			case 2 -> {
				logger.info(nl + "Buscar cliente por Id: "+ nl);
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if(cliente != null){
					logger.info("Cliente encontrado" + cliente + nl);
				} else {
					logger.info("Cliente NO encontrado" + cliente + nl);
				}
			}
			case 3 -> {
				logger.info(nl + "Agregar cliente: "+ nl);
				logger.info(nl + "Dime el nombre del cliente: "+ nl);
				var nombre = consola.nextLine();
				logger.info(nl + "Dime el apellido del cliente: "+ nl);
				var apellido = consola.nextLine();
				logger.info(nl + "Dime la membresia del cliente: "+ nl);
				var membresia = Integer.parseInt(consola.nextLine());
				var clienteNuevo = new Cliente();
				clienteNuevo.setNombre(nombre);
				clienteNuevo.setApellido(apellido);
				clienteNuevo.setMembresia(membresia);
				clienteServicio.guardarCliente(clienteNuevo);
				logger.info("Cliente Agregado a la base de datos: " + clienteNuevo + nl);

			}
			case 4 -> {
				logger.info(nl + "Actualizar cliente: "+ nl);
				var idCliente = Integer.parseInt(consola.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if(cliente != null){
					logger.info(nl + "Dime el nombre del cliente: "+ nl);
					var nombre = consola.nextLine();
					logger.info(nl + "Dime el apellido del cliente: "+ nl);
					var apellido = consola.nextLine();
					logger.info(nl + "Dime la membresia del cliente: "+ nl);
					var membresia = Integer.parseInt(consola.nextLine());
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente actualizado: " + cliente + nl);

				} else {
					logger.info("Cliente no actualizado/encontrado: " + cliente + nl);
				}
			}
			case 5 -> {
				logger.info(nl + "Borrar cliente: "+ nl);
				var idCliente = Integer.parseInt(consola.nextLine());
				var cliente = clienteServicio.buscarClientePorId(idCliente);
				if(cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info("Cliente eliminado: " + cliente + nl);
				} else {
					logger.info("Cliente no eliminado: " + cliente + nl);
				}
			}
			case 6 -> {
				salir = true;
				logger.info("¡Hasta pronto!");
			}
			default -> {
				logger.info("La opción no se encuentra disponible");
			}
		}
		return salir;
	}
}
