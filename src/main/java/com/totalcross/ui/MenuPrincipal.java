package com.totalcross.ui;

import com.totalcross.ui.button.MethodButton;

import totalcross.ui.Container;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class MenuPrincipal extends Container {

	private MethodButton cadastrarButton, buscarTodosButton, buscarPorIdButton, atualizarButton, deletarButton;

	private static int comprimento = 175;
	private static int altura = 35;

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
			} else if (event.target == buscarTodosButton) {
				ListarTodosClientes listarTodosClientes = new ListarTodosClientes();
				MainWindow.getMainWindow().swap(listarTodosClientes);
			} else if (event.target == buscarPorIdButton) {
				ListarCliente listarCliente = new ListarCliente();
				MainWindow.getMainWindow().swap(listarCliente);
			} else if (event.target == atualizarButton) {
				AtualizarCliente atualizarCliente = new AtualizarCliente();
				MainWindow.getMainWindow().swap(atualizarCliente);
			} else if (event.target == deletarButton) {
				DeletarCliente deletarCliente = new DeletarCliente();
				MainWindow.getMainWindow().swap(deletarCliente);
			}

				break;

		default:
			break;
		}
		super.onEvent(event);
	}

}
