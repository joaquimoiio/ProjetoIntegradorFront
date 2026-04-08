package com.totalcross.ui;

import java.sql.SQLException;

import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.ErroBox;
import com.totalcross.util.Header;

import totalcross.ui.ComboBox;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.gfx.Color;

public class CadastrarCliente extends Container {

	private Edit nome, cpf, cnpj, telefone, email;
	private ComboBox tipoDePessoa;
	private Label lblCpfCnpj;
	private ClienteService service = new ClienteService();

	String[] tipo = { "FISICA", "JURIDICA" };

	public CadastrarCliente() {
	}

	public void initUI() {
		Header header = new Header("<", "Cadastro cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);
		tipoDePessoa();
		nome();
		cpf();
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
		Label lblTipo = new Label("Selecione tipo de pessoa:");
		add(lblTipo, getLeft(), TOP + 65);

		ComboBox.usePopupMenu = false;
		tipoDePessoa = new ComboBox(tipo);
		tipoDePessoa.setSelectedIndex(0);
		add(tipoDePessoa, getLeft(), AFTER + 5, getFill(), PREFERRED);
	}

	public void nome() {
		Label lblNome = new Label("Digite seu nome");
		add(lblNome, getLeft(), AFTER + 15);

		nome = new Edit();
		add(nome, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void cpf() {
		lblCpfCnpj = new Label("Digite seu CPF");
		add(lblCpfCnpj, getLeft(), AFTER + 15, FILL, getTamanhoCampo("Digite seu CNPJ"));

		cpf = new Edit("999.999.999-99");
		cpf.setMaxLength(11);
		cpf.setMode(Edit.NORMAL, true);
		cpf.setKeyboard(Edit.KBD_NUMERIC);
		cpf.setValidChars("0123456789");
		add(cpf, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public int getTamanhoCampo(String campo) {
		return campo.length();
	}

	public void telefone() {
		Label lblTelefone = new Label("Digite seu telefone");
		add(lblTelefone, getLeft(), AFTER + 15);

		telefone = new Edit("(99) 99999-9999");
		telefone.setMaxLength(11);
		telefone.setKeyboard(Edit.KBD_NUMERIC);
		telefone.setValidChars("0123456789");
		add(telefone, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void email() {
		Label lblEmail = new Label("Digite seu email");
		add(lblEmail, getLeft(), AFTER + 15);

		email = new Edit();
		email.setKeyboard(Edit.KBD_DEFAULT);
		add(email, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void botaoCadastrar() {
		MethodButton btnCadastrar = new MethodButton("Cadastrar");
		add(btnCadastrar, RIGHT - 30, AFTER + 30, DP + 75, DP + 35);
	}

	private void doInsert() {
		try {
			String nomeStr = nome.getTextWithoutMask();
			String tipoStr = tipoDePessoa.getSelectedIndex() == 0 ? "FISICA" : "JURIDICA";

			String cpfStr = "";
			String cnpjStr = "";

			if ("FISICA".equals(tipoStr)) {
				cpfStr = cpf != null ? cpf.getTextWithoutMask() : null;
				cnpjStr = null;
			} else {
				cnpjStr = cnpj != null ? cnpj.getTextWithoutMask() : null;
				cpfStr = null;
			}

			String telefoneStr = telefone.getTextWithoutMask();
			String emailStr = email.getText().trim();

			Cliente cliente = new Cliente();
			cliente.setNomeDoCliente(nomeStr);
			cliente.setTipoDePessoa(tipoStr);
			cliente.setCpf(cpfStr);
			cliente.setCnpj(cnpjStr);
			cliente.setTelefone(telefoneStr);
			cliente.setEmail(emailStr);

			service.cadastrarCliente(cliente);

			CadastrarCliente cadastrarCliente = new CadastrarCliente();
			MainWindow.getMainWindow().swap(cadastrarCliente);

			MessageBox mb = new MessageBox("Atenção!", "Cliente " + nomeStr + " cadastrado com sucesso!");
			mb.setBackForeColors(Color.WHITE, Color.BLACK);
			mb.popup();
		} catch (IllegalArgumentException e) {
			new ErroBox("Atenção!", e.getMessage(), new String[] { "Voltar" }).popup();
		} catch (SQLException e) {
			new ErroBox("Erro ao Cadastrar", "Erro ao salvar no banco de dados!", new String[] { "Voltar" }).popup();
		}
	}

	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == tipoDePessoa) {
				remove(tipoDePessoa.getSelectedIndex() == 0 ? cnpj : cpf);
				if (tipoDePessoa.getSelectedIndex() == 0) {
					lblCpfCnpj.setText("Digite seu CPF");
					cpf = new Edit("999.999.999-99");
					cpf.setMaxLength(11);
					cpf.setKeyboard(Edit.KBD_NUMERIC);
					cpf.setValidChars("0123456789");
					add(cpf, getLeft(), AFTER + 5, getFill(), getPreferredEdit(), lblCpfCnpj);
				} else {
					lblCpfCnpj.setText("Digite seu CNPJ");
					cnpj = new Edit("99.999.999/9999-99");
					cnpj.setMaxLength(14);
					cnpj.setKeyboard(Edit.KBD_NUMERIC);
					cnpj.setValidChars("0123456789");
					add(cnpj, getLeft(), AFTER + 5, getFill(), getPreferredEdit(), lblCpfCnpj);
				}
				repaintNow();
			} else if (event.target instanceof MethodButton) {
				doInsert();
			}
			break;

		default:
			break;
		}
	}
}