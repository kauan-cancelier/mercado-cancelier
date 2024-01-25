package br.com.mercadocancelier.views.table;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Entrada;

@Component
public class EntradaTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 6;

	private List<Entrada> entradas;

	public EntradaTableModel() {
		this.entradas = new ArrayList<Entrada>();
	}

	public EntradaTableModel(List<Entrada> entradas) {
		this();
		if (entradas != null && !entradas.isEmpty()) {
			this.entradas = entradas;
		}
	}

	public List<Entrada> getEntradas() {
		return new ArrayList<>(entradas);
	}

	public int getRowCount() {
		return entradas.size();
	}

	public int getColumnCount() {
		return QTDE_COLUNAS;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Código";
		} else if (column == 1) {
			return "Nome";
		} else if (column == 2) {
			return "Fornecedor";
		} else if (column == 3) {
			return "Preço";
		} else if (column == 4) {
			return "Qtde";
		} else if (column == 5) {
			return "Total";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return entradas.get(rowIndex).getProduto().getCodigo();
		} else if (columnIndex == 1) {
			return entradas.get(rowIndex).getProduto().getNome();
		} else if (columnIndex == 2) {
			return entradas.get(rowIndex).getFornecedor();
		} else if (columnIndex == 3) {
			return entradas.get(rowIndex).getPreco();
		} else if (columnIndex == 4) {
			return entradas.get(rowIndex).getQuantidade() + " "
		+ entradas.get(rowIndex).getProduto().getUnidadeDeMedida();
		} else if (columnIndex == 5) {
			return "R$" + entradas.get(rowIndex).getTotal();
		}
		throw new IllegalArgumentException("Índice inválido");
	}

	public Entrada getPor(int rowIndex) {
		return entradas.get(rowIndex);
	}

	public void adicionarEntrada(Entrada entrada) {
		BigDecimal quantidade = BigDecimal.valueOf(entrada.getQuantidade());
		entrada.setTotal(quantidade.multiply(entrada.getPreco()));
		entradas.add(entrada);
		fireTableRowsInserted(entradas.size() - 1, entradas.size() - 1);
	}

	public void removerEntrada(int rowIndex) {
		if (rowIndex >= 0 && rowIndex < entradas.size()) {
			entradas.get(rowIndex);
			entradas.remove(rowIndex);
			fireTableRowsDeleted(rowIndex, rowIndex);
		}
	}

	public void atualizarEntrada(Entrada entrada) {
		BigDecimal quantidade = BigDecimal.valueOf(entrada.getQuantidade());
		entrada.setTotal(quantidade.multiply(entrada.getPreco()));
		int index = entradas.indexOf(entrada);
		if (index != -1) {
			entradas.set(index, entrada);
			fireTableRowsUpdated(index, index);
		}
	}

	public void limparEntradas() {
		entradas.clear();
		fireTableDataChanged();
	}

	public BigDecimal calcularTotal() {
		BigDecimal totalGeral = BigDecimal.ZERO;

		for (Entrada entrada : entradas) {
			if (entrada.getTotal() != null) {
				totalGeral = totalGeral.add(entrada.getTotal());
			}
		}

		return totalGeral;
	}

}
