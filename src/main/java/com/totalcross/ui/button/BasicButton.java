package com.totalcross.ui.button;


import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class BasicButton extends Button {

	private static final long serialVersionUID = 1L;

	public BasicButton(String valor) {
		super(valor);
		setBackForeColors(Color.BLACK, Color.WHITE);
		Font font = MainWindow.getDefaultFont().percentBy(150);
		setFont(font);
	}
}
