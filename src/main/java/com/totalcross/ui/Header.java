package com.totalcross.ui;

import com.totalcross.ui.button.MethodButton;

import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class Header extends Container {

	private String titulo;
	private MethodButton btnVoltar;

	public Header(String msg) {
		setTitulo(msg);
	}

	@Override
	public void initUI() {
		montaTela();
	}

	private void montaTela() {
		btnVoltar = new MethodButton("<");
		add(btnVoltar, LEFT + 10, getTop(), DP + 50, DP + 25);

		Label lblTitulo = new Label(titulo);
		add(lblTitulo, CENTER, getTop());
	}
	
	@Override
	public void onEvent(Event event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnVoltar) {
			}

			break;

		default:
			break;
		}
	}

	public int getTop() {
		return TOP + 10;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
