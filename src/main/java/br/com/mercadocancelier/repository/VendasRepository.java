package br.com.mercadocancelier.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mercadocancelier.entity.Venda;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Integer>{
	
	@Query(value = "SELECT v "
			+ "FROM Venda v "
			+ "WHERE v.id = :id")
	public Venda buscarPor(@Param("id") Integer id);
	
	@Query(value = "SELECT v "
			+ "FROM Venda v "
			+ "WHERE (v.dataDeVenda >= :desde) "
			+ "AND (v.dataDeVenda <= :ate) "
			+ "ORDER BY v.dataDeVenda DESC")
	public List<Venda> listar(
			@Param("desde") LocalDateTime desde,
			@Param("ate") LocalDateTime ate);
	
	@Query(value = "SELECT v "
			+ "FROM Venda v "
			+ "ORDER BY v.dataDeVenda DESC")
	public List<Venda> listarTodas();

}
