package com.totalcross.ui;

import com.totalcross.entity.Cliente;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.DocumentoCliente;
import com.totalcross.util.Header;
import com.totalcross.util.ListarClientesComponente;

import totalcross.ui.Container;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class ListarTodosClientes extends Container {

	private DocumentoCliente documentoCliente;
	private MethodButton btnFiltrar;
	private ListarClientesComponente listaClientes;
	private Cliente clienteSelecionado = null;

	public ListarTodosClientes() {
	}

	public int getLeft() {
		return LEFT + 30;
	}

	public void initUI() {

		Header header = new Header("<", "Listar clientes", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		documentoCliente = new DocumentoCliente("Digite CPF/CNPJ para filtrar:");
		add(documentoCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		listaClientes = new ListarClientesComponente();
		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, FILL - 65);

		listaClientes.carregarClientes();

		botaoFiltrar();
		registrarListenerLista();
	}

	public void botaoFiltrar() {
		btnFiltrar = new MethodButton("Filtrar");
		add(btnFiltrar, RIGHT - 30, AFTER + 10, DP + 75, DP + 35);
	}


	private void registrarListenerLista() {
		listaClientes.setClienteSelecionadoListener(new ListarClientesComponente.ClienteSelecionadoListener() {
			@Override
			public void onClienteSelecionado(Cliente cliente) {
				clienteSelecionado = cliente;
				if ("FISICA".equals(cliente.getTipoDePessoa())) {
					documentoCliente.setValue(cliente.getCpf());
				} else {
					documentoCliente.setValue(cliente.getCnpj());
				}
			}
		});
	}

	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnFiltrar) {
				String doc = documentoCliente.getValue();
				if (doc.isEmpty()) {
					listaClientes.carregarClientes();
				} else {
					listaClientes.carregarClientePorDocumento(doc);
				}
			}
			break;

		default:
			break;
		}
	}

}