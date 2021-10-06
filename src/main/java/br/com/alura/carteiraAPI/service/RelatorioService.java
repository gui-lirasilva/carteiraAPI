package br.com.alura.carteiraAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.carteiraAPI.dto.ItemCarteiraDto;
import br.com.alura.carteiraAPI.repository.TransacaoRepository;

@Service
public class RelatorioService {
	
	@Autowired
	private TransacaoRepository repository;
	
	public List<ItemCarteiraDto> relatorioCarteiraDeInvestimentos(){
		return repository.relatorioCarteiraDeInvestimentos();
	} 
}
