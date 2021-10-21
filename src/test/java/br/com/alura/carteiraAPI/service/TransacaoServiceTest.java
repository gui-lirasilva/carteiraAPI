package br.com.alura.carteiraAPI.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.modelo.TipoTransacao;

class TransacaoServiceTest {

	@Test
	void deveriaCadastrarUmaTransacao() {
		TransacaoService service = new TransacaoService();
		TransacaoFormDto formDto = new TransacaoFormDto(
				"ITAUSA",
				new BigDecimal("50.00"),
				LocalDate.now(),
				120,
				TipoTransacao.COMPRA,
				1523l);		
		service.cadastrar(formDto);
	}

}
