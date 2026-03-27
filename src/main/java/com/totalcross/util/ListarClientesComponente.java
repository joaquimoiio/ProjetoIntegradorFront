package com.totalcross.util;

import java.sql.SQLException;
import java.util.List;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;

import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.ScrollContainer;
import totalcross.ui.gfx.Color;

public class ListarClientesComponente extends Container {
	private ScrollContainer scrollContainer;

	@Override
	public void initUI() {
		scrollContainer = new ScrollContainer(false, true);
		scrollContainer.setBackColor(Color.WHITE);
		add(scrollContainer, LEFT, TOP, FILL, FILL);
		carregarClientes();
	}

	public void carregarClientes() {
		try {
			CLIENTEDAO dao = new CLIENTEDAO();
			List<Cliente> clientes = dao.buscarTodosClientes();

			for (Cliente cliente : clientes) {
				Container card = criarCard(cliente);
				scrollContainer.add(card, LEFT + 5, AFTER + 5, FILL - 5, DP + 50);
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
		card.setRect(0, 0, scrollContainer.getWidth() - 7, DP + 50);
		Label nomeLabel = new Label(nomeCliente.getNomeDoCliente());
		nomeLabel.setForeColor(Color.BLACK);
		card.add(nomeLabel, LEFT + 10, CENTER);
		return card;
	}
	
	
}