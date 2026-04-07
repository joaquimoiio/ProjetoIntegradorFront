package com.totalcross.ui;

import java.sql.SQLException;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.DocumentoCliente;
import com.totalcross.util.ErroBox;
import com.totalcross.util.Header;
import com.totalcross.util.ListarClientesComponente;

import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.gfx.Color;

public class AtualizarCliente extends Container {

	private Edit email, telefone, edtCpfCnpj;

	private MethodButton btnAtualizar, btnBuscar;

	private DocumentoCliente documentoCliente;

	private ClienteService service = new ClienteService();

	private CLIENTEDAO dao = new CLIENTEDAO();

	private ListarClientesComponente listaClientes;

	private Cliente clienteSelecionado = null;

	public AtualizarCliente() {
	}

	public void initUI() {

		Header header = new Header("<", "Atualizar cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		campoCpfCnpj();
		botaoBuscar();
		inicializarLista();
		atualizarTelefone();
		atualizarEmail();
		botaoAtualizar();
		registrarListenerLista();
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
			String doc = documentoCliente.getValue();
			if (doc.isEmpty()) {
				listaClientes.limparLista();
				telefone.setText("");
				email.setText("");
				listaClientes.carregarClientes();
				return;
			}

			Cliente cliente = dao.buscarClientePorDocumento(doc);

			if (cliente == null) {
				new ErroBox("Atenção!", "Cliente não encontrado!", new String[] { "Voltar" }).popup();
				return;
			}

			clienteSelecionado = cliente;
			telefone.setText(cliente.getTelefone() != null ? cliente.getTelefone() : "");
			email.setText(cliente.getEmail() != null ? cliente.getEmail() : "");

			listaClientes.limparLista();
			listaClientes.carregarClientePorDocumento(doc);

		} catch (SQLException e) {
			new ErroBox("Erro", "Erro ao buscar cliente!", new String[] { "Voltar" }).popup();
		}
	}

	private void campoCpfCnpj() {
		documentoCliente = new DocumentoCliente("Digite CPF/CNPJ para deletar:");
		add(documentoCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);
	}

	private void inicializarLista() {
		listaClientes = new ListarClientesComponente();
		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, PARENTSIZE + 35);
		listaClientes.carregarClientes();
	}

	private void registrarListenerLista() {
		listaClientes.setClienteSelecionadoListener(new ListarClientesComponente.ClienteSelecionadoListener() {
		    @Override
		    public void onClienteSelecionado(Cliente cliente) {
		        clienteSelecionado = cliente;
		        telefone.setText(cliente.getTelefone() != null ? cliente.getTelefone() : "");
		        email.setText(cliente.getEmail() != null ? cliente.getEmail() : "");

		        if ("FISICA".equals(cliente.getTipoDePessoa())) {
		            documentoCliente.setValue(cliente.getCpf());
		        } else {
		            documentoCliente.setValue(cliente.getCnpj());
		        }
		    }
		});
	}

	private void doUpdate() {
		try {

			if (clienteSelecionado == null) {
				new ErroBox("Atenção!", "Selecione um cliente na lista ou busque pelo CPF/CNPJ!",
						new String[] { "Voltar" }).popup();
				return;
			}

			String telefoneStr = telefone.getTextWithoutMask();
			String emailStr = email.getText().trim();

			if (telefoneStr == null || telefoneStr.trim().isEmpty()) {
				new ErroBox("Atenção!", "Telefone não informado!", new String[] { "Voltar" }).popup();
				return;
			}

			clienteSelecionado.setTelefone(telefoneStr);
			clienteSelecionado.setEmail(emailStr);

			service.atualizarCliente(clienteSelecionado);

			String nomeCliente = clienteSelecionado.getNomeDoCliente();
			clienteSelecionado = null;

			MessageBox mb = new MessageBox("Atenção!",
					"Cliente " + nomeCliente + " atualizado com sucesso!");
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
				doUpdate();
				listaClientes.limparLista();
				listaClientes.carregarClientes();
			}
			break;
		default:
			break;
		}
	}
}