package com.totalcross.ui;


import java.sql.SQLException;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.ErroBox;
import com.totalcross.util.Header;
import com.totalcross.util.IdCliente;
import com.totalcross.util.ListarClientesComponente;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.gfx.Color;

public class AtualizarCliente extends Container {

	private Edit email, telefone;

	private MethodButton btnAtualizar, btnBuscar;
	
	private IdCliente idCliente;

	private ClienteService service = new ClienteService();

	private CLIENTEDAO dao = new CLIENTEDAO();

	private ListarClientesComponente listaClientes;

	public AtualizarCliente() {

	}

	public void initUI() {

		Header header = new Header("<", "Atualizar cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		idCliente = new IdCliente("Digite o id do Cliente para filtrar:");
		add(idCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		botaoBuscar();

		listaClientes = new ListarClientesComponente();
		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, PARENTSIZE + 35);
		listaClientes.carregarClientes();

		atualizarTelefone();
		atualizarEmail();
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

		Label lblEmail = new Label("Digite o novo email");
		add(lblEmail, getLeft(), AFTER + 10);

		email = new Edit();
		add(email, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void atualizarTelefone() {
		Label lblTelefone = new Label("Digite o novo telefone");
		add(lblTelefone, getLeft(), AFTER + 15);

		telefone = new Edit("(99) 99999-9999");
		telefone.setMaxLength(11);
		telefone.setKeyboard(Edit.KBD_NUMERIC);
		telefone.setValidChars("0123456789");
		add(telefone, getLeft(), AFTER + 5, getFill(), getPreferredEdit());
	}

	public void botaoBuscar() {
		btnBuscar = new MethodButton("Buscar");
		add(btnBuscar, RIGHT - 30, AFTER + 5, DP + 75, DP + 35);
	}
	public void botaoAtualizar() {
		btnAtualizar = new MethodButton("Atualizar");
		add(btnAtualizar, RIGHT - 30, AFTER + 30, DP + 75, DP + 35);
	}

	private void preencherCamposDoCliente() {
		try {
			long id = idCliente.getValue();
			if (id == 0)
				return;

			Cliente clienteBusca = service.clienteFinter(id);

			if (!dao.existeId(clienteBusca)) {
				new ErroBox("Atenção!", "Cliente não encontrado!", new String[] { "Voltar" }).popup();
				return;
			}

			Cliente cliente = dao.buscarClientePorId(clienteBusca);
			telefone.setText(cliente.getTelefone() != null ? cliente.getTelefone() : "");
			email.setText(cliente.getEmail() != null ? cliente.getEmail() : "");

			listaClientes.limparLista();
			listaClientes.carregarClientesPorId(clienteBusca);

		} catch (InvalidNumberException e) {
			new ErroBox("Atenção!", "ID inválido!", new String[] { "Voltar" }).popup();
		} catch (SQLException e) {
			new ErroBox("Erro", "Erro ao buscar cliente!", new String[] { "Voltar" }).popup();
		}
	}

	private void doUpdate() throws InvalidNumberException {
		try {

			long id = idCliente.getValue();

			Cliente cliente = service.clienteFinter(id);
			String telefoneStr = telefone.getTextWithoutMask();
			String emailStr = email.getText().trim();

			if (telefoneStr == null || telefoneStr.trim().isEmpty() || telefoneStr == "") {
				new ErroBox("Atenção!", "Telefone nao informado", new String[] { "Voltar" }).popup();
				return;
			}

			cliente.setTelefone(telefoneStr);
			cliente.setEmail(emailStr);

			service.atualizarCliente(cliente);

			MessageBox mb = new MessageBox("Atenção!",
					"Cliente com id:" + id + " atualizado com sucesso!");
			mb.setBackForeColors(Color.WHITE, Color.BLACK);
			mb.popup();

		} catch (IllegalArgumentException e) {
			new ErroBox("Atenção!", e.getMessage(), new String[] { "Voltar" }).popup();
		} catch (SQLException e) {
			new ErroBox("Erro ao Atualizar", "Erro ao salvar no banco de dados!", new String[] { "Voltar" }).popup();
		}
	}

	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnBuscar) {
				preencherCamposDoCliente();
			}
			if (event.target == btnAtualizar) {
				try {
					doUpdate();
					listaClientes.limparLista();
					listaClientes.carregarClientes();
				} catch (InvalidNumberException e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			break;
		}
	}
}






