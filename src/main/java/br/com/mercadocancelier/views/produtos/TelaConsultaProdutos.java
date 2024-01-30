package br.com.mercadocancelier.views.produtos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.service.ProdutoService;
import br.com.mercadocancelier.views.table.ProdutosTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@Component
public class TelaConsultaProdutos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFiltro;

	private JTable tbProdutos;

	@Autowired
	@Lazy
	private TelaCadastroProdutos telaCadastroDeProdutos;

	@Autowired
	private ProdutoService produtoService;

	public void abrirTela() {
		this.configurarTabela();
		this.listarProdutos();
		this.setVisible(true);
	}

	public TelaConsultaProdutos() {
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

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaCadastroDeProdutos.abrirTela();
			}
		});
		btnCadastrar.setHorizontalAlignment(SwingConstants.RIGHT);
		btnCadastrar.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelCadastrar.add(btnCadastrar, BorderLayout.NORTH);

		JPanel panelFiltrar = new JPanel();
		panelSuperior.add(panelFiltrar, BorderLayout.SOUTH);
		panelFiltrar.setLayout(new BorderLayout(20, 0));

		JLabel lblNomeDoProduto = new JLabel("Nome do produto");
		panelFiltrar.add(lblNomeDoProduto, BorderLayout.NORTH);

		txtFiltro = new JTextField();
		panelFiltrar.add(txtFiltro, BorderLayout.CENTER);
		txtFiltro.setColumns(50);

		JButton btnFiltrar = new JButton("Filtrar");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String filtro = txtFiltro.getText();
				if (filtro.isBlank()) {
					listarProdutos();
				} else {
					listarProdutosPor(filtro);
				}
			}
		});
		panelFiltrar.add(btnFiltrar, BorderLayout.EAST);
		btnFiltrar.setFont(new Font("Dialog", Font.PLAIN, 20));

		JPanel panelMeio = new JPanel();
		contentPane.add(panelMeio, BorderLayout.CENTER);
		panelMeio.setLayout(new BorderLayout(0, 0));

		tbProdutos = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbProdutos);
		tbProdutos.getTableHeader().setPreferredSize(new Dimension(tbProdutos.getTableHeader().getWidth(), 50));
		tbProdutos.setRowHeight(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbProdutos.setDefaultRenderer(Object.class, centerRenderer);		panelMeio.add(scrollPane);

		JPanel panelAcoes = new JPanel();
		contentPane.add(panelAcoes, BorderLayout.SOUTH);
		panelAcoes.setLayout(new BorderLayout(0, 50));

		JPanel panelBotoes = new JPanel();
		panelAcoes.add(panelBotoes, BorderLayout.EAST);
		panelBotoes.setLayout(new GridLayout(1, 1, 20, 0));

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(SystemColor.info);
		btnEditar.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tbProdutos.getSelectedRow();
				if (linhaSelecionada != -1) {
					ProdutosTableModel model = (ProdutosTableModel) tbProdutos.getModel();
					Produto produtoSelecionado = model.getPor(linhaSelecionada);
					telaCadastroDeProdutos.abrirModoEdicao(produtoSelecionado);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Escolha uma linha para continuar ", "Aviso! ",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panelBotoes.add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setForeground(Color.WHITE);
		btnExcluir.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnExcluir.setBackground(Color.RED);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tbProdutos.getSelectedRow() >= 0) {
					int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente remover o produto?",
							"Atenção! ", JOptionPane.YES_NO_OPTION);
					if (op == JOptionPane.YES_OPTION) {
						ProdutosTableModel model = (ProdutosTableModel) tbProdutos.getModel();
						Produto produtoSelecionado = model.getPor(tbProdutos.getSelectedRow());
						try {
							produtoService.excluirPor(produtoSelecionado.getId());
							JOptionPane.showMessageDialog(contentPane, "Produto removido.");
							tbProdutos.clearSelection();
							listarProdutos();
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane, e2.getMessage(), "Erro ",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Escolha uma linha para continuar ", "Aviso! ",
							JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		panelBotoes.add(btnExcluir);
		
	}

	private void listarProdutos() {
		List<Produto> produtos = new ArrayList<>();
		produtos.addAll(produtoService.listarTodos());
		ProdutosTableModel model = new ProdutosTableModel(produtos);
		tbProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProdutos.setModel(model);
	}

	private void listarProdutosPor(String nome) {
		List<Produto> produtos = new ArrayList<>();
		produtos.addAll(produtoService.listarPor(nome));
		ProdutosTableModel model = new ProdutosTableModel(produtos);
		tbProdutos.setModel(model);
	}

	private void configurarTabela() {
		tbProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbProdutos.getTableHeader().setReorderingAllowed(false);
		tbProdutos.getTableHeader().setResizingAllowed(false);
	}

}
