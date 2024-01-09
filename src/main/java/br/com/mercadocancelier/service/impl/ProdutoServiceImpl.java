package br.com.mercadocancelier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.ItemVenda;
import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.repository.ItensVendaRepository;
import br.com.mercadocancelier.repository.ProdutosRepository;
import br.com.mercadocancelier.service.ProdutoService;
import jakarta.transaction.Transactional;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutosRepository produtosRepository;
	
	@Autowired
	private ItensVendaRepository itensVendaRepository;
	
	public List<Produto> listarTodos() {
		return produtosRepository.listarTodos();
	}

	@Override
	public List<Produto> listarPor(String nome) {
		Preconditions.checkNotNull(nome, "O nome do produto é obrigatório para listagem. ");
		return produtosRepository.listarPor(nome + "%");
	}

	@Override
	public Produto buscarPor(String codigo) {
		Preconditions.checkNotNull(codigo, "O codigo do produto é obrigatório para listagem. ");
		return produtosRepository.buscarPor(codigo);
	}

	@Override
	public void salvar(Produto produto) {
		Preconditions.checkNotNull(produto, "O produto é obrigatório");
		if (produto.getId() != null && produto.getId() > 0) {
			produtosRepository.atualizarProduto(produto.getId(), produto.getCodigo(), produto.getNome(),
					produto.getPreco(), produto.getEstoque());
		}
		produtosRepository.save(produto);
	}

	@Transactional
	@Override
	public void excluirPor(Integer id) {
		Preconditions.checkNotNull(id, "O produto é obrigatório");
		List<ItemVenda> itens = itensVendaRepository.listarPor(produtosRepository.buscarPor(id));
		Preconditions.checkArgument(itens.size() == 0, "Existem vendas vinculadas a esse produto! portanto ele não pode ser excluido. ");
		produtosRepository.excluirPor(id);			
	}

}
