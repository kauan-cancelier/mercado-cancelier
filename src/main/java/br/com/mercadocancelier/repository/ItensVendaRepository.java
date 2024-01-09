package br.com.mercadocancelier.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mercadocancelier.entity.ItemVenda;
import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.entity.Venda;

public interface ItensVendaRepository extends JpaRepository<ItemVenda, Integer> {
	
	@Query(value = "SELECT iv "
			+ "FROM ItemVenda iv "
			+ "WHERE iv.id = :id")
	public ItemVenda buscarPor(@Param("id") Integer id);

	 @Query(value = "SELECT iv "
	 		+ "FROM ItemVenda iv "
	 		+ "WHERE iv.venda = :venda")
	 public List<ItemVenda> listarPor(@Param("venda") Venda venda);

	 @Query(value = "DELETE "
	 		+ "FROM ItemVenda iv "
	 		+ "WHERE iv.id = :id ")
	 public void removerPor(@Param("id") Integer id);

	 @Query(value = "SELECT iv "
		 		+ "FROM ItemVenda iv "
		 		+ "WHERE iv.produto = :produto")
	 public List<ItemVenda> listarPor(@Param("produto") Produto produto);
}
