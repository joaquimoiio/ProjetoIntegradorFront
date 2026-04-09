package com.totalcross.util;

import java.util.List;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;

import totalcross.io.ByteArrayStream;
import totalcross.io.IOException;
import totalcross.json.JSONFactory;
import totalcross.net.HttpStream;
import totalcross.net.URI;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.PressListener;

public class SyncCliente {

	public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";

	private CLIENTEDAO dao = new CLIENTEDAO();

	public PressListener getPressListener(final String url, final String httpType) {
		return new PressListener() {
			@Override
			public void controlPressed(ControlEvent e) {
				HttpStream.Options options = new HttpStream.Options();
				options.httpType = httpType;

				try {
					if (HttpStream.GET.equals(httpType)) {
						HttpStream httpStream = null;
						try {
							httpStream = new HttpStream(new URI(url), options);
							ByteArrayStream bas = new ByteArrayStream(4096);
							bas.readFully(httpStream, 10, 2048);
							String data = new String(bas.getBuffer(), 0, bas.available());

							Response<Cliente> response = new Response<>();
							response.responseCode = httpStream.responseCode;
							response.listData = JSONFactory.asList(data, Cliente.class);

							dao.recebeDados(response);
							new MessageBox("Informação", "Dados recebidos com sucesso!").popup();

						} finally {
							if (httpStream != null) {
								try {
									httpStream.close();
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}
						}

					} else if (HttpStream.POST.equals(httpType)) {
						options.setContentType(CONTENT_TYPE_JSON);
						dao.enviarDados(url, options);
						new MessageBox("Informação", "Dados enviados com sucesso!").popup();
					}

				} catch (Exception ex) {
					new MessageBox("Erro", "Não foi possível receber ou enviar dados: " + ex.getMessage()).popup();
				}
			}
		};
	}

	public static class Response<T> {
		public T data;
		public List<T> listData;
		public int responseCode;
	}

}