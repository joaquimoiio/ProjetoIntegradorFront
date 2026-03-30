package com.totalcross.ui;

import com.totalcross.entity.Cliente;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.Header;
import com.totalcross.util.IdCliente;
import com.totalcross.util.ListarClientesComponente;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class ListarTodosClientes extends Container {

	private IdCliente idCliente;
	private MethodButton btnFiltrar;
	private ListarClientesComponente listaClientes;

	public ListarTodosClientes() {

	}

	public int getLeft() {
		return LEFT + 30;
	}

	public void initUI() {

		Header header = new Header("<", "Listar clientes", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		idCliente = new IdCliente("Digite o id do Cliente para filtrar:");
		add(idCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		listaClientes = new ListarClientesComponente();
		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, FILL - 65);

		try {
			verificarId();
		} catch (InvalidNumberException e) {
			e.printStackTrace();
		}

		botaoFiltrar();

	}

	public void botaoFiltrar() {
		btnFiltrar = new MethodButton("Filtrar");
		add(btnFiltrar, RIGHT - 30, AFTER + 10, DP + 75, DP + 35);
	}

	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnFiltrar) {
				try {
					verificarId();
				} catch (InvalidNumberException e) {
					e.printStackTrace();
				}
			}

			break;

		default:
			break;
		}
	}
	
	public Cliente clienteFinter() throws InvalidNumberException {
		Cliente cliente = new Cliente();
		cliente.setId(idCliente.getValue());
		return cliente;
	}

	public void verificarId() throws InvalidNumberException {
		if (idCliente.getValue() == 0) {
			listaClientes.carregarClientes();
		} else {
			listaClientes.carregarClientesPorId(clienteFinter());
		}
	}



}
