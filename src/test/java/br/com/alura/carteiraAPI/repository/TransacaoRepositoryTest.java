package br.com.alura.carteiraAPI.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.carteiraAPI.dto.ItemCarteiraDto;
import br.com.alura.carteiraAPI.modelo.TipoTransacao;
import br.com.alura.carteiraAPI.modelo.Transacao;
import br.com.alura.carteiraAPI.modelo.Usuario;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE) // Para não subistituir o banco de dados pelo banco em memória
@ActiveProfiles("test") // Para utilizar o aplication-test.properties para esse arquivo
class TransacaoRepositoryTest {

	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	void deveriaRetornarRelatorioDeCarteiraDeInvestimentos() {
		
		Usuario usuario = new Usuario("Guilherme", "guilherme@gmail.com", "589311");
		entityManager.persist(usuario);
		
		Transacao t1 = new Transacao(
				"XPTO4",
				new BigDecimal("55.50"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t1);
		
		Transacao t2 = new Transacao(
				"XPTO5",
				new BigDecimal("55.50"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t2);
		
		Transacao t3 = new Transacao(
				"XPTO6",
				new BigDecimal("100.00"),
				100,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t3);
		
		Transacao t4 = new Transacao(
				"XPTO6",
				new BigDecimal("100.00"),
				100,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t4);
		
		Transacao t5 = new Transacao(
				"XPTO8",
				new BigDecimal("1000.00"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t5);
		
		List<ItemCarteiraDto> relatorio = repository.relatorioCarteiraDeInvestimentos();
		
		Assertions
		.assertThat(relatorio)
		.hasSize(4)
		.extracting(ItemCarteiraDto::getTicker, ItemCarteiraDto::getQuantidade, ItemCarteiraDto::getPercentual)
		.containsExactlyInAnyOrder( 
				Assertions.tuple("XPTO4", 10l, new BigDecimal("4.35")),
				Assertions.tuple("XPTO5", 10l, new BigDecimal("4.35")),
				Assertions.tuple("XPTO6", 200l, new BigDecimal("86.96")),
				Assertions.tuple("XPTO8", 10l, new BigDecimal("4.35")));
	}
	
	@Test
	void deveriaRetornarRelatorioDeCarteiraDeInvestimentosConsiderandoVendas() {
		
		Usuario usuario = new Usuario("Guilherme", "guilherme@gmail.com", "589311");
		entityManager.persist(usuario);
		
		Transacao t1 = new Transacao(
				"XPTO4",
				new BigDecimal("55.50"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t1);
		
		Transacao t2 = new Transacao(
				"XPTO5",
				new BigDecimal("55.50"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t2);
		
		Transacao t3 = new Transacao(
				"XPTO6",
				new BigDecimal("100.00"),
				200,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t3);
		
		Transacao t4 = new Transacao(
				"XPTO6",
				new BigDecimal("100.00"),
				100,
				LocalDate.now(),
				TipoTransacao.VENDA,
				usuario);
		entityManager.persist(t4);
		
		Transacao t5 = new Transacao(
				"XPTO8",
				new BigDecimal("1000.00"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				usuario);
		entityManager.persist(t5);
		
		List<ItemCarteiraDto> relatorio = repository.relatorioCarteiraDeInvestimentos();
		
		Assertions
		.assertThat(relatorio)
		.hasSize(4)
		.extracting(ItemCarteiraDto::getTicker, ItemCarteiraDto::getQuantidade, ItemCarteiraDto::getPercentual)
		.containsExactlyInAnyOrder( 
				Assertions.tuple("XPTO4", 10l, new BigDecimal("0.07")),
				Assertions.tuple("XPTO5", 10l,new BigDecimal("0.07")),
				Assertions.tuple("XPTO6", 100l, new BigDecimal("0.76")),
				Assertions.tuple("XPTO8", 10l, new BigDecimal("0.07")));
	}

}
