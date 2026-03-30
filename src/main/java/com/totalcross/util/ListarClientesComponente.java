package com.totalcross.util;

import java.sql.SQLException;
import java.util.List;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;

import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.gfx.Color;

public class ListarClientesComponente extends Container {
	private ScrollContainer scrollContainer;
	private ClienteService service = new ClienteService();

	@Override
	public void initUI() {
		scrollContainer = new ScrollContainer(false, true);
		scrollContainer.setBackColor(Color.WHITE);
		add(scrollContainer, LEFT, TOP, FILL, FILL);

	}

	public void carregarClientesPorId(Cliente id) {
		try {
			limparLista();
			service.validarId(id);
			CLIENTEDAO dao = new CLIENTEDAO();
			Cliente cliente = dao.buscarClientePorId(id);

			Container card = criarCard(cliente);
			scrollContainer.add(card, LEFT + 5, AFTER + 5, FILL - 5, DP + 70);

		} catch (IllegalArgumentException e) {
			new ErroBox("Atenção!", e.getMessage(), new String[] { "Voltar" }).popup();
			carregarClientes();
		} catch (SQLException e) {
			e.printStackTrace();
			Label erroLabel = new Label("Erro ao carregar clientes.");
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

	private Container criarCard(Cliente nomeCliente) {
		Container card = new Container() {
		};
		card.setRect(0, 0, scrollContainer.getWidth() - 7, DP + 160);

		Label idLabel = new Label("Id: " + nomeCliente.getId());
		idLabel.setForeColor(Color.BLACK);
		card.add(idLabel, LEFT + 3, TOP + 3);

		Label nomeLabel = new Label("Nome: " + nomeCliente.getNomeDoCliente());
		nomeLabel.setForeColor(Color.BLACK);
		card.add(nomeLabel, LEFT + 3, AFTER + 3);

		if ("FISICA".equals(nomeCliente.getTipoDePessoa())) {
			Label fisicaLabel = new Label("Pessoa: " + nomeCliente.getTipoDePessoa());
			fisicaLabel.setForeColor(Color.BLACK);
			card.add(fisicaLabel, LEFT + 3, AFTER + 3);
		} else {
			Label juridicaLabel = new Label("Pessoa: " + nomeCliente.getTipoDePessoa());
			juridicaLabel.setForeColor(Color.BLACK);
			card.add(juridicaLabel, LEFT + 3, AFTER + 3);
		}

		Label telefoneLabel = new Label("Telefone: " + nomeCliente.getTelefone());
		telefoneLabel.setForeColor(Color.BLACK);
		card.add(telefoneLabel, RIGHT - 3, TOP + 3);

		Label emailLabel = new Label("Email: " + nomeCliente.getEmail());
		emailLabel.setForeColor(Color.BLACK);
		card.add(emailLabel, RIGHT - 3, AFTER + 3);

		if ("FISICA".equals(nomeCliente.getTipoDePessoa())) {
			Label cpfLabel = new Label("Cpf: " + nomeCliente.getCpf());
			cpfLabel.setForeColor(Color.BLACK);
			card.add(cpfLabel, RIGHT - 3, AFTER + 3);
		} else {
			Label cnpjLabel = new Label("Cnpj: " + nomeCliente.getCpf());
			cnpjLabel.setForeColor(Color.BLACK);
			card.add(cnpjLabel, RIGHT - 3, AFTER + 3);
		}

		return card;
	}
	
	public void limparLista() {
		scrollContainer.removeAll();
		scrollContainer.reposition();
	}
	
}