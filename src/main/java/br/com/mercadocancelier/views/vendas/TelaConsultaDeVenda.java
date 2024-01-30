package br.com.mercadocancelier.views.vendas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
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

	private JFormattedTextField ftfDesde;

	private JFormattedTextField ftfAte;

	private JTable tbVendas;

	@Autowired
	private VendaService vendaService;

	@Autowired
	@Lazy
	private TelaDetalhesDeVenda telaDetalhesDeVenda;

	@Autowired
	@Lazy
	private TelaDeVenda telaDeVenda;

	@PostConstruct
	public void abrirTela() {
		this.listarProdutos();
	}

	public TelaConsultaDeVenda() {
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 20));

		JPanel panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 20));

		JPanel panelCadastrar = new JPanel();
		panelSuperior.add(panelCadastrar, BorderLayout.EAST);
		panelCadastrar.setLayout(new BorderLayout(0, 0));

		JButton btnNovaVenda = new JButton("Nova venda");
		btnNovaVenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaDeVenda.setVisible(true);
				dispose();
			}
		});
		btnNovaVenda.setHorizontalAlignment(SwingConstants.RIGHT);
		btnNovaVenda.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelCadastrar.add(btnNovaVenda, BorderLayout.NORTH);

		JPanel panelFiltrar = new JPanel();
		panelSuperior.add(panelFiltrar, BorderLayout.SOUTH);
		panelFiltrar.setLayout(new BorderLayout(0, 0));

		JPanel panelItens = new JPanel();
		panelItens.setBorder(null);
		panelFiltrar.add(panelItens, BorderLayout.NORTH);
		panelItens.setLayout(new GridLayout(1, 1, 20, 0));

		JPanel panelDesde = new JPanel();
		panelItens.add(panelDesde);
		panelDesde.setLayout(new BorderLayout(10, 0));

		MaskFormatter dateFormatter = null;
		try {
			dateFormatter = new MaskFormatter("##/##/#### ##:##");
			dateFormatter.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			e.printStackTrace();
		}

		ftfDesde = new JFormattedTextField(dateFormatter);
		panelDesde.add(ftfDesde);
		ftfDesde.setToolTipText("");
		ftfDesde.setColumns(10);

		JLabel lblDesde = new JLabel("Desde");
		panelDesde.add(lblDesde, BorderLayout.WEST);

		JPanel panelAte = new JPanel();
		panelItens.add(panelAte);
		panelAte.setLayout(new BorderLayout(10, 0));

		JLabel lblAte = new JLabel("Ate");
		panelAte.add(lblAte, BorderLayout.WEST);

		ftfAte = new JFormattedTextField(dateFormatter);
		panelAte.add(ftfAte, BorderLayout.CENTER);
		ftfAte.setColumns(10);

		JPanel panelButtonFiltrar = new JPanel();
		panelItens.add(panelButtonFiltrar);
		panelButtonFiltrar.setLayout(new BorderLayout(0, 0));

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.setHorizontalAlignment(SwingConstants.RIGHT);
		btnFiltrar.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String desdeText = ftfDesde.getText();
				String ateText = ftfAte.getText();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				LocalDateTime desde;
				LocalDateTime ate;

				if (desdeText.contains("_")) {
					desde = LocalDateTime.now().minusDays(30);
					String desdeTextFormatted = desde.format(formatter);
					ftfDesde.setText(desdeTextFormatted);
				} else {
					desde = LocalDateTime.parse(desdeText, formatter);					
				}
				
				
				if (ateText.contains("_")) {
					ate = LocalDateTime.now();
					String ateTextFormatted = ate.format(formatter);
					ftfAte.setText(ateTextFormatted);
				} else {
					ate = LocalDateTime.parse(ateText, formatter);
				}

				listarProdutos(desde, ate);
			}
		});
		panelButtonFiltrar.add(btnFiltrar, BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panelSuperior.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Consulta de vendas");
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		panel.add(lblNewLabel, BorderLayout.NORTH);

		JPanel panelMeio = new JPanel();
		contentPane.add(panelMeio, BorderLayout.CENTER);
		panelMeio.setLayout(new BorderLayout(0, 0));

		tbVendas = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbVendas);
		tbVendas.getTableHeader().setPreferredSize(new Dimension(tbVendas.getTableHeader().getWidth(), 50));
		tbVendas.setRowHeight(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbVendas.setDefaultRenderer(Object.class, centerRenderer);
		panelMeio.add(scrollPane, BorderLayout.CENTER);

		JPanel panelAcoes = new JPanel();
		contentPane.add(panelAcoes, BorderLayout.SOUTH);
		panelAcoes.setLayout(new BorderLayout(0, 50));

		JPanel panelBotoes = new JPanel();
		panelAcoes.add(panelBotoes, BorderLayout.EAST);
		panelBotoes.setLayout(new GridLayout(1, 1, 20, 0));

		JButton btnDetalhes = new JButton("Detalhes");
		btnDetalhes.setBackground(new Color(238, 238, 238));
		btnDetalhes.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnDetalhes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int linhaSelecionada = tbVendas.getSelectedRow();
				if (linhaSelecionada != -1) {
					VendaTableModel model = (VendaTableModel) tbVendas.getModel();
					Venda venda = model.getPor(linhaSelecionada);
					telaDetalhesDeVenda.setVenda(venda);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Escolha uma linha para continuar ", "Aviso! ",
							JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		panelBotoes.add(btnDetalhes);
	}

	private void listarProdutos() {
		List<Venda> vendas = new ArrayList<>();
		vendas.addAll(vendaService.listarTodas());
		VendaTableModel model = new VendaTableModel(vendas);
		tbVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbVendas.setModel(model);
		tbVendas.updateUI();
	}

	private void listarProdutos(LocalDateTime desde, LocalDateTime ate) {
		List<Venda> vendas = new ArrayList<>();
		vendas.addAll(vendaService.listar(desde, ate));
		VendaTableModel model = new VendaTableModel(vendas);
		tbVendas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbVendas.setModel(model);
	}
}
