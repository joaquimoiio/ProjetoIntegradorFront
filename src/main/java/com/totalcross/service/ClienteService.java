package com.totalcross.service;

import java.sql.SQLException;

import com.totalcross.dao.CLIENTEDAO;
import com.totalcross.entity.Cliente;

public class ClienteService {

	private CLIENTEDAO dao = new CLIENTEDAO();

	public void validarCliente(Cliente cliente) throws SQLException {
		if (cliente.getNomeDoCliente() == null || cliente.getNomeDoCliente().trim().isEmpty()) {
			throw new IllegalArgumentException("Nome é obrigatório!");
		}

		if ("FISICA".equals(cliente.getTipoDePessoa())) {
			if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
				throw new IllegalArgumentException("CPF é obrigatório!");
			}
			if (!validarCPF(cliente.getCpf())) {
				throw new IllegalArgumentException("CPF inválido!");
			}
			if (existeCpf(cliente)) {
				throw new IllegalArgumentException("CPF já cadastrado!");
			}

		} else {
			if (cliente.getCnpj() == null || cliente.getCnpj().trim().isEmpty()) {
				throw new IllegalArgumentException("CNPJ é obrigatório!");
			}
			if (!validarCNPJ(cliente.getCnpj())) {
				throw new IllegalArgumentException("CNPJ inválido!");
			}
			if (existeCnpj(cliente)) {
				throw new IllegalArgumentException("CNPJ já cadastrado!");
			}
		}

		telefone(cliente.getTelefone());

		email(cliente.getEmail());
	}

	private static boolean validarCPF(String cpf) {
		cpf = cpf.replaceAll("\\D", "");

		if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
			return false;
		}

		try {
			int soma = 0;
			for (int i = 0; i < 9; i++) {
				soma += (cpf.charAt(i) - '0') * (10 - i);
			}
			int primeiroDigito = 11 - (soma % 11);
			if (primeiroDigito >= 10)
				primeiroDigito = 0;

			soma = 0;
			for (int i = 0; i < 10; i++) {
				soma += (cpf.charAt(i) - '0') * (11 - i);
			}
			int segundoDigito = 11 - (soma % 11);
			if (segundoDigito >= 10)
				segundoDigito = 0;

			return (cpf.charAt(9) - '0' == primeiroDigito) && (cpf.charAt(10) - '0' == segundoDigito);
		} catch (Exception e) {
			return false;
		}
	}

	private static boolean validarCNPJ(String cnpj) {
		cnpj = cnpj.replaceAll("\\D", "");

		if (cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) {
			return false;
		}

		try {
			int[] peso1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
			int[] peso2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

			int soma = 0;
			for (int i = 0; i < 12; i++) {
				soma += (cnpj.charAt(i) - '0') * peso1[i];
			}
			int primeiroDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

			soma = 0;
			for (int i = 0; i < 13; i++) {
				soma += (cnpj.charAt(i) - '0') * peso2[i];
			}
			int segundoDigito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);

			return (cnpj.charAt(12) - '0' == primeiroDigito) && (cnpj.charAt(13) - '0' == segundoDigito);
		} catch (Exception e) {
			return false;
		}
	}

	private void telefone(String telefone) {

		Cliente cliente = new Cliente();
		cliente.setTelefone(telefone);

		if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
			throw new IllegalArgumentException("Telefone inválido!");
		}
	}

	private void email(String email) {

		Cliente cliente = new Cliente();
		cliente.setEmail(email);

		if (!validarEmail(cliente.getEmail()) && !cliente.getEmail().isEmpty()) {
			throw new IllegalArgumentException("Email inválido!");
		}
	}

	private boolean validarEmail(String texto) {
		return texto.contains("@") && texto.contains(".");
	}

	private boolean existeCpf(Cliente cpf) throws SQLException {
		return dao.cpf(cpf);
	}

	private boolean existeCnpj(Cliente cliente) throws SQLException {
		return dao.cnpj(cliente);
	}

	public void validarId(Cliente cliente) throws SQLException {
		if (!dao.existeId(cliente)) {
			throw new IllegalArgumentException("Id inexistente!");
		}
	}

	public Cliente clienteFinter(Long id) {
		Cliente cliente = new Cliente();
		cliente.setId(id);
		return cliente;
	}

	public void cadastrarCliente(Cliente cliente) throws SQLException {
		validarCliente(cliente);
		dao.insertCliente(cliente);
	}

	public void atualizarCliente(Cliente cliente) throws SQLException {
		validarId(cliente);
		telefone(cliente.getTelefone());
		email(cliente.getEmail());
		dao.atualizarCliente(cliente);
	}

	public void deletarCliente(Cliente cliente) throws SQLException {
		validarId(cliente);
		dao.deletarCliente(cliente);
	}


}
