package br.com.mercadocancelier.views.produtos;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.service.ProdutoService;
import br.com.mercadocancelier.views.table.ProdutosTableModel;
import jakarta.annotation.PostConstruct;

@Component
public class TelaConsultaDeProdutos extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtNomeDoProduto;

	private JTable tbProdutos;

	@Autowired
	@Lazy
	private TelaCadastroDeProdutos telaCadastroDeProdutos;

	@Autowired
	private ProdutoService produtoService;

	public void abrirTela() {
		this.inicializarComponentes();
		this.configurarTabela();
		this.listarProdutos();
		this.setVisible(true);
	}

	public TelaConsultaDeProdutos() {
	}

	@PostConstruct
	private void inicializarComponentes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1366, 768);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panelBusca = new JPanel();

		txtNomeDoProduto = new JTextField();
		txtNomeDoProduto.setColumns(11);

		JLabel lblNomeDoProduto = new JLabel("Nome do produto");
		lblNomeDoProduto.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JButton btnListar = new JButton("Listar");
		btnListar.setBackground(SystemColor.text);
		btnListar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String filtro = txtNomeDoProduto.getText();
				if (filtro.isBlank()) {
					listarProdutos();
				} else {
					listarProdutosPor(filtro);
				}
			}
		});

		GroupLayout gl_panelBusca = new GroupLayout(panelBusca);
		gl_panelBusca.setHorizontalGroup(gl_panelBusca.createParallelGroup(Alignment.LEADING).addGroup(gl_panelBusca
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelBusca.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_panelBusca.createSequentialGroup()
								.addComponent(txtNomeDoProduto, GroupLayout.PREFERRED_SIZE, 1080,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
								.addComponent(btnListar, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelBusca.createSequentialGroup().addComponent(lblNomeDoProduto)
								.addContainerGap(1192, Short.MAX_VALUE)))));
		gl_panelBusca.setVerticalGroup(gl_panelBusca.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBusca.createSequentialGroup().addGap(22).addComponent(lblNomeDoProduto)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelBusca.createParallelGroup(Alignment.BASELINE)
								.addComponent(txtNomeDoProduto, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
								.addComponent(btnListar, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panelBusca.setLayout(gl_panelBusca);

		tbProdutos = new JTable();
		JScrollPane scrollPane = new JScrollPane(tbProdutos);
		tbProdutos.getTableHeader().setPreferredSize(new Dimension(tbProdutos.getTableHeader().getWidth(), 50));
		tbProdutos.setRowHeight(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbProdutos.setDefaultRenderer(Object.class, centerRenderer);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				telaCadastroDeProdutos.abrirTela();
			}
		});
		btnCadastrar.setBackground(SystemColor.text);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(SystemColor.text);
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

		JButton btnExcluir = new JButton("Excluir");
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
		btnExcluir.setBackground(SystemColor.text);

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
								.addPreferredGap(ComponentPlacement.RELATED, 906, Short.MAX_VALUE).addComponent(
										btnCadastrar, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE))
						.addComponent(panelBusca, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1330, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
								.addGap(4))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1320, Short.MAX_VALUE)))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(40)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(btnCadastrar,
								GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelBusca, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEditar, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		setLocationRelativeTo(null);
		scrollPane.setViewportView(tbProdutos);
		contentPane.setLayout(gl_contentPane);
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