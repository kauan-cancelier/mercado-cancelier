package br.com.mercadocancelier.service;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.Venda;

@Validated
public interface VendaService {
	
	public Venda salvar(Venda venda);
}
