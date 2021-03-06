package br.com.alura.carteiraAPI.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.alura.carteiraAPI.dto.ItemCarteiraDto;
import br.com.alura.carteiraAPI.modelo.Transacao;
import br.com.alura.carteiraAPI.modelo.Usuario;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	@Query("SELECT new br.com.alura.carteiraAPI.dto.ItemCarteiraDto("
			+ " t.ticker,"
			+ " SUM(CASE WHEN(t.tipo = 'COMPRA') THEN t.quantidade ELSE(t.quantidade * -1) END),"
			+ " (SELECT SUM(CASE WHEN(t2.tipo = 'COMPRA') THEN t2.quantidade ELSE(t2.quantidade * -1) END) FROM Transacao t2))"
			+ " FROM Transacao t"
			+ " GROUP BY t.ticker")
	List<ItemCarteiraDto> relatorioCarteiraDeInvestimentos();
	
	Page<Transacao> findAllByUsuario(Usuario usuario, Pageable paginacao);
		
}
