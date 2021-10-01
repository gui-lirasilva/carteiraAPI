package br.com.alura.carteiraAPI.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteiraAPI.dto.TransacaoDto;
import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {
	
	@Autowired
	TransacaoService service;

	@GetMapping
	public Page<TransacaoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		return service.listar(paginacao);
	}

	@PostMapping	
	public void cadastrar(@RequestBody @Valid TransacaoFormDto dto) {
		service.cadastrar(dto);
	}
}
