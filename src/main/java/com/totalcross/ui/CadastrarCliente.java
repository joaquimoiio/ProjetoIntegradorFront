package com.totalcross.ui;

import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class CadastrarCliente extends Container {

	private Edit nome, cpfCnpj, telefone, email;
	private ComboBox tipoDePessoa;
	private Label lblCpfCnpj;

	String[] tipo = { "FISICA", "JURIDICA" };

	public CadastrarCliente() {

	}

	public void initUI() {
		nome();
		tipoDePessoa();
		cpfCnpj();
		telefone();
		email();
		botaoCadastrar();
	}

	public void nome() {
		Label lblNome = new Label("Digite seu nome");
		add(lblNome, LEFT, AFTER + 15);

		nome = new Edit();
		add(nome, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	public void tipoDePessoa() {

		Label lblTipo = new Label("Tipo de Pessoa");
		add(lblTipo, LEFT, TOP + 20);

		ComboBox.usePopupMenu = false;
		tipoDePessoa = new ComboBox(tipo);
		tipoDePessoa.caption = "Selecione seu tipo de Pessoa";
		add(tipoDePessoa, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	public void cpfCnpj() {

		lblCpfCnpj = new Label("Digite seu CPF    ");
		add(lblCpfCnpj, LEFT, AFTER + 15);

		cpfCnpj = new Edit();
		add(cpfCnpj, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == tipoDePessoa) {
				if (tipoDePessoa.getSelectedIndex() == 0) {
					lblCpfCnpj.setText("Digite seu CPF");
				} else {
					lblCpfCnpj.setText("Digite seu CNPJ");
				}
				repaintNow();
			}
			
			
			
			break;

		default:
			break;
		}
	}

	public void telefone() {
		Label lblTelefone = new Label("Digite seu telefone");
		add(lblTelefone, LEFT, AFTER + 15);

		telefone = new Edit();
		add(telefone, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	public void email() {
		Label lblEmail = new Label("Digite seu email");
		add(lblEmail, LEFT, AFTER + 15);

		email = new Edit();
		add(email, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	public void botaoCadastrar() {
		BasicButton btnCadastrar = new BasicButton("Cadastrar");
		add(btnCadastrar, CENTER, AFTER + 30, DP + 150, DP + 60);
	}

}
