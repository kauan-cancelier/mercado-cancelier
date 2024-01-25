package br.com.mercadocancelier.service;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.Entrada;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public interface EntradaService {
	
	public Entrada salvar(
			@NotNull(message = "A entrada é obrigatória. ")
			Entrada entrada);
	
	public Entrada buscarPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id);
	
	public Entrada alterar(
			@Valid
			@NotNull(message = "A entrada é obrigatória.")
			Entrada entrada);

}
