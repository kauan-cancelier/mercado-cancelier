package br.com.mercadocancelier.views.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Produto;


@Component
public class ProdutosTableModel extends AbstractTableModel {

private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 5;
	
	private List<Produto> produtos;

	public ProdutosTableModel() {
		this.produtos = new ArrayList<>();
	}
	
	public ProdutosTableModel(List<Produto> produtos) {
		this();
		if (produtos != null && !produtos.isEmpty()) {		
			this.produtos = produtos;
		}
	}

	@Override
	public int getRowCount() {	
		return produtos.size();
	}
	
	@Override
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
			return "Estoque";
		} else if (column == 4) {
			return "Unidade de medida";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return produtos.get(rowIndex).getCodigo();
		} else if (columnIndex == 1) {
			return produtos.get(rowIndex).getNome();
		} else if (columnIndex == 2) {
			return produtos.get(rowIndex).getPreco();
		} else if (columnIndex == 3) {
			return produtos.get(rowIndex).getEstoque();
		} else if (columnIndex == 4) {
			return produtos.get(rowIndex).getUnidadeDeMedida();
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Produto getPor(int rowIndex) {
		return produtos.get(rowIndex);
	}	

}