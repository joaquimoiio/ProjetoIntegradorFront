package com.totalcross.util;

import totalcross.ui.Button;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class Header extends Container {

	private String titulo;
    private Container telaDestino;
    private String botao;
	private Button btnVoltar;

	public Header(String botao, String titulo, Container telaDestino) {
        this.botao = botao;
        this.titulo = titulo;
        this.telaDestino = telaDestino;
	}

	public void initUI() {
		botaoVoltar(botao);
        titulo(titulo);
	}

    private int getTop(){
        return TOP + 10;
    }


    private void botaoVoltar(String botao) {
		btnVoltar = new Button(botao);
		setBackForeColors(parent.getBackColor(), Color.BLACK);
		Font font = MainWindow.getDefaultFont().percentBy(100);
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
                MainWindow.getMainWindow().swap(telaDestino);
			}
			break;

		default:
			break;
		}
	}


}
