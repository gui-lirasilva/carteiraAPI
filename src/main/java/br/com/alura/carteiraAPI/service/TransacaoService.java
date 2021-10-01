package br.com.alura.carteiraAPI.service;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.alura.carteiraAPI.dto.TransacaoDto;
import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.modelo.Transacao;
import br.com.alura.carteiraAPI.repository.TransacaoRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public Page<TransacaoDto> listar(Pageable paginacao) {
		Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
		return transacoes.map(t -> modelMapper.map(t, TransacaoDto.class));
	}
	
	@Transactional
	public void cadastrar(@RequestBody @Valid TransacaoFormDto dto) {
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacao.setId(null);
		transacaoRepository.save(transacao);
	}
}
