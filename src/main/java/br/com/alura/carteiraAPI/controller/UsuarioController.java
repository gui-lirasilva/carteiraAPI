package br.com.alura.carteiraAPI.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteiraAPI.dto.UsuarioDto;
import br.com.alura.carteiraAPI.dto.UsuarioFormDto;
import br.com.alura.carteiraAPI.modelo.Usuario;
import br.com.alura.carteiraAPI.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService service;

	@GetMapping
	public List<UsuarioDto> listar() {
		return service.listar();
	}

	@PostMapping
	public void cadastrar(@RequestBody @Valid UsuarioFormDto dto) {
		service.cadastrar(dto);
	}
}