package br.com.mercadocancelier.views.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Produto;

@Component
public class EstoqueTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 5;
	
	private List<Produto> produtos;

	public EstoqueTableModel() {
		this.produtos = new ArrayList<Produto>();
	}
	
	public EstoqueTableModel(List<Produto> produtos) {
		this();
		if (produtos != null && !produtos.isEmpty()) {		
			this.produtos = produtos;
		}
	}

	public int getRowCount() {	
		return produtos.size();
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
			return "Preço";
		} else if (column == 3) {
			return "Qtde";
		} else if (column == 4) {
			return "Última entrada";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return produtos.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return produtos.get(rowIndex).getNome();
		} else if (columnIndex == 2) {
			return produtos.get(rowIndex).getPreco();
		} else if (columnIndex == 3) {
			return produtos.get(rowIndex).getEstoque();
		} else if (columnIndex == 4) {
			return "criar entrada";
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Produto getPor(int rowIndex) {
		return produtos.get(rowIndex);
	}	

}
