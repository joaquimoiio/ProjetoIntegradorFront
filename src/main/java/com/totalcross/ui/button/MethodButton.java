package com.totalcross.ui;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class MethodButton extends Button {

	public MethodButton(String msg) {
		super(msg);
		setBackForeColors(Color.getRGB(128, 128, 128), Color.BLACK);
		Font font = MainWindow.getDefaultFont().percentBy(150);
		setFont(font);
	}

}
