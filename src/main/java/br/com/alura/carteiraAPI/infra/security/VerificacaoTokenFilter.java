package br.com.alura.carteiraAPI.infra.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.carteiraAPI.modelo.Usuario;
import br.com.alura.carteiraAPI.repository.UsuarioRepository;

public class VerificacaoTokenFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	public VerificacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = request.getHeader("Authorization");

		if (token == null || token.isBlank()) {
			filterChain.doFilter(request, response);
			return;
		}

		token = token.replace("Bearer ", "");

		if (tokenService.isValido(token)) {
			Long idUsuario = tokenService.extrairIdDoUsuario(token);
			Usuario usuario = usuarioRepository.carregarPorIdComPerfis(idUsuario);

			Authentication autenticacao = new UsernamePasswordAuthenticationToken(
					usuario, null,
					usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
		}

		filterChain.doFilter(request, response);

	}

}
