package br.com.alura.carteiraAPI.service;

import java.util.Random;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alura.carteiraAPI.dto.UsuarioDto;
import br.com.alura.carteiraAPI.dto.UsuarioFormDto;
import br.com.alura.carteiraAPI.modelo.Perfil;
import br.com.alura.carteiraAPI.modelo.Usuario;
import br.com.alura.carteiraAPI.repository.UsuarioRepository;
import br.com.alura.carteiraAPI.repository.PerfilRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<UsuarioDto> listar(Pageable paginacao) {		
		return usuarioRepository.findAll(paginacao).map(u -> modelMapper.map(u, UsuarioDto.class)); 
	}

	public UsuarioDto cadastrar(@Valid UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		String senha = new Random().nextInt(999999)+"";
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		usuario.adicionarPerfil(perfil);
		
		usuarioRepository.save(usuario);
		return modelMapper.map(usuario, UsuarioDto.class);
	}
	
}
