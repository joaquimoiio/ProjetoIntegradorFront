package com.totalcross.ui;


import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.Header;
import com.totalcross.util.IdCliente;
import com.totalcross.util.ListarClientesComponente;

import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class AtualizarCliente extends Container {

	private Edit email, telefone;

	public AtualizarCliente() {

	}

	public void initUI() {

		Header header = new Header("<", "Atualizar cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		IdCliente idCliente = new IdCliente("Digite o id do Cliente para filtrar:");
		add(idCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		ListarClientesComponente listaClientes = new ListarClientesComponente();
		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, PARENTSIZE + 40);

		atualizarEmail();
		atualizarTelefone();
		botaoAtualizar();

	}

	public int getLeft() {
		return LEFT + 30;
	}

	public int getFill() {
		return FILL - 30;
	}

	public int getPreferredEdit() {
		return PREFERRED - 20;
	}

	public void atualizarEmail() {

		Label lblEmail = new Label("Digite o email que voce quer atualizar");
		add(lblEmail, getLeft(), AFTER + 10);

		email = new Edit();
		add(email, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	private boolean validarEmail(String texto) {
		return texto.contains("@") && texto.contains(".");
	}

	public void atualizarTelefone() {
		Label lblTelefone = new Label("Digite o telefone que voce quer atualizar");
		add(lblTelefone, getLeft(), AFTER + 15);

		telefone = new Edit();
		add(telefone, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void botaoAtualizar() {
		MethodButton btnAtualizar = new MethodButton("Atualizar");
		add(btnAtualizar, RIGHT - 30, AFTER + 30, DP + 75, DP + 35);
	}

}
