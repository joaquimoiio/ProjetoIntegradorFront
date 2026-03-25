package com.totalcross;

import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.font.Font;
import totalcross.ui.gfx.Color;
public class App extends MainWindow {

	private Button btnLogin;
	private Edit login, senha;

	public App() {
        setUIStyle(Settings.MATERIAL_UI);
    }

    @Override
	public void initUI() {

		Label lblLogin = new Label("Digite seu usuario");
		add(lblLogin, LEFT, CENTER - 125);

		login = new Edit();
		add(login, LEFT, AFTER + 5, FILL, PREFERRED);

		Label lblSenha = new Label("Digite sua senha");
		add(lblSenha, LEFT, AFTER + 10);

		senha = new Edit();
		add(senha, LEFT, AFTER + 5, FILL, PREFERRED);

		btnLogin = new Button("Login");
		btnLogin.setBackForeColors(Color.BLACK, Color.WHITE);
		Font font = MainWindow.getDefaultFont().percentBy(150);
		btnLogin.setFont(font);
		add(btnLogin, CENTER, AFTER + 20, DP + 100, DP + 70);
    }
}
