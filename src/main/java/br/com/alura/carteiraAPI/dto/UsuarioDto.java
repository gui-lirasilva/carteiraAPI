package br.com.alura.carteiraAPI.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
	
	private String nome;
	private String login;
	private Long id;
}
