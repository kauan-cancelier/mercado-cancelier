package br.com.mercadocancelier.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.Venda;
import br.com.mercadocancelier.repository.VendasRepository;
import br.com.mercadocancelier.service.VendaService;

@Service
public class VendaServiceImpl implements VendaService {

	@Autowired
	private VendasRepository vendasRepository;
	
	@Override
	public Venda salvar(Venda venda) {
		Preconditions.checkNotNull(venda, "A venda é obrigatória. ");
		return vendasRepository.save(venda);
	}

	@Override
	public List<Venda> listarTodas() {
		return vendasRepository.findAll();
	}

}
