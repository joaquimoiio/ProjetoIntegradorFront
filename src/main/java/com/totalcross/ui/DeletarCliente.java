package com.totalcross.ui;

import java.sql.SQLException;

import com.totalcross.entity.Cliente;
import com.totalcross.service.ClienteService;
import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.ErroBox;
import com.totalcross.util.Header;
import com.totalcross.util.IdCliente;
import com.totalcross.util.ListarClientesComponente;

import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class DeletarCliente extends Container {

	private MethodButton btnDeletar;

	private IdCliente idCliente;

	private ListarClientesComponente listaClientes;

	private ClienteService service = new ClienteService();

	public DeletarCliente() {

	}

	public void initUI() {
		Header header = new Header("<", "Deletar cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		idCliente = new IdCliente("Digite o id do Cliente para deletar:");
		add(idCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		listaClientes = new ListarClientesComponente();
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
					Cliente cliente = service.clienteFinter(idCliente.getValue());
					service.deletarCliente(cliente);
					listaClientes.limparLista();
					listaClientes.carregarClientes();
				} catch (IllegalArgumentException e) {
					new ErroBox("Atenção!", e.getMessage(), new String[] { "Voltar" }).popup();
				} catch (SQLException e) {
					e.printStackTrace();
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
