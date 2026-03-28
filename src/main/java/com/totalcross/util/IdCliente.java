package com.totalcross.util;

import totalcross.sys.Convert;
import totalcross.sys.InvalidNumberException;
import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class IdCliente extends Container {

	private Edit id;
	private Label lblIdCliente;
	private String mensagem;

	public IdCliente(String mensagem) {
		this.mensagem = mensagem;
		
	}

	public void initUI() {
		lblIdCliente = new Label(mensagem);
		add(lblIdCliente, LEFT + 30, AFTER + 20);

		id = new Edit();
		id.setMaxLength(4);
		id.setKeyboard(Edit.KBD_NUMERIC);
		id.setValidChars("0123456789");
		add(id, RIGHT - 45, AFTER - 28, DP + 55, DP + 35);
	}

	public long getValue() throws InvalidNumberException {
		return Convert.toLong(id.getTextWithoutMask());
	}

}
