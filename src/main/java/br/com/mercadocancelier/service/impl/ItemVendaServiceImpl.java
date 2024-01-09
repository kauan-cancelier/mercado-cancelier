package br.com.mercadocancelier.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.ItemVenda;
import br.com.mercadocancelier.repository.ItensVendaRepository;
import br.com.mercadocancelier.service.ItemVendaService;

@Service
public class ItemVendaServiceImpl implements ItemVendaService {

	@Autowired
	private ItensVendaRepository itensVendaRepository;

	public void excluirPor(Integer id) {
		Preconditions.checkNotNull(id, "O id é obrigatório para exclusão. ");
		itensVendaRepository.removerPor(id);
	}

	@Override
	public void salvar(ItemVenda itemVenda) {
		Preconditions.checkNotNull(itemVenda, "O item venda é obrigatório. ");
		itensVendaRepository.save(itemVenda);
	}

}
