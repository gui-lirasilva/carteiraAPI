package br.com.alura.carteiraAPI.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteiraAPI.dto.LoginFormDto;
import br.com.alura.carteiraAPI.dto.TokenDto;
import br.com.alura.carteiraAPI.infra.security.AutenticacaoService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	@Autowired
	private AutenticacaoService service;
	
	@PostMapping
	public TokenDto autenticar (@RequestBody @Valid LoginFormDto dto) {
		return new TokenDto (service.autenticar(dto));
	}
}
