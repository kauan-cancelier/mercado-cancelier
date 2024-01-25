package br.com.mercadocancelier.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mercadocancelier.entity.Entrada;

@Repository
public interface EntradasRepository extends JpaRepository<Entrada, Integer> {
	
	@Query(value = "SELECT e "
			+ "FROM Entrada e "
			+ "WHERE e.id = :id")
	public Entrada buscarPor(@Param("id") Integer id);

}
