package br.com.mercadocancelier.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mercadocancelier.entity.Produto;
import jakarta.transaction.Transactional;

@Repository
public interface ProdutosRepository extends JpaRepository<Produto, Integer> {
	
	@Query(value = "SELECT p "
			+ "FROM Produto p "
			+ "WHERE p.id = :id")
	public Produto buscarPor(@Param("id") Integer id);

	@Query(value = "SELECT p "
			+ "FROM Produto p")
	public List<Produto> listarTodos();
	
	@Query("SELECT p FROM Produto p WHERE UPPER(p.nome) LIKE UPPER(:nome)")
	public List<Produto> listarPor(@Param("nome") String nome);
	
	@Modifying
	@Query("DELETE FROM Produto p WHERE p.id = :id")
	public void excluirPor(@Param("id") Integer id);
	
	@Transactional
    @Modifying
    @Query("UPDATE Produto p SET"
    		+ " p.codigo = :codigo,"
    		+ " p.nome = :nome,"
    		+ " p.preco = :preco,"
    		+ " p.estoque = :estoque"
    		+ " WHERE p.id = :id")
    void atualizarProduto(@Param("id") Integer id,
    		@Param("codigo") String codigo,
    		@Param("nome") String nome,
    		@Param("preco") BigDecimal preco,
    		@Param("estoque") Integer estoque);
	 
}
