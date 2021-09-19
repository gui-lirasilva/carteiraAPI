package br.com.alura.carteiraAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteiraAPI.dto.TransacaoDto;
import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.modelo.Transacao;

@RestController
@RequestMapping("/transacoes")
public class TransacoesController {

	private List<Transacao> transacoes = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();

	@GetMapping
	public List<TransacaoDto> listar() {

		List<TransacaoDto> transacoesDto = new ArrayList<>();
		
		return transacoes.stream().map(t -> modelMapper.map(t, TransacaoDto.class)).collect(Collectors.toList()); 
	}

	@PostMapping	// Para que as validações da classe sejam feitas, é necessário usar @valid
	public void cadastrar(@RequestBody @Valid TransacaoFormDto dto) {
		
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacoes.add(transacao);
	}
}
