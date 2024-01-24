package br.com.mercadocancelier.views.vendas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Preconditions;

import br.com.mercadocancelier.entity.ItemVenda;
import br.com.mercadocancelier.entity.Venda;
import br.com.mercadocancelier.service.ItemVendaService;
import br.com.mercadocancelier.util.Conversor;
import br.com.mercadocancelier.views.table.ItensVendaTableModel;

@Component
public class TelaDetalhesDeVenda extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;

	private JLabel lblValorVendido;
	
	private JLabel lblVendaNumero;
	
	private JLabel lblData;
	
	private JLabel lblStatus;
	
	private JLabel lblTipoPagamento;
	
	@Autowired
	private ItemVendaService itemVendaService;
	
	private JTable tbItensVenda;

	
	public void setVenda(Venda venda) {
		Preconditions.checkNotNull(venda, "A venda é obrigatória ");
		this.setLabels(venda);
		this.listarProdutosPela(venda);
		this.setVisible(true);
	}
	
	public TelaDetalhesDeVenda() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblValorVendidoLabel = new JLabel("Valor vendido:");
		lblValorVendidoLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblValorVendidoLabel.setBounds(12, 69, 156, 15);
		contentPane.add(lblValorVendidoLabel);
		
		lblValorVendido = new JLabel("<dynamic>");
		lblValorVendido.setFont(new Font("Dialog", Font.BOLD, 20));
		lblValorVendido.setBounds(161, 64, 416, 24);
		contentPane.add(lblValorVendido);
		
		JLabel lblDataVendaLabel = new JLabel("Data:");
		lblDataVendaLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblDataVendaLabel.setBounds(12, 127, 75, 24);
		contentPane.add(lblDataVendaLabel);
		
		JLabel lblStatusLabel = new JLabel("Status:");
		lblStatusLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblStatusLabel.setBounds(12, 100, 93, 15);
		contentPane.add(lblStatusLabel);
		
		JLabel lblTipoDePagamentoLabel = new JLabel("Tipo de pagamento:");
		lblTipoDePagamentoLabel.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblTipoDePagamentoLabel.setBounds(12, 163, 225, 22);
		contentPane.add(lblTipoDePagamentoLabel);
		
		JLabel lblItensVendidos = new JLabel("Itens vendidos:");
		lblItensVendidos.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblItensVendidos.setBounds(12, 228, 205, 30);
		contentPane.add(lblItensVendidos);
		
		tbItensVenda = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbItensVenda);
		tbItensVenda.getTableHeader().setPreferredSize(new Dimension(tbItensVenda.getTableHeader().getWidth(), 50));
		tbItensVenda.setRowHeight(40);
	    tbItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbItensVenda.setDefaultRenderer(Object.class, centerRenderer);
		scrollPane.setBounds(12, 270, 1332, 385);
		contentPane.add(scrollPane);
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.setBounds(1227, 681, 117, 38);
		contentPane.add(btnVoltar);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, -2, 1367, 59);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Venda n°");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 30));
		lblTitle.setBounds(598, 10, 167, 35);
		lblTitle.setForeground(new Color(255, 255, 255));
		panel.add(lblTitle);
		
		lblVendaNumero = new JLabel("<dynamic>");
		lblVendaNumero.setFont(new Font("Dialog", Font.BOLD, 30));
		lblVendaNumero.setForeground(new Color(255, 255, 255));
		lblVendaNumero.setBounds(760, 12, 228, 30);
		panel.add(lblVendaNumero);
		
		lblData = new JLabel("<dynamic>");
		lblData.setFont(new Font("Dialog", Font.BOLD, 20));
		lblData.setBounds(68, 127, 449, 24);
		contentPane.add(lblData);
		
		lblStatus = new JLabel("<dynamic>");
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 20));
		lblStatus.setBounds(92, 96, 475, 24);
		contentPane.add(lblStatus);
		
		lblTipoPagamento = new JLabel("<dynamic>");
		lblTipoPagamento.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTipoPagamento.setBounds(218, 162, 325, 24);
		contentPane.add(lblTipoPagamento);
		setLocationRelativeTo(null);
	}
	
	private void listarProdutosPela(Venda venda) {
		List<ItemVenda> itensVenda = itemVendaService.listarPela(venda);
	    ItensVendaTableModel model = new ItensVendaTableModel(itensVenda);
	    tbItensVenda.setModel(model);
	}
	
	private void setLabels(Venda venda) {
		this.lblVendaNumero.setText(venda.getId().toString());
		this.lblValorVendido.setText("R$ " + Conversor.numeroParaString(venda.getValorTotal()));
		this.lblData.setText(Conversor.dataParaString(venda.getDataDeVenda()));
		this.lblStatus.setText(venda.getStatus().toString());
		this.lblTipoPagamento.setText(venda.getTipoDePagamento().toString());
	}
	
}
