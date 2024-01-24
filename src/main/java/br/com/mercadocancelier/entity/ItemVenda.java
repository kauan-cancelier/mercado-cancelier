package br.com.mercadocancelier.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "ItemVenda")
@Table(name = "itens_venda")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemVenda implements Validavel {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;

	@Column(name = "preco_total")
	@NotNull(message = "O preço total do item de venda é obrigatório. ")
	private BigDecimal precoTotal;

	@Column(name = "quantidade")
	@NotNull(message = "A quantidade do item de venda é obrigatório. ")
	private BigDecimal quantidade;
	
	@ManyToOne
	@JoinColumn(name = "id_produto")
	@NotNull(message = "O produto do item de venda é obrigatório. ")
	private Produto produto;
	
	@ManyToOne
	@JoinColumn(name = "id_venda")
	@NotNull(message = "A venda é obrigatória no item de venda. ")
	private Venda venda;
	
	@Transient
	@Override
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}

}
