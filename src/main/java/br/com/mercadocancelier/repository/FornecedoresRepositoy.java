package br.com.mercadocancelier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	public List<Fornecedor> listarPor(@Param("nome") String nome);
	
	@Query(value = "SELECT f " 
			+ "FROM Fornecedor f " 
			+ "WHERE Upper(f.nome) = Upper(:nome)")
	public Fornecedor buscarPor(@Param("nome") String nome);
	
	@Query(value = "SELECT f " 
			+ "FROM Fornecedor f " 
			+ "WHERE Upper(f.cnpj) = Upper(:cnpj)")
	public Fornecedor buscarPorCnpj(@Param("cnpj") String cnpj);
	
	@Query(value = "SELECT f "
			+ "FROM Fornecedor f "
			+ "WHERE f.id = :id")
	public Fornecedor buscarPor(@Param("id") Integer id);
	
	@Query(value = "SELECT f "
			+ "FROM Fornecedor f "
			+ "ORDER BY f.nome")
	public List<Fornecedor> listarTodos();
	
	@Modifying
	@Query("UPDATE Fornecedor f "
			+ "SET f.status = 'I' "
			+ "WHERE f.status = 'A' "
			+ "AND f.id = :id")
	public Integer inativarPor(@Param("id") Integer id);
	
	@Modifying
	@Query("UPDATE Fornecedor f "
			+ "SET f.status = 'A' "
			+ "WHERE f.status = 'I' "
			+ "AND f.id = :id")
	public Integer ativarPor(@Param("id") Integer id);
	
}
