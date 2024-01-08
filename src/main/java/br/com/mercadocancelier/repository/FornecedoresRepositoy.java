package br.com.mercadocancelier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mercadocancelier.entity.Fornecedor;

@Repository
public interface FornecedoresRepositoy extends JpaRepository<Fornecedor, Integer> {
	
	@Query(value = "SELECT f "
			+ "FROM Fornecedor f "
			+ "WHERE Upper(f.nome) LIKE Upper(:nome) "
			+ "ORDER BY f.nome")
	public List<Fornecedor> listarPor(String nome);
	
	@Query(value = "SELECT f " 
			+ "FROM Fornecedor f " 
			+ "WHERE Upper(f.nome) = Upper(:nome)")
	public Fornecedor buscarPor(String nome);
	
	@Query(value = "SELECT f "
			+ "FROM Fornecedor f "
			+ "WHERE f.id = :id")
	public Fornecedor buscarPor(@Param("id") Integer id);
	
	@Query(value = "SELECT f "
			+ "FROM Fornecedor f")
	public List<Fornecedor> listarTodos();

}
