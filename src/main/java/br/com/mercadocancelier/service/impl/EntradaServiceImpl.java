package br.com.mercadocancelier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.Entrada;
import br.com.mercadocancelier.repository.EntradasRepository;
import br.com.mercadocancelier.service.EntradaService;

@Service
public class EntradaServiceImpl implements EntradaService {
	
	@Autowired
	private EntradasRepository repository;

	@Override
	public Entrada salvar(Entrada entrada) {
		Entrada entradaSalva = repository.save(entrada);
		return entradaSalva;
	}

	@Override
	public Entrada buscarPor(Integer id) {
		Entrada entradaEncontrada = repository.buscarPor(id);
		Preconditions.checkNotNull(entradaEncontrada, "Não foi encontrada entrada para o id informado.");
		return entradaEncontrada ;
	}

	@Override
	public Entrada alterar(Entrada entrada) {
		Entrada entradaSalva = repository.buscarPor(entrada.getId());
		entradaSalva.setProduto(entrada.getProduto());
		entradaSalva.setFornecedor(entrada.getFornecedor());
		entradaSalva.setDataDeEntrada(entrada.getDataDeEntrada());
		entradaSalva.setUnidadeDeMedida(entrada.getUnidadeDeMedida());
		entradaSalva.setQuantidade(entrada.getQuantidade());
		entradaSalva.setPreco(entrada.getPreco());
		Entrada entradaAtualizada = repository.saveAndFlush(entradaSalva);
		return buscarPor(entradaAtualizada.getId());
	}

}
