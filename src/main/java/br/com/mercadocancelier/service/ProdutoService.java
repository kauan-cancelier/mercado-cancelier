package br.com.mercadocancelier.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.Produto;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ProdutoService {
	
	public List<Produto> listarTodos();
	
	public void salvar(
			@NotNull(message = "O produto é obrigatório. ")
			Produto produto);
	
}
