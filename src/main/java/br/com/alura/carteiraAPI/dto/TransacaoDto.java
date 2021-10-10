package br.com.alura.carteiraAPI.dto;

import java.math.BigDecimal;

import br.com.alura.carteiraAPI.modelo.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {
	
	private Long id;
	private String ticker;
	private BigDecimal preco; 
	private Integer quantidade;
	private TipoTransacao tipo;
}
