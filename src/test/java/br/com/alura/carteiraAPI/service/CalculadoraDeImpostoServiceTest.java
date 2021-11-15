package br.com.alura.carteiraAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.carteiraAPI.modelo.TipoTransacao;
import br.com.alura.carteiraAPI.modelo.Transacao;
import br.com.alura.carteiraAPI.modelo.Usuario;

class CalculadoraDeImpostoServiceTest {

	private CalculadoraDeImpostoService calculadora;

	private Transacao criarTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
		Transacao transacao = new Transacao(
				120l,
				"BBSE3",
				preco,
				quantidade,
				LocalDate.now(),
				tipo,
				new Usuario(1l, "Rafaela", "rafa@gmail.com", "senha123", null));
		return transacao;
	}
	
	@BeforeEach
	public void criarCalculadora() {
		calculadora = new CalculadoraDeImpostoService();
	} // Para criar uma calculadora antes da execução de cada teste
	
	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		Transacao transacao = criarTransacao(new BigDecimal("30.00"), 10, TipoTransacao.COMPRA);
		
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
	}

	
	@Test
	void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
		Transacao transacao = criarTransacao(new BigDecimal("30.00"), 10, TipoTransacao.VENDA);
		
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
	}
	
	@Test
	void deveriaCalcularImpostoDeTransacaoDoTipoVEndaComValorMaiorQueVinteMil() {
		Transacao transacao = criarTransacao(new BigDecimal("1000.00"), 30, TipoTransacao.VENDA);
		
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(new BigDecimal("4500.00"), imposto);
	}

}
