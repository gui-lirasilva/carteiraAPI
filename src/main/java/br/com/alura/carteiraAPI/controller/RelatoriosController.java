package br.com.alura.carteiraAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteiraAPI.dto.ItemCarteiraDto;
import br.com.alura.carteiraAPI.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@Autowired
	RelatorioService service;
	
	@GetMapping("/carteira")
	public List<ItemCarteiraDto> relatorioCarteiraDeInvestimentos(){
		return service.relatorioCarteiraDeInvestimentos();
	}
}
