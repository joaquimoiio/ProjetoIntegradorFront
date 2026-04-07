package com.totalcross.ui.button;

import totalcross.ui.Button;
import totalcross.ui.MainWindow;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;

public class MethodButton extends Button {

	public MethodButton(String msg) {
		super(msg);
		setBackForeColors(Color.getRGB(128, 128, 128), Color.BLACK);
		Font font = MainWindow.getDefaultFont().percentBy(100);
		setFont(font);
	}

	@Override
	public void onPaint(totalcross.ui.gfx.Graphics g) {
		g.backColor = getBackColor();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.foreColor = getForeColor();
		String[] lines = getText().split("\n");
		int lineHeight = font.fm.height;
		int totalHeight = lines.length * lineHeight;
		int startY = (getHeight() - totalHeight) / 2;
		for (int i = 0; i < lines.length; i++) {
			int textWidth = font.fm.stringWidth(lines[i]);
			int x = (getWidth() - textWidth) / 2;
			int y = startY + (i * lineHeight);
			g.drawText(lines[i], x, y);
		}
	}
}
