package com.totalcross.ui;

import totalcross.ui.Container;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class MenuPrincipal extends Container {

	MethodButton cadastrarButton, buscarTodosButton, buscarPorIdButton, atualizarButton, deletarButton;

	private static int comprimento = 225;
	private static int altura = 40;

	public MenuPrincipal() {
	}

	public void initUI() {
		cadastrarCliente();
		ListarTodosClientes();
		ListarCliente();
		atualizarCliente();
		deletarCliente();
	}

	public void cadastrarCliente() {
		cadastrarButton = new MethodButton("Cadastrar Cliente");
		add(cadastrarButton, CENTER, CENTER - 120, comprimento, altura);

	}

	public void ListarTodosClientes() {
		buscarTodosButton = new MethodButton("Listar Todos Clientes");
		add(buscarTodosButton, CENTER, CENTER - 60, comprimento, altura);
	}

	public void ListarCliente() {
		buscarPorIdButton = new MethodButton("Listar Clientes");
		add(buscarPorIdButton, CENTER, CENTER, comprimento, altura);
	}

	public void atualizarCliente() {
		atualizarButton = new MethodButton("Atualizar Cliente");
		add(atualizarButton, CENTER, CENTER + 60, comprimento, altura);
	}

	public void deletarCliente() {
		deletarButton = new MethodButton("Deletar Cliente");
		add(deletarButton, CENTER, CENTER + 120, comprimento, altura);
	}

	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == cadastrarButton) {

				CadastrarCliente cadastrarCliente = new CadastrarCliente();
				MainWindow.getMainWindow().swap(cadastrarCliente);
			}

				break;

		default:
			break;
		}
		super.onEvent(event);
	}

}
