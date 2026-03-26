package com.totalcross.ui;

import com.totalcross.ui.button.MethodButton;

import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class CadastrarCliente extends Container {

	private Edit nome, cpfCnpj, telefone, email;
	private ComboBox tipoDePessoa;
	private Label lblCpfCnpj;
	private MethodButton btnVoltar;

	String[] tipo = { "FISICA", "JURIDICA" };

	public CadastrarCliente() {

	}

	public void initUI() {
		Header header = new Header("Cadastro novo cliente");
		add(header, LEFT, TOP);
		tipoDePessoa();
		nome();
		cpfCnpj();
		telefone();
		email();
		botaoCadastrar();
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

	public void tipoDePessoa() {

		Label lblTipo = new Label("Tipo de Pessoa");
		add(lblTipo, getLeft(), TOP + 65);

		ComboBox.usePopupMenu = false;
		tipoDePessoa = new ComboBox(tipo);
		tipoDePessoa.caption = "Selecione seu tipo de Pessoa";
		add(tipoDePessoa, getLeft(), AFTER + 5, getFill(), PREFERRED);
	}

	public void nome() {
		Label lblNome = new Label("Digite seu nome");
		add(lblNome, getLeft(), AFTER + 15);

		nome = new Edit();
		add(nome, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void cpfCnpj() {

		lblCpfCnpj = new Label("Digite seu CPF");
		add(lblCpfCnpj, getLeft(), AFTER + 15, FILL, getTamanhoCampo("Digite seu CNPJ"));

		cpfCnpj = new Edit();
		add(cpfCnpj, getLeft(), AFTER + 5, getFill(), getPreferredEdit());

	}

	public int getTamanhoCampo(String campo) {

		return campo.length();

	}


	public void telefone() {
		Label lblTelefone = new Label("Digite seu telefone");
		add(lblTelefone, getLeft(), AFTER + 15);

		telefone = new Edit();
		add(telefone, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void email() {
		Label lblEmail = new Label("Digite seu email");
		add(lblEmail, getLeft(), AFTER + 15);

		email = new Edit();
		add(email, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void botaoCadastrar() {
		MethodButton btnCadastrar = new MethodButton("Cadastrar");
		add(btnCadastrar, RIGHT - 30, AFTER + 30, DP + 75, DP + 35);
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
			
			if (event.target == btnVoltar) {
				MenuPrincipal menuPrincipal = new MenuPrincipal();
				MainWindow.getMainWindow().swap(menuPrincipal);
			}

			break;

		default:
			break;
		}
	}

}
