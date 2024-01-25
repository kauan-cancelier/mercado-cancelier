package br.com.mercadocancelier.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.Produto;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ProdutoService {
	
	public List<Produto> listarTodos();
	
	public List<String> listarTodosProdutosPorNome();
	
	public List<Produto> listarPor(String nome);
	
	public Produto buscarPor(String codigo);
	
	public Produto buscarPorNome(String nome);
	
	public void salvar(
			@NotNull(message = "O produto é obrigatório. ")
			Produto produto);
	
	public void excluirPor(
			@NotNull(message = "O Id é obrigatório para remoção. ")
			Integer id);
	
	public void atualizarEstoquePor(
			@NotNull(message = "O id é obrigatório. ")
			Integer id,
			@NotNull(message =  "A quantidade é obrigatória.")
			BigDecimal quantidade);
	
}
