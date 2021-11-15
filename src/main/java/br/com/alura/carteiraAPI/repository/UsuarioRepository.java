package br.com.alura.carteiraAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.carteiraAPI.modelo.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByLogin(String login);
	
	@Query("SELECT u FROM Usuario u JOIN FETCH u.perfis WHERE u.id = :id")
	Usuario carregarPorIdComPerfis(Long id);
	
}
