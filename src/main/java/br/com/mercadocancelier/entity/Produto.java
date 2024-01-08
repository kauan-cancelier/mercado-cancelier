package br.com.mercadocancelier.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Produto")
@Table(name = "produtos")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
	public class Produto {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "codigo")
	@NotNull(message =  "O código é obrigatório. ")
	private String codigo;
	
	@Column(name = "nome")
	@NotBlank(message = "O nome do produto é obrigatório. ")
	@Size(max = 255, message = "O nome deve conter no máximo 255 caracteres")
	private String nome;
	
	@Column(name = "preco")
	@NotNull(message = "O preço do produto é obrigatório. ")
	private BigDecimal preco;
	
	@Column(name = "estoque")
	@NotNull(message = "O estoque do produto é obrigatório. ")
	private Integer estoque;
	
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}

}
