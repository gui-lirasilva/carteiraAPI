package br.com.alura.carteiraAPI.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.alura.carteiraAPI.dto.UsuarioDto;
import br.com.alura.carteiraAPI.dto.UsuarioFormDto;
import br.com.alura.carteiraAPI.modelo.Usuario;

@Service // Define para o Spring que essa classe contém um serviço/regras de negócio
public class UsuarioService { // A classe service contém as regras de negócio
	
	private List<Usuario> usuarios = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();
	
	public List<UsuarioDto> listar() {
		return usuarios.stream().map(t -> modelMapper.map(t, UsuarioDto.class)).collect(Collectors.toList()); 
	}

	public void cadastrar(@Valid UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		String senha = new Random().nextInt(999999)+"";
		usuario.setSenha(senha);
		
		usuarios.add(usuario);
	}
	
}
