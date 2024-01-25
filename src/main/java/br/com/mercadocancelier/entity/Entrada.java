package br.com.mercadocancelier.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.mercadocancelier.entity.enums.UnidadeDeMedida;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Entrada")
@Table(name = "entradas")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Entrada {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	@NotNull(message = "O produto para a entrada é obrigatório. ")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	@NotNull(message = "O fornecedor para a entrada é obrigatório. ")
	private Fornecedor fornecedor;
	
	@Column(name = "data_entrada")
	@NotNull(message = "A data para a entrada é obrigatória. ")
	private LocalDate dataDeEntrada;
	
	@Column(name = "unidade_medida")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "A unidade de medida para a entrada é obrigatória.")
	private UnidadeDeMedida unidadeDeMedida;
	
	@Column(name = "quantidade")
	@NotNull(message = "A quantidade para a entrada é obrigatória.")
	private Double quantidade;
	
	@Column(name = "preco")
	@NotNull(message = "O preco para a entrada é obrigatório.")
	private BigDecimal preco;
	
	@Column(name = "total")
	@NotNull(message = "O total para a entrada é obrigatório.")
	private BigDecimal total;
	
	public Entrada() {}

	public Entrada(Produto produto, Fornecedor fornecedor, LocalDate dataDeEntrada, 
			UnidadeDeMedida unidadeDeMedida, Double quantidade, BigDecimal preco) {
		this.produto = produto;
		this.fornecedor = fornecedor;
		this.dataDeEntrada = dataDeEntrada;
		this.unidadeDeMedida = unidadeDeMedida;
		this.quantidade = quantidade;
		this.preco = preco;
	}

}
