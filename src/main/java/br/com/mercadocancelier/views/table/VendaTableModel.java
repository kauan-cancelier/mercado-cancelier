package br.com.mercadocancelier.views.table;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Venda;

@Component
public class VendaTableModel extends AbstractTableModel {

private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 4;
	
	private List<Venda> vendas;

	public VendaTableModel() {
		this.vendas = new ArrayList<>();
	}
	
	public VendaTableModel(List<Venda> vendas) {
		this();
		if (vendas != null && !vendas.isEmpty()) {		
			this.vendas = vendas;
		}
	}

	@Override
	public int getRowCount() {	
		return vendas.size();
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
			return "Data";
		} else if (column == 2) {
			return "Valor";
		} else if (column == 3) {
			return "Tipo de pagamento";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
	        return vendas.get(rowIndex).getId();
	    } else if (columnIndex == 1) {
	        return vendas.get(rowIndex).getDataDeVenda();
	    } else if (columnIndex == 2) {
	        return vendas.get(rowIndex).getValorTotal();
	    }else if (columnIndex == 3) {
	        return vendas.get(rowIndex).getTipoDePagamento();
	    }
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public Venda getPor(int rowIndex) {
		return vendas.get(rowIndex);
	}	

}