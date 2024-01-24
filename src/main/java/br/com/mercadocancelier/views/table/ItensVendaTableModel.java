package br.com.mercadocancelier.views.table;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.ItemVenda;
import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.util.Conversor;

@Component
public class ItensVendaTableModel extends AbstractTableModel {

private static final long serialVersionUID = 1L;
	
	private final int QTDE_COLUNAS = 6;
	
	private List<ItemVenda> itensVenda;

	public ItensVendaTableModel() {
		this.itensVenda = new ArrayList<>();
	}
	
	public ItensVendaTableModel(List<ItemVenda> itensVenda) {
		this();
		if (itensVenda != null && !itensVenda.isEmpty()) {		
			this.itensVenda = itensVenda;
		}
	}

	@Override
	public int getRowCount() {	
		return itensVenda.size();
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
			return "Nome do produto";
		} else if (column == 2) {
			return "Qtde";
		} else if (column == 3) {
			return "Estoque";
		} else if (column == 4) {
			return "Valor unit";
		} else if (column == 5) {
			return "Total do produto";
		}
		throw new IllegalArgumentException("Indíce inválido");
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ItemVenda itemVenda = itensVenda.get(rowIndex);
		Produto produto = itemVenda.getProduto();
		if (columnIndex == 0) {
	        return produto.getCodigo();
	    } else if (columnIndex == 1) {
	        return produto.getNome();
	    } else if (columnIndex == 2) {
	        return Conversor.numeroParaString(itemVenda.getQuantidade()) + ' ' + produto.getUnidadeDeMedida();
	    }else if (columnIndex == 3) {
	        return Conversor.numeroParaString(produto.getEstoque());
	    } else if (columnIndex == 4) {
	        return "R$ " + Conversor.numeroParaString(produto.getPreco());
	    } else if (columnIndex == 5) {
	        return "R$ " + Conversor.numeroParaString(itemVenda.getPrecoTotal());
	    }
		throw new IllegalArgumentException("Índice inválido");
	}
	
	public ItemVenda getPor(int rowIndex) {
		return itensVenda.get(rowIndex);
	}	

}