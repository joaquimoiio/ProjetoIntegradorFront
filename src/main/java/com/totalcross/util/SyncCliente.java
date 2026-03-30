package com.totalcross.util;

import java.sql.SQLException;
import java.util.List;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;

import totalcross.io.IOException;
import totalcross.net.HttpStream;
import totalcross.net.URI;
import totalcross.util.Hashtable;

public class SyncCliente {

	private CLIENTEDAO dao = new CLIENTEDAO();

	private String safe(String value) {
		return value != null ? value : "";
	}

	public void sincronizarTodos() {
		try {
			List<Cliente> pendentes = dao.buscarNaoSincronizados();

			if (pendentes.isEmpty()) {
				return;
			}

			for (Cliente cliente : pendentes) {
				boolean enviado = enviarDados(cliente);
				if (enviado) {
					dao.marcarComoSincronizado(cliente);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean enviarDados(Cliente cliente) {
        HttpStream stream = null;

        try {
			String url = "http://10.1.1.101:8081/sync/cliente";

            String json = "{"
					+ "\"id\":" + cliente.getId() + "," + "\"nomeDoCliente\":\"" + safe(cliente.getNomeDoCliente())
					+ "\"," + "\"tipoDePessoa\":\"" + safe(cliente.getTipoDePessoa()) + "\"," + "\"cpf\":\""
					+ safe(cliente.getCpf()) + "\"," + "\"cnpj\":\"" + safe(cliente.getCnpj()) + "\","
					+ "\"telefone\":\"" + safe(cliente.getTelefone()) + "\"," + "\"email\":\""
					+ safe(cliente.getEmail()) + "\"," + "\"sync\":" + cliente.isSync()
                    + "}";

			Hashtable headers = new Hashtable(2);
			headers.put("Content-Type", "application/json");
			headers.put("Content-Length", String.valueOf(json.length()));

            HttpStream.Options options = new HttpStream.Options();
			options.httpType = HttpStream.POST;
			options.postHeaders = headers;

            stream = new HttpStream(new URI(url), options);
			stream.writeBytes(json);

            int responseCode = stream.responseCode;

			return responseCode == 200;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}