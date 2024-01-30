package br.com.mercadocancelier.views.vendas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
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

	
	@Autowired
	private ItemVendaService itemVendaService;
	
	private JTable tbItensVenda;
	
	private JLabel lblSetValorVendido;
	
	private JLabel lblSetTipoDePagamento;
	
	private JLabel lblSetData;
	
	private JLabel lblSetStatus;
	
	private JLabel lblSetNumeroVenda;
	
	@Autowired
	@Lazy
	private TelaConsultaDeVenda telaConsultaDeVenda;
	
	public void setVenda(Venda venda) {
		Preconditions.checkNotNull(venda, "A venda é obrigatória ");
		this.setLabels(venda);
		this.listarProdutosPela(venda);
		this.setVisible(true);
	}
	
	public TelaDetalhesDeVenda() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));
		
		JPanel panelSuperior = new JPanel();
		panelSuperior.setBackground(SystemColor.activeCaption);
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panelSuperior.add(panel, BorderLayout.NORTH);
		
		JLabel lblDetalhes = new JLabel("Detalhes da venda n°");
		panel.add(lblDetalhes);
		lblDetalhes.setForeground(Color.WHITE);
		lblDetalhes.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblDetalhes.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblSetNumeroVenda = new JLabel("");
		panel.add(lblSetNumeroVenda);
		lblSetNumeroVenda.setForeground(Color.WHITE);
		lblSetNumeroVenda.setFont(new Font("Dialog", Font.BOLD, 30));
		
		JPanel panelInfos = new JPanel();
		panelSuperior.add(panelInfos, BorderLayout.SOUTH);
		panelInfos.setLayout(new GridLayout(0, 2, 0, 10));
		
		JLabel lblSpacing = new JLabel("");
		panelInfos.add(lblSpacing);
		
		JLabel lblSpacing1 = new JLabel("");
		panelInfos.add(lblSpacing1);
		
		JLabel lblValorVendido = new JLabel("Valor vendido:");
		lblValorVendido.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelInfos.add(lblValorVendido);
		
		lblSetValorVendido = new JLabel("");
		lblSetValorVendido.setFont(new Font("Dialog", Font.BOLD, 20));
		panelInfos.add(lblSetValorVendido);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelInfos.add(lblStatus);
		
		lblSetStatus = new JLabel("");
		lblSetStatus.setFont(new Font("Dialog", Font.BOLD, 20));
		panelInfos.add(lblSetStatus);
		
		JLabel lblData = new JLabel("Data:");
		lblData.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelInfos.add(lblData);
		
		lblSetData = new JLabel("");
		lblSetData.setFont(new Font("Dialog", Font.BOLD, 20));
		panelInfos.add(lblSetData);
		
		JLabel lblTipoDePagamento = new JLabel("Tipo de pagamento:");
		lblTipoDePagamento.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelInfos.add(lblTipoDePagamento);
		
		lblSetTipoDePagamento = new JLabel("");
		lblSetTipoDePagamento.setFont(new Font("Dialog", Font.BOLD, 20));
		panelInfos.add(lblSetTipoDePagamento);
		
		JLabel lblSpacing2 = new JLabel("");
		panelInfos.add(lblSpacing2);
		
		JPanel panelMeio = new JPanel();
		panelMeio.setBorder(new TitledBorder(new LineBorder(new Color(255, 255, 255)), "Itens vendidos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		contentPane.add(panelMeio, BorderLayout.CENTER);
		
		tbItensVenda = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbItensVenda);
		tbItensVenda.getTableHeader().setPreferredSize(new Dimension(tbItensVenda.getTableHeader().getWidth(), 50));
		tbItensVenda.setRowHeight(40);
	    tbItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbItensVenda.setDefaultRenderer(Object.class, centerRenderer);
		panelMeio.setLayout(new BorderLayout(0, 0));
		scrollPane.setBounds(12, 270, 1332, 385);
		panelMeio.add(scrollPane);
		
		JPanel panelInferior = new JPanel();
		contentPane.add(panelInferior, BorderLayout.SOUTH);
		panelInferior.setLayout(new BorderLayout(0, 0));
		
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaConsultaDeVenda.abrirTela();
				dispose();
			}
		});
		btnVoltar.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelInferior.add(btnVoltar, BorderLayout.EAST);
	}
	
	private void listarProdutosPela(Venda venda) {
		List<ItemVenda> itensVenda = itemVendaService.listarPela(venda);
	    ItensVendaTableModel model = new ItensVendaTableModel(itensVenda);
	    tbItensVenda.setModel(model);
	}
	
	private void setLabels(Venda venda) {
		this.lblSetNumeroVenda.setText(venda.getId().toString());
		this.lblSetValorVendido.setText("R$ " + Conversor.numeroParaString(venda.getValorTotal()));
		this.lblSetData.setText(Conversor.dataParaString(venda.getDataDeVenda()));
		this.lblSetStatus.setText(venda.getStatus().toString());
		this.lblSetTipoDePagamento.setText(venda.getTipoDePagamento().toString());
	}

}
