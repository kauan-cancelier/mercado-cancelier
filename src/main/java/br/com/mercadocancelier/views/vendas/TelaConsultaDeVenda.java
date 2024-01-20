package br.com.mercadocancelier.views.vendas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Venda;
import br.com.mercadocancelier.service.VendaService;
import br.com.mercadocancelier.views.table.VendaTableModel;
import jakarta.annotation.PostConstruct;


@Component
public class TelaConsultaDeVenda extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTable tbVendas;
	
	@Autowired
	private VendaService vendaService;
	
	@PostConstruct
	public void abrirTela() {
		this.listarProdutos();
	}
	
	public TelaConsultaDeVenda() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		
		tbVendas = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbVendas);
		tbVendas.getTableHeader().setPreferredSize(new Dimension(tbVendas.getTableHeader().getWidth(), 50));
		tbVendas.setRowHeight(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbVendas.setDefaultRenderer(Object.class, centerRenderer);
		
		JButton btnExcluir = new JButton("Excluir");
		
		JButton btnDetalhes = new JButton("Detalhes");
		
		JButton btnNovaVenda = new JButton("Nova venda");
		
		JPanel panelFiltro = new JPanel();
		panelFiltro.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Filtros", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(1092, Short.MAX_VALUE)
					.addComponent(btnDetalhes, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1346, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panelFiltro, GroupLayout.PREFERRED_SIZE, 863, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 365, Short.MAX_VALUE)
					.addComponent(btnNovaVenda, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addComponent(btnNovaVenda, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panelFiltro, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDetalhes, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		MaskFormatter dateFormatter = null;
		try {
		    dateFormatter = new MaskFormatter("##/##/####");
		    dateFormatter.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		JLabel lblDesde = new JLabel("Desde");
		
		JFormattedTextField ftfDesde = new JFormattedTextField(dateFormatter);
		
		JLabel lblAt = new JLabel("Até");
		
		JFormattedTextField ftfAte = new JFormattedTextField(dateFormatter);
		
		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		GroupLayout gl_panelFiltro = new GroupLayout(panelFiltro);
		gl_panelFiltro.setHorizontalGroup(
			gl_panelFiltro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFiltro.createSequentialGroup()
					.addGap(18)
					.addComponent(lblDesde)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ftfDesde, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
					.addGap(28)
					.addComponent(lblAt, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ftfAte, GroupLayout.PREFERRED_SIZE, 290, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnFiltrar)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_panelFiltro.setVerticalGroup(
			gl_panelFiltro.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFiltro.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelFiltro.createParallelGroup(Alignment.BASELINE)
						.addComponent(ftfDesde, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDesde)
						.addComponent(lblAt)
						.addComponent(ftfAte, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnFiltrar))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panelFiltro.setLayout(gl_panelFiltro);
		
		JLabel lblConsultaDeVendas = new JLabel("Consulta de vendas");
		lblConsultaDeVendas.setForeground(Color.WHITE);
		lblConsultaDeVendas.setFont(new Font("Dialog", Font.BOLD, 40));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(460, Short.MAX_VALUE)
					.addComponent(lblConsultaDeVendas)
					.addGap(445))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblConsultaDeVendas)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
	
	private void listarProdutos() {
		List<Venda> vendas = new ArrayList<>();
		vendas.addAll(vendaService.listarTodas());
		VendaTableModel model = new VendaTableModel(vendas);
		tbVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbVendas.setModel(model);
	}
}
