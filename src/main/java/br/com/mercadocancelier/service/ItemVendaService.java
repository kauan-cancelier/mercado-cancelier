package br.com.mercadocancelier.service;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.ItemVenda;
import jakarta.validation.constraints.NotNull;

@Validated
public interface ItemVendaService {

	public void excluirPor(@NotNull(message = "O id não pode ser vazio. ") Integer id);
	
	public void salvar(@NotNull(message = "O Item venda é obrigatório. ") ItemVenda itemVenda);
}
