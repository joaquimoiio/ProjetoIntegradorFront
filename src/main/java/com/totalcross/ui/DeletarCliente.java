package com.totalcross.ui;

import java.sql.SQLException;

import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.DocumentoCliente;
import com.totalcross.util.ErroBox;
import com.totalcross.util.Header;
import com.totalcross.util.ListarClientesComponente;

import totalcross.ui.Container;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.gfx.Color;

public class DeletarCliente extends Container {

	private MethodButton btnDeletar;

	private DocumentoCliente documentoCliente;

	private ListarClientesComponente listaClientes;

	private ClienteService service = new ClienteService();

	private Cliente clienteSelecionado = null;

	public DeletarCliente() {
	}

	public void initUI() {
		Header header = new Header("<", "Deletar cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		documentoCliente = new DocumentoCliente("Digite CPF/CNPJ para deletar:");
		add(documentoCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		listaClientes = new ListarClientesComponente();

		listaClientes.setClienteSelecionadoListener(new ListarClientesComponente.ClienteSelecionadoListener() {
			@Override
			public void onClienteSelecionado(Cliente cliente) {
				clienteSelecionado = cliente;
				MessageBox mb = new MessageBox("Selecionado",
						"Cliente: " + cliente.getNomeDoCliente()
								+ "\nClique em Deletar para confirmar.");
				mb.setBackForeColors(Color.WHITE, Color.BLACK);
				mb.popup();
			}
		});

		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, FILL - 65);
		listaClientes.carregarClientes();

		botaoDeletar();
	}

	public void botaoDeletar() {
		btnDeletar = new MethodButton("Deletar");
		add(btnDeletar, RIGHT - 30, AFTER + 10, DP + 75, DP + 35);
	}

	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnDeletar) {
				try {
					Cliente cliente;

					if (clienteSelecionado != null) {
						cliente = clienteSelecionado;
					} else {
						String doc = documentoCliente.getValue();
						if (doc.isEmpty()) {
							new ErroBox("Atenção!", "Selecione um cliente ou digite o CPF/CNPJ!",
									new String[] { "Voltar" }).popup();
							return;
						}
						cliente = new Cliente();
						if (doc.length() == 11) {
							cliente.setCpf(doc);
							cliente.setTipoDePessoa("FISICA");
						} else {
							cliente.setCnpj(doc);
							cliente.setTipoDePessoa("JURIDICA");
						}
					}

					service.deletarCliente(cliente);

					MessageBox mb = new MessageBox("Atenção!",
							"Cliente deletado com sucesso!");
					mb.setBackForeColors(Color.WHITE, Color.BLACK);
					mb.popup();

					clienteSelecionado = null;
					listaClientes.limparLista();
					listaClientes.carregarClientes();

				} catch (IllegalArgumentException e) {
					new ErroBox("Atenção!", e.getMessage(), new String[] { "Voltar" }).popup();
				} catch (SQLException e) {
					new ErroBox("Erro", "Erro ao deletar cliente!", new String[] { "Voltar" }).popup();
				}
			}
			break;
		default:
			break;
		}
	}
}