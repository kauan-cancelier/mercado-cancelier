package br.com.mercadocancelier.views.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Fornecedor;

@Component
public class FornecedorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 3;
	
	private List<Fornecedor> fornecedores;

	public FornecedorTableModel() {
		this.fornecedores = new ArrayList<Fornecedor>();
	}
	
	public FornecedorTableModel(List<Fornecedor> fornecedores) {
		this();
		if (fornecedores != null && !fornecedores.isEmpty()) {		
			this.fornecedores = fornecedores;
		}
	}

	public int getRowCount() {	
		return fornecedores.size();
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
			return "CNPJ";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return fornecedores.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return fornecedores.get(rowIndex).getNome();
		} else if (columnIndex == 2) {
			return fornecedores.get(rowIndex).getCnpj();
		}
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Fornecedor getPor(int rowIndex) {
		return fornecedores.get(rowIndex);
	}	

}
