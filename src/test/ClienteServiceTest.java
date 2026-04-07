package com.totalcross.service;

import com.totalcross.entity.Cliente;
import org.junit.Test;

public class ClienteServiceTest {

	private ClienteService service = new ClienteService();

	@Test(expected = IllegalArgumentException.class)
	public void nomeVazioDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setNomeDoCliente("");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void nomeNuloDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setNomeDoCliente(null);
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cpfVazioDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setCpf("");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cpfInvalidoDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setCpf("12345678901");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cpfTodosDigitosIguaisDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setCpf("11111111111");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cnpjVazioDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteJuridicaValido();
		cliente.setCnpj("");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cnpjInvalidoDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteJuridicaValido();
		cliente.setCnpj("12345678000100");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void cnpjTodosDigitosIguaisDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteJuridicaValido();
		cliente.setCnpj("11111111111111");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void telefoneVazioDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setTelefone("");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void telefoneCurtoDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setTelefone("1234567890");
		service.validarCliente(cliente);
	}

	@Test(expected = IllegalArgumentException.class)
	public void emailSemArrobaDeveLancarErro() throws Exception {
		Cliente cliente = criarClienteFisicaValido();
		cliente.setEmail("emailinvalido");
		service.validarCliente(cliente);
	}

	private Cliente criarClienteFisicaValido() {
		Cliente cliente = new Cliente();
		cliente.setNomeDoCliente("João Silva");
		cliente.setTipoDePessoa("FISICA");
		cliente.setCpf("52998224725"); 
		cliente.setCnpj("");
		cliente.setTelefone("11999998888");
		cliente.setEmail("joao@email.com");
		return cliente;
	}

	private Cliente criarClienteJuridicaValido() {
		Cliente cliente = new Cliente();
		cliente.setNomeDoCliente("Empresa LTDA");
		cliente.setTipoDePessoa("JURIDICA");
		cliente.setCpf("");
		cliente.setCnpj("11222333000181");
		cliente.setTelefone("11999998888");
		cliente.setEmail("empresa@email.com");
		return cliente;
	}
}