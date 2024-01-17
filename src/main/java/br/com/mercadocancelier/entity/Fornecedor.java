package br.com.mercadocancelier.entity;

import br.com.mercadocancelier.entity.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Fornecedor")
@Table(name = "fornecedores")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Fornecedor implements Validavel {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "nome")
	@NotBlank(message = "O nome do fornecedor é obrigatório. ")
	@Size(max = 255, message = "O nome deve conter no máximo 255 caracteres")
	private String nome;
	
	@Column(name = "cnpj")
	@NotBlank(message = "O CNPJ do fornecedor é obrigatório. ")
	@Size(max = 20, message = "O CNPJ deve conter no máximo 20 caracteres")
	private String cnpj;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "O status para o fornecedor é obrigatório. ")
	private Status status;
	
	public Fornecedor() {
		this.status = Status.A;
	}
	
	@Transient
	@Override
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}

	@Override
	public String toString() {
		return nome;
	}
	
	

}
