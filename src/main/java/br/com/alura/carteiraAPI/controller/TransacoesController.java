package br.com.alura.carteiraAPI.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.carteiraAPI.dto.AtualizacaoTransacaoFormDto;
import br.com.alura.carteiraAPI.dto.TransacaoDetalhadaDto;
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
	public ResponseEntity<TransacaoDto> cadastrar(@RequestBody @Valid TransacaoFormDto dto, UriComponentsBuilder uriBuilder) {
		TransacaoDto transacaoDto = service.cadastrar(dto);
		
		URI uri = uriBuilder
				.path("/transacoes/{id}")
				.buildAndExpand(transacaoDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(transacaoDto);	
	}
	
	@PutMapping	
	public ResponseEntity<TransacaoDto> atualizar(@RequestBody @Valid AtualizacaoTransacaoFormDto dto) {
		TransacaoDto atualizada = service.atualizar(dto);
		return ResponseEntity.ok(atualizada);	
	}
	
	@DeleteMapping("/{id}")	
	public ResponseEntity<TransacaoDto> excluir(@PathVariable @NotNull Long id) {
		service.remover(id);
		return ResponseEntity.noContent().build();	
	}
	
	@GetMapping("/{id}")	
	public ResponseEntity<TransacaoDetalhadaDto> detalhar(@PathVariable @NotNull Long id) {
		TransacaoDetalhadaDto dto = service.listarPorId(id);
		return ResponseEntity.ok(dto);	
	}
}
