package br.com.alura.carteiraAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.alura.carteiraAPI.dto.TransacaoDto;
import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.modelo.TipoTransacao;

class TransacaoServiceTest {

	@Test
	void deveriaCadastrarUmaTransacao() {
		TransacaoFormDto formDto = new TransacaoFormDto(
				"ITAUSA",
				new BigDecimal("50.00"),
				LocalDate.now(),
				120,
				TipoTransacao.COMPRA,
				1523l);
		
		TransacaoService service = new TransacaoService();
		TransacaoDto dto = service.cadastrar(formDto);
		
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
	}

}
