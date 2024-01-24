package br.com.mercadocancelier.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import br.com.mercadocancelier.entity.Fornecedor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Validated
public interface FornecedorService {
	
	public Fornecedor salvar(
			@NotNull(message = "O fornecedor é obrigatório. ")
			Fornecedor fornecedor);
	
	public Fornecedor buscarPor(
			@NotNull(message = "O id é obrigatório.") 
			@Positive(message = "O id deve ser positivo.")
			Integer id);
	
	public Fornecedor alterar(
			@Valid
			@NotNull(message = "O fornecedor é obrigatório")
			Fornecedor fornecedor);
	
	public List<Fornecedor> listarPor(
			@NotBlank(message = "O nome é obrigatório.") 
			@Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
			String nome);
	
	public List<Fornecedor> listarTodos();
	
	public Integer inativarPor(
			@NotNull(message = "O Id é obrigatório para inativação. ")
			Integer id);
	
	public Integer ativarPor(
			@NotNull(message = "O Id é obrigatório para ativação. ")
			Integer id);


}
