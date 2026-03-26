package com.totalcross.ui;

import com.totalcross.ui.button.MethodButton;

import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;

public class Header extends Container {

	private String titulo;
	private MethodButton btnVoltar;

	public Header(String msg) {
        this.titulo = msg;
	}

	public void initUI() {
		botaoVoltar();
        titulo(titulo);
	}

    private int getTop(){
        return TOP + 10;
    }


    private void botaoVoltar() {
        btnVoltar = new MethodButton("<");
        add(btnVoltar, LEFT + 10, getTop(), DP + 50, DP + 25);
    }

    private void titulo(String titulo) {
        Label lblTitulo = new Label(titulo);
        add(lblTitulo, CENTER, getTop() + 5);
    }
	
	@Override
	public void onEvent(Event event) {
		super.onEvent(event);
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnVoltar) {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                MainWindow.getMainWindow().swap(menuPrincipal);
			}
			break;

		default:
			break;
		}
	}


}
