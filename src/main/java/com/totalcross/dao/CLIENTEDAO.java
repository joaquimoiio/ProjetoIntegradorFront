package com.totalcross.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.totalcross.entity.Cliente;
import com.totalcross.util.DatabaseManager;

import totalcross.sql.Connection;
import totalcross.sql.PreparedStatement;
import totalcross.sql.ResultSet;
import totalcross.sql.Statement;

public class CLIENTEDAO {

	public void insertCliente(String nomeDoCliente, String tipoDePessoa, String cpf, String cnpj, String telefone,
			String email) throws SQLException {
		
		String sql = "INSERT INTO person (nomeDoCliente, tipoDePessoa, cpf, cnpj, telefone, email) VALUES (?,?,?,?,?,?,?)";
		
		Connection dbcon = DatabaseManager.getConnection(); 
		PreparedStatement ps = dbcon.prepareStatement(sql);

		ps.setString(1, nomeDoCliente);
		ps.setString(2, tipoDePessoa);
		ps.setString(3, cpf);
		ps.setString(4, cnpj);
		ps.setString(5, telefone);
		ps.setString(6, email);

		ps.executeUpdate();

		ps.close();
		dbcon.close();
	}

	public List<Cliente> buscarTodosClientes() throws SQLException {
		
		List<Cliente> clientes = new ArrayList<>();
		
		Connection dbcon = DatabaseManager.getConnection();
		Statement st = dbcon.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM person");
		
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
		PreparedStatement ps = dbcon.prepareStatement("SELECT * FROM person WHERE id = ?");
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

		return cliente;
	}

	public void deletarCliente(Cliente cliente) throws SQLException {

		Connection dbcon = DatabaseManager.getConnection();
		PreparedStatement ps = dbcon.prepareStatement("DELETE FROM person WHERE id = ?");
		ps.setLong(1, cliente.getId());
		ps.executeUpdate();
		ps.close();
		dbcon.close();
	}

}
