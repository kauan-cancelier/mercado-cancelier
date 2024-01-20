package br.com.mercadocancelier.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.Venda;
import jakarta.validation.constraints.NotNull;

@Validated
public interface VendaService {
	
	public Venda salvar(
			@NotNull(message= "A venda é obrigatória")
			Venda venda);
	
	public List<Venda> listarTodas();
}
