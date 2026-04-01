package com.totalcross.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.totalcross.entity.Cliente;
import com.totalcross.util.DatabaseManager;
import com.totalcross.util.SyncCliente;
import com.totalcross.util.SyncCliente.Response;

import totalcross.io.IOException;
import totalcross.net.HttpStream;
import totalcross.net.HttpStream.Options;
import totalcross.net.URI;
import totalcross.net.UnknownHostException;
import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class CLIENTEDAO {

	public void insertCliente(Cliente cliente) throws SQLException {

		String sql = "INSERT INTO person (nomeDoCliente, tipoDePessoa, cpf, cnpj, telefone, email, sync, deletado) VALUES (?,?,?,?,?,?,?,?)";

		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon.prepareStatement(sql);

		ps.setString(1, cliente.getNomeDoCliente());
		ps.setString(2, cliente.getTipoDePessoa());
		ps.setString(3, cliente.getCpf());
		ps.setString(4, cliente.getCnpj());
		ps.setString(5, cliente.getTelefone());
		ps.setString(6, cliente.getEmail());
		ps.setBoolean(7, cliente.isSync());
		ps.setBoolean(8, cliente.isDeletado());

		ps.executeUpdate();

		ps.close();
		dbcon.close();
	}

	public ArrayList<Cliente> buscarTodosClientes() throws SQLException {
		
		ArrayList<Cliente> clientes = new ArrayList<>();
		
		Connection dbcon = DatabaseManager.getConnection();
		Statement st = dbcon.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM person WHERE deletado = 0");
		
		while (rs.next()) {
			clientes.add(montaCliente(rs));
	    }
		
		rs.close();
		st.close();
		dbcon.close();

		return clientes;
	}

	public Cliente buscarClientePorId(Cliente id) throws SQLException {


		Connection dbcon = DatabaseManager.getConnection();
		Statement st = dbcon.createStatement();
		PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM person WHERE id = ? AND deletado = 0");
		ps.setLong(1, id.getId());
		ResultSet rs = ps.executeQuery();
		
		Cliente cliente = new Cliente();
		
		while (rs.next()) {
			cliente = montaCliente(rs);

		}
		
		rs.close();
		st.close();
		dbcon.close();
		
		return cliente;

	}

	public Cliente montaCliente(ResultSet rs) throws SQLException {
		Cliente cliente = new Cliente();
		cliente.setId(rs.getLong("id"));
		cliente.setNomeDoCliente(rs.getString("nomeDoCliente"));
		cliente.setTipoDePessoa(rs.getString("tipoDePessoa"));
		cliente.setCpf(rs.getString("cpf"));
		cliente.setCnpj(rs.getString("cnpj"));
		cliente.setTelefone(rs.getString("telefone"));
		cliente.setEmail(rs.getString("email"));
		cliente.setSync(rs.getBoolean("sync"));
		cliente.setDeletado(rs.getBoolean("deletado"));

		return cliente;
	}



	public void deletarCliente(Cliente cliente) throws SQLException {


		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon.prepareStatement("UPDATE person SET sync = ?, deletado = ? WHERE id = ?");
		ps.setBoolean(1, false);
		ps.setBoolean(2, true);
		ps.setLong(3, cliente.getId());
		ps.executeUpdate();
		ps.close();
		dbcon.close();

	}

	public void atualizarCliente(Cliente cliente) throws SQLException {

		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon
				.prepareStatement("UPDATE person SET telefone = ?, email = ? , sync = ?  WHERE id = ?");
		ps.setString(1, cliente.getTelefone());
		ps.setString(2, cliente.getEmail());
		ps.setBoolean(3, false);
		ps.setLong(4, cliente.getId());

		ps.executeUpdate();

		ps.close();
		dbcon.close();
	}

	public void atualizarClienteSync(Cliente cliente) throws SQLException {
		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon
				.prepareStatement(
						"UPDATE person SET telefone = ?, email = ?, sync = ? , deletado = ? WHERE cpf = ? OR cnpj = ?");
		ps.setString(1, cliente.getTelefone());
		ps.setString(2, cliente.getEmail());
		ps.setBoolean(3, true);
		ps.setBoolean(4, cliente.isDeletado());
		ps.setString(5, cliente.getCpf());
		ps.setString(6, cliente.getCnpj());
		ps.executeUpdate();
		ps.close();
		dbcon.close();
	}

	public boolean existeId(Cliente id) throws SQLException {
		Connection dbcon = DatabaseManager.getConnection();
		Statement st = dbcon.createStatement();
		PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM person WHERE id = ? and deletado = ?");
		ps.setLong(1, id.getId());
		ps.setBoolean(2, false);
		ResultSet rs = ps.executeQuery();

		boolean existe = rs.next();
		rs.close();
		ps.close();
		dbcon.close();
		return existe;
	}

	public boolean cpf(Cliente cpf) throws SQLException {
		Connection dbcon = DatabaseManager.getConnection();
		Statement st = dbcon.createStatement();
		PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM person WHERE cpf = ?");
		ps.setString(1, cpf.getCpf());
		ResultSet rs = ps.executeQuery();

		boolean existe = rs.next();
		rs.close();
		ps.close();
		dbcon.close();
		return existe;
	}

	public boolean cnpj(Cliente cnpj) throws SQLException {
		Connection dbcon = DatabaseManager.getConnection();
		Statement st = dbcon.createStatement();
		PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM person WHERE cnpj = ?");
		ps.setString(1, cnpj.getCnpj());
		ResultSet rs = ps.executeQuery();

		boolean existe = rs.next();
		rs.close();
		ps.close();
		dbcon.close();
		return existe;
	}

	public void enviarDados(String url, Options options) throws SQLException, UnknownHostException, IOException {

		ArrayList<Cliente> clientes = buscarNaoSincronizados();

		if (clientes.isEmpty()) {
			return;
		}

		for (Cliente cliente : clientes) {
			JSONObject jsonCliente = new JSONObject();
			jsonCliente.put("nomeDoCliente", cliente.getNomeDoCliente());
			jsonCliente.put("tipoDePessoa", cliente.getTipoDePessoa());
			jsonCliente.put("cpf", cliente.getCpf() != null ? cliente.getCpf() : "");
			jsonCliente.put("cnpj", cliente.getCnpj() != null ? cliente.getCnpj() : "");
			jsonCliente.put("telefone", cliente.getTelefone());
			jsonCliente.put("email", cliente.getEmail());
			jsonCliente.put("sync", cliente.isSync());
			jsonCliente.put("deletado", cliente.isDeletado());

			options.data = jsonCliente.toString();
			HttpStream http = new HttpStream(new URI(url), options);

			System.out.println("JSON enviado: " + jsonCliente.toString());
			System.out.println("Response code: " + http.responseCode);

			if (http.responseCode == 200) {
				marcarComoSincronizado(cliente);
			}

			http.close();
		}


	}

	public void recebeDados(Response<Cliente> response) {
		for (Cliente cliente : response.listData) {
			Cliente clienteR = new Cliente();
			clienteR.setId(cliente.getId());
			clienteR.setNomeDoCliente(cliente.getNomeDoCliente());
			clienteR.setTipoDePessoa(cliente.getTipoDePessoa());
			clienteR.setCpf(cliente.getCpf());
			clienteR.setCnpj(cliente.getCnpj());
			clienteR.setTelefone(cliente.getTelefone());
			clienteR.setEmail(cliente.getEmail());
			clienteR.setSync(cliente.isSync());
			clienteR.setDeletado(cliente.isDeletado());



			try {
				if ("FISICA".equals(clienteR.getTipoDePessoa())) {
					if (!cpf(clienteR)) {
						insertCliente(clienteR);
						marcarComoSincronizado(cliente);
					} else {
						atualizarClienteSync(clienteR);
					}
				} else {
					if (!cnpj(clienteR)) {
						insertCliente(clienteR);
						marcarComoSincronizado(cliente);
					} else {
						atualizarClienteSync(clienteR);
					}
				}



			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Está: " + cliente.isDeletado());
			System.out.println("Tipo: " + cliente.getTipoDePessoa());
		}


	}

	public ArrayList<Cliente> buscarNaoSincronizados() throws SQLException {

		ArrayList<Cliente> clientes = new ArrayList<>();

		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM person WHERE sync = ?");
		ps.setBoolean(1, false);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			clientes.add(montaCliente(rs));
		}

		rs.close();
		ps.close();
		dbcon.close();

		return clientes;
	}

	public void marcarComoSincronizado(Cliente cliente) throws SQLException {

		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon.prepareStatement("UPDATE person SET sync = ? WHERE id = ?");
		ps.setBoolean(1, true);
		ps.setLong(2, cliente.getId());
		ps.executeUpdate();
		ps.close();
		dbcon.close();
	}


	public boolean checkClienteExistsWeb(Cliente cliente) {
		try {
			return SyncCliente.checkClienteExists(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
