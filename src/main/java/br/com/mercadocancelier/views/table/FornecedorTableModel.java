package br.com.mercadocancelier.views.table;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Fornecedor;

@Component
public class FornecedorTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private final int QTDE_COLUNAS = 4;
	
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
		} else if (column == 3) {
			return "Status";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
	    Fornecedor fornecedor = fornecedores.get(rowIndex);
	    switch (columnIndex) {
	        case 0: return fornecedor.getId();
	        case 1: return fornecedor.getNome();
	        case 2: return formatarCnpj(fornecedor.getCnpj());
	        case 3: return fornecedor.getStatus();
	        default: throw new IllegalArgumentException("Índice inválido");
	    }
	}

	private String formatarCnpj(String cnpj) {
	    if (cnpj != null && cnpj.length() == 14) {
	        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
	    }
	    return cnpj;
	}
	
	public Fornecedor getPor(int rowIndex) {
		return fornecedores.get(rowIndex);
	}
	
	public void atualizarStatusNaTabela() {
		fireTableDataChanged();
	}
	
	public boolean isVazio() {
		return fornecedores.isEmpty();
	}
	
	public void limpar() {
		this.fornecedores = new ArrayList<>();
	}

}
