package br.com.mercadocancelier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.repository.ProdutosRepository;
import br.com.mercadocancelier.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutosRepository produtosRepository;
	
	public List<Produto> listarTodos() {
		return produtosRepository.listarTodos();
	}

	@Override
	public void salvar(Produto produto) {
		Preconditions.checkNotNull(produto, "O produto é obrigatório");
		produtosRepository.save(produto);
	}
	
}
