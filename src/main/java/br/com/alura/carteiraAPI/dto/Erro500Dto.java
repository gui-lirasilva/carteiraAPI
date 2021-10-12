package br.com.alura.carteiraAPI.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Erro500Dto {
	
	private LocalDateTime timestamp;
	private String erro;
	private String menssagem;
	private String path;
}
