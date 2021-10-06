package br.com.alura.carteiraAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCarteiraDto {
	
	private String ticker;
	private Long quantidade;
	private double percentual;
}
