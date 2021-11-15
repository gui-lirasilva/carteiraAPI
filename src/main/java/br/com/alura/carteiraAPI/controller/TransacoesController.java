package br.com.alura.carteiraAPI.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import br.com.alura.carteiraAPI.modelo.Usuario;
import br.com.alura.carteiraAPI.service.TransacaoService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {
	
	@Autowired
	TransacaoService service;

	@GetMapping
	public Page<TransacaoDto> listar(@PageableDefault(size = 10) Pageable paginacao, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		return service.listar(paginacao, logado);
	}

	@PostMapping	
	public ResponseEntity<TransacaoDto> cadastrar(@RequestBody @Valid TransacaoFormDto dto, UriComponentsBuilder uriBuilder,  @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		TransacaoDto transacaoDto = service.cadastrar(dto, logado);
		
		URI uri = uriBuilder
				.path("/transacoes/{id}")
				.buildAndExpand(transacaoDto.getId())
				.toUri();
		return ResponseEntity.created(uri).body(transacaoDto);	
	}
	
	@PutMapping	
	public ResponseEntity<TransacaoDto> atualizar(@RequestBody @Valid AtualizacaoTransacaoFormDto dto, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		TransacaoDto atualizada = service.atualizar(dto, logado);
		return ResponseEntity.ok(atualizada);	
	}
	
	@DeleteMapping("/{id}")	
	public ResponseEntity<TransacaoDto> excluir(@PathVariable @NotNull Long id, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		service.remover(id, logado);
		return ResponseEntity.noContent().build();	
	}
	
	@GetMapping("/{id}")	
	public ResponseEntity<TransacaoDetalhadaDto> detalhar(@PathVariable @NotNull Long id, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		TransacaoDetalhadaDto dto = service.listarPorId(id, logado);
		return ResponseEntity.ok(dto);	
	}
}
