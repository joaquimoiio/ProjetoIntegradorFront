package com.totalcross.util;

import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;

public class DocumentoCliente extends Container {

	private Edit documento;
	private Label lblDocumento;
	private String mensagem;

	public DocumentoCliente(String mensagem) {
		this.mensagem = mensagem;
	}

	public void initUI() {
		lblDocumento = new Label(mensagem);
		add(lblDocumento, LEFT + 15, AFTER + 20);

		documento = new Edit();
		documento.setMaxLength(14);
		documento.setKeyboard(Edit.KBD_NUMERIC);
		documento.setValidChars("0123456789");
		add(documento, RIGHT - 20, AFTER - 28, DP + 135, DP + 35);
	}

	public String getValue() {
		String texto = documento.getTextWithoutMask();
		if (texto == null || texto.trim().isEmpty()) {
			return "";
		}
		return texto.trim();
	}

	public void setValue(String valor) {
		documento.setText(valor != null ? valor : "");
	}

}