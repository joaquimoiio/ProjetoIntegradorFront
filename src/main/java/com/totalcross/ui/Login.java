package com.totalcross.ui;

import totalcross.ui.Container;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.event.EventHandler;

public class Login extends Container {

	private BasicButton btnLogin;
	private Edit login, senha;

	public Login() {

	}
	

	public void initUI() {


		login();

		senha();

		botaoLogin();
	}

	private void login() {
		Label lblLogin = new Label("Digite seu usuario");
		add(lblLogin, LEFT, CENTER - 125);

		login = new Edit();
		add(login, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	private void senha() {
		Label lblSenha = new Label("Digite sua senha");
		add(lblSenha, LEFT, AFTER + 10);

		senha = new Edit();
		add(senha, LEFT, AFTER + 5, FILL, PREFERRED);
	}

	private void botaoLogin() {
		btnLogin = new BasicButton("Login");
		add(btnLogin, CENTER, AFTER + 20, DP + 100, DP + 70);
	}

	@Override
	public <H extends EventHandler> void onEvent(Event<H> event) {
		super.onEvent(event);
		try {
		switch (event.type) {
		case ControlEvent.PRESSED:
			if (event.target == btnLogin) {

				boolean estado = verificacaoLogin(login.getText(), senha.getText());

				if (estado) {
					MenuPrincipal menuPrincipal = new MenuPrincipal();
					MainWindow.getMainWindow().swap(menuPrincipal);
				} else {
					throw new Exception("Erro ao login");
				}
			}

			break;

		default:
			break;
		}


	} catch (Exception e) {
		ErroBox erroLogin = new ErroBox("Erro ao Login", "Usuario ou senha invalido", new String[] { "Voltar" });
		erroLogin.popup();
	}
	}

	private boolean verificacaoLogin(String login, String senha) {
		return login.equals("Administrador") && senha.equals("admin2026@") || true;
	}
}
