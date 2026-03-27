package com.totalcross.ui;

import com.totalcross.ui.button.MethodButton;
import com.totalcross.util.Header;
import com.totalcross.util.IdCliente;
import com.totalcross.util.ListarClientesComponente;

import totalcross.ui.Container;

public class DeletarCliente extends Container {

	public DeletarCliente() {

	}

	public void initUI() {
		Header header = new Header("<", "Deletar cliente", new MenuPrincipal());
		add(header, LEFT, TOP, FILL, DP + 40);

		IdCliente idCliente = new IdCliente("Digite o id do Cliente para filtrar:");
		add(idCliente, LEFT, AFTER + 10, FILL, PARENTSIZE + 9);

		ListarClientesComponente listaClientes = new ListarClientesComponente();
		add(listaClientes, LEFT + 10, AFTER + 10, FILL - 10, FILL - 65);
		
		
		botaoDeletar();
		
	}
	
	public void botaoDeletar() {
		MethodButton btnDeletar = new MethodButton("Deletar");
		add(btnDeletar, RIGHT - 30, AFTER + 10, DP + 75, DP + 35);
	}

}
