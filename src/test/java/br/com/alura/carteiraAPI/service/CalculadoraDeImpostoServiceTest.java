package br.com.alura.carteiraAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.alura.carteiraAPI.modelo.TipoTransacao;
import br.com.alura.carteiraAPI.modelo.Transacao;
import br.com.alura.carteiraAPI.modelo.Usuario;

class CalculadoraDeImpostoServiceTest {

	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		Transacao transacao = new Transacao(
				120l,
				"BBSE3",
				new BigDecimal("30.00"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				new Usuario(1l, "Rafaela", "rafa@gmail.com", "senha123"));
		
		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
	}
	
	@Test
	void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
		Transacao transacao = new Transacao(
				120l,
				"BBSE3",
				new BigDecimal("30.00"),
				10,
				LocalDate.now(),
				TipoTransacao.VENDA,
				new Usuario(1l, "Rafaela", "rafa@gmail.com", "senha123"));
		
		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
	}
	
	@Test
	void deveriaCalcularImpostoDeTransacaoDoTipoVEndaComValorMaiorQueVinteMil() {
		Transacao transacao = new Transacao(
				120l,
				"BBSE3",
				new BigDecimal("1000.00"),
				30,
				LocalDate.now(),
				TipoTransacao.VENDA,
				new Usuario(1l, "Rafaela", "rafa@gmail.com", "senha123"));
		
		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(new BigDecimal("4500.00"), imposto);
	}

}
