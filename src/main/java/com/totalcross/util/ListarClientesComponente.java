package com.totalcross.util;

import java.sql.SQLException;
import java.util.List;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;

import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;
import totalcross.ui.gfx.Color;

public class ListarClientesComponente extends Container {
	private ScrollContainer scrollContainer;
	private ClienteService service = new ClienteService();
	private ClienteSelecionadoListener listener;

	public interface ClienteSelecionadoListener {
		void onClienteSelecionado(Cliente cliente);
	}

	public void setClienteSelecionadoListener(ClienteSelecionadoListener listener) {
		this.listener = listener;
	}

	@Override
	public void initUI() {
		scrollContainer = new ScrollContainer(false, true);
		scrollContainer.setBackColor(Color.WHITE);
		add(scrollContainer, LEFT, TOP, FILL, FILL);
	}

	public void carregarClientePorDocumento(String documento) {
		try {
			limparLista();
			CLIENTEDAO dao = new CLIENTEDAO();
			Cliente cliente = dao.buscarClientePorDocumento(documento);

			if (cliente == null) {
				new ErroBox("Atenção!", "Cliente não encontrado!", new String[] { "Voltar" }).popup();
				carregarClientes();
				return;
			}

			Container card = criarCard(cliente);
			scrollContainer.add(card, LEFT + 5, AFTER + 5, FILL - 5, DP + 70);

		} catch (SQLException e) {
			e.printStackTrace();
			Label erroLabel = new Label("Erro ao carregar cliente.");
			erroLabel.setForeColor(Color.RED);
			scrollContainer.add(erroLabel, LEFT + 10, AFTER + 5);
		}
	}

	public void carregarClientes() {
		limparLista();
		try {
			CLIENTEDAO dao = new CLIENTEDAO();
			List<Cliente> clientes = dao.buscarTodosClientes();

			for (Cliente cliente : clientes) {
				Container card = criarCard(cliente);
				scrollContainer.add(card, LEFT + 5, AFTER + 5, FILL - 5, DP + 70);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			Label erroLabel = new Label("Erro ao carregar clientes.");
			erroLabel.setForeColor(Color.RED);
			scrollContainer.add(erroLabel, LEFT + 10, AFTER + 5);
		}
	}

	private Container criarCard(Cliente clienteData) {
		final Cliente clienteRef = clienteData;

		Container card = new Container() {
			@Override
			public <H extends EventHandler> void onEvent(Event<H> event) {
				super.onEvent(event);
				if (event.type == ControlEvent.PRESSED) {
					if (listener != null) {
						listener.onClienteSelecionado(clienteRef);
					}
				}
			}
		};
		card.setRect(0, 0, scrollContainer.getWidth() - 7, DP + 160);

		Label nomeLabel = new Label("Nome: " + clienteData.getNomeDoCliente());
		nomeLabel.setForeColor(Color.BLACK);
		card.add(nomeLabel, LEFT + 3, TOP + 3);

		if ("FISICA".equals(clienteData.getTipoDePessoa())) {
			Label tipoLabel = new Label("Pessoa: FISICA");
			tipoLabel.setForeColor(Color.BLACK);
			card.add(tipoLabel, LEFT + 3, AFTER + 3);

			Label cpfLabel = new Label("CPF: " + clienteData.getCpf());
			cpfLabel.setForeColor(Color.BLACK);
			card.add(cpfLabel, LEFT + 3, AFTER + 3);
		} else {
			Label tipoLabel = new Label("Pessoa: JURIDICA");
			tipoLabel.setForeColor(Color.BLACK);
			card.add(tipoLabel, LEFT + 3, AFTER + 3);

			Label cnpjLabel = new Label("CNPJ: " + clienteData.getCnpj());
			cnpjLabel.setForeColor(Color.BLACK);
			card.add(cnpjLabel, LEFT + 3, AFTER + 3);
		}

		Label telefoneLabel = new Label("Telefone: " + clienteData.getTelefone());
		telefoneLabel.setForeColor(Color.BLACK);
		card.add(telefoneLabel, RIGHT - 3, TOP + 3);

		Label emailLabel = new Label("Email: " + clienteData.getEmail());
		emailLabel.setForeColor(Color.BLACK);
		card.add(emailLabel, RIGHT - 3, AFTER + 3);

		return card;
	}

	public void limparLista() {
		scrollContainer.removeAll();
		scrollContainer.reposition();
	}
}