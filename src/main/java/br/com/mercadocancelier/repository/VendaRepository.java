package br.com.mercadocancelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mercadocancelier.entity.Venda;

public interface VendaRepository extends JpaRepository<Venda, Integer>{
	
	@Query(value = "SELECT v FROM Venda v WHERE v.id = :id")
	public Venda buscarPor(@Param("id") Integer id);

}
