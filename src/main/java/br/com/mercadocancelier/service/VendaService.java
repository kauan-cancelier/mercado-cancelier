package br.com.mercadocancelier.service;

import java.time.LocalDateTime;
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
	
	public List<Venda> listar(
			@NotNull(message = "O campo desde é obrigatório. ")
			LocalDateTime desde, 
			@NotNull(message = "O campo até é obrigatório. ")
			LocalDateTime ate);
	
}
