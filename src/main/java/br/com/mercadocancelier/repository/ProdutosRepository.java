package br.com.mercadocancelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mercadocancelier.entity.Produto;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto, Integer>{
	
	@Query(value = "SELECT p FROM Produto p WHERE p.id = :id")
	public Produto buscarPor(@Param("id") Integer id);

}
