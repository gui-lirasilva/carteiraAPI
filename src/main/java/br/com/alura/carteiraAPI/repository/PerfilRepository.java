package br.com.alura.carteiraAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.carteiraAPI.modelo.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>  {

}
