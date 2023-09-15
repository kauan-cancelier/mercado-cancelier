package br.com.mercadocancelier.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "Venda")
@Table(name = "vendas")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Venda {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "valor_total")
	@NotNull(message = "O valor total da venda é obrigatório. ")
	private BigDecimal valorTotal;
	
	@Column(name = "data_venda")
	@NotNull(message = "A data de venda é obrigatória para venda")
	private LocalDateTime dataDeVenda;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	@NotNull(message = "O status para venda é obrigatório. ")
	private Status status;
	
	public Venda() {
		this.status = Status.A;
	}
	
	@Transient
	public boolean isPersistido() {
		return getId() != null && getId() > 0;
	}
	
	@Transient
	public boolean isPago() {
		return getStatus() == Status.A;
	}
	
}
