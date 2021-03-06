package br.com.alura.carteiraAPI.service;

import java.math.BigDecimal;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.alura.carteiraAPI.dto.AtualizacaoTransacaoFormDto;
import br.com.alura.carteiraAPI.dto.TransacaoDetalhadaDto;
import br.com.alura.carteiraAPI.dto.TransacaoDto;
import br.com.alura.carteiraAPI.dto.TransacaoFormDto;
import br.com.alura.carteiraAPI.modelo.Transacao;
import br.com.alura.carteiraAPI.modelo.Usuario;
import br.com.alura.carteiraAPI.repository.TransacaoRepository;
import br.com.alura.carteiraAPI.repository.UsuarioRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CalculadoraDeImpostoService calculadoraDeImpostoService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<TransacaoDto> listar(Pageable paginacao, Usuario logado) {
		return transacaoRepository.findAllByUsuario(logado, paginacao).map(t -> modelMapper.map(t, TransacaoDto.class));
	}
	
	@Transactional
	public TransacaoDto cadastrar(@RequestBody @Valid TransacaoFormDto dto, Usuario logado) {
		Long idUsuario = dto.getUsuarioId();
		
		try {
			Usuario usuario = usuarioRepository.getById(idUsuario);
			
			if (!logado.equals(usuario)) {
				lancarErro403();
			}
			
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(usuario);
			
			BigDecimal imposto = calculadoraDeImpostoService.calcular(transacao);
			transacao.setImposto(imposto);
			
			transacaoRepository.save(transacao);
			
			return modelMapper.map(transacao, TransacaoDto.class);
			
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Usuario inexistente");
		}
	}
	
	@Transactional
	public TransacaoDto atualizar(@Valid AtualizacaoTransacaoFormDto dto, Usuario logado) {
		Transacao transacao = transacaoRepository.getById(dto.getId());	
		
		if (!transacao.pertenceAo(logado)) {
			lancarErro403();
		}
		
		transacao.atualizarInformacoes(dto.getTicker(), dto.getPreco(), dto.getQuantidade(), dto.getData(), dto.getTipo());
		return modelMapper.map(transacao, TransacaoDto.class);
	}
	
	@Transactional
	public void remover(Long id, Usuario logado) {
		
		Transacao transacao = transacaoRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		if (transacao.pertenceAo(logado)) {
			transacaoRepository.deleteById(id);
		} else {
			lancarErro403();
		}
	}

	public TransacaoDetalhadaDto listarPorId(@NotNull Long id, Usuario logado) {
		
		Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		if (!transacao.pertenceAo(logado)) {
			lancarErro403();
		}
		
		return modelMapper.map(transacao, TransacaoDetalhadaDto.class);
	}
	
	private void lancarErro403() {
		throw new AccessDeniedException("Acesso negado!");
	}
}
