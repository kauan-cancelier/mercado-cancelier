package br.com.mercadocancelier.repository;

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

}
