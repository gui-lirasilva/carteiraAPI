package br.com.alura.carteiraAPI.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.alura.carteiraAPI.dto.TransacaoDto;
import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.modelo.TipoTransacao;
import br.com.alura.carteiraAPI.repository.TransacaoRepository;
import br.com.alura.carteiraAPI.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class) //Para que o mockito seja executado pelo JUnit
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	private TransacaoService service;
	
	@Test
	void deveriaCadastrarUmaTransacao() {
		TransacaoFormDto formDto = new TransacaoFormDto(
				"ITAUSA",
				new BigDecimal("50.00"),
				LocalDate.now(),
				120,
				TipoTransacao.COMPRA,
				1523l);
		
		TransacaoDto dto = service.cadastrar(formDto);
		
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
	}
	
	@Test
	void naoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() {
		TransacaoFormDto formDto = new TransacaoFormDto(
				"ITAUSA",
				new BigDecimal("50.00"),
				LocalDate.now(),
				120,
				TipoTransacao.COMPRA,
				1523l);
		
		Mockito
		.when(usuarioRepository.getById(formDto.getUsuarioId()))
		.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto)); 
		// Para o JUnit entender que o teste será bem sucedido quando essa exception for lançada ao tentar cadastrar nesse caso
		
	}

}
