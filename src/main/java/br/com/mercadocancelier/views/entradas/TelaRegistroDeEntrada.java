
package br.com.mercadocancelier.views.entradas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.AbstractDocument;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.toedter.calendar.JDateChooser;

import br.com.mercadocancelier.entity.Entrada;
import br.com.mercadocancelier.entity.Fornecedor;
import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.entity.enums.UnidadeDeMedida;
import br.com.mercadocancelier.service.EntradaService;
import br.com.mercadocancelier.service.FornecedorService;
import br.com.mercadocancelier.service.ProdutoService;
import br.com.mercadocancelier.util.DecimalFilter;
import br.com.mercadocancelier.views.table.EntradaTableModel;
import jakarta.annotation.PostConstruct;

@Component
public class TelaRegistroDeEntrada extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtQtde;
	private JTextField txtPreco;
	private JComboBox<Fornecedor> cbFornecedores;
	private JComboBox<String> cbProdutos;
	private JComboBox<UnidadeDeMedida> cbUnidadeMedida;
	private JTable tableEntrada;
	private JLabel lblTotal;
	private BigDecimal totalGeral = BigDecimal.ZERO;
	private boolean modoEdicao = false;
	private JDateChooser txtDataEntrada;
	private UnidadeDeMedida unidadeMedidaProdutoSelecionado;

	@Autowired
	private FornecedorService fornecedorService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private EntradaService service;

	@PostConstruct
	public void carregarComboFornecedor() {
		cbFornecedores.removeAllItems();
		List<Fornecedor> fornecedores = fornecedorService.listarTodos();
		Fornecedor selecione = new Fornecedor();
		selecione.setNome("Selecione...");
		cbFornecedores.addItem(selecione);
		for (Fornecedor fornecedor : fornecedores) {
			cbFornecedores.addItem(fornecedor);
		}

		if (cbFornecedores.getItemCount() == 1) {
			cbFornecedores.addItem(new Fornecedor());
		}
	}

	@PostConstruct
	public void carregarComboProduto() {
		cbProdutos.removeAllItems();
		List<String> produtos = produtoService.listarTodosProdutosPorNome();
		cbProdutos.addItem("Selecione...");
		for (String produto : produtos) {
			cbProdutos.addItem(produto);
		}
	}

	public TelaRegistroDeEntrada() {
		EntradaTableModel model = new EntradaTableModel();
		this.tableEntrada = new JTable(model);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JButton btnRelatorio = new JButton("Relatório");
		btnRelatorio.setBackground(new Color(255, 255, 255));
		btnRelatorio.setBounds(1100, 12, 211, 62);
		contentPane.add(btnRelatorio);

		cbProdutos = new JComboBox<String>();
		cbProdutos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String produtoSelecionado = (String) cbProdutos.getSelectedItem();

				if (!produtoSelecionado.equals("Selecione...")) {
					Produto produto = produtoService.buscarPorNome(produtoSelecionado);

					if (produto != null) {
						unidadeMedidaProdutoSelecionado = produto.getUnidadeDeMedida();
						cbUnidadeMedida.setSelectedItem(unidadeMedidaProdutoSelecionado);
					}
				}
			}
		});

		cbProdutos.setBounds(78, 122, 462, 43);
		contentPane.add(cbProdutos);

		cbFornecedores = new JComboBox<Fornecedor>();
		cbFornecedores.setBounds(576, 122, 442, 43);
		contentPane.add(cbFornecedores);

		UnidadeDeMedida[] valoresOpcoes = UnidadeDeMedida.values();
		cbUnidadeMedida = new JComboBox<>(valoresOpcoes);
		cbUnidadeMedida.setSelectedIndex(0);
		cbUnidadeMedida.insertItemAt(null, 0);
		cbUnidadeMedida.setSelectedIndex(0);
		cbUnidadeMedida.setBounds(78, 203, 231, 42);
		contentPane.add(cbUnidadeMedida);

		txtQtde = new JTextField();
		((AbstractDocument) txtQtde.getDocument()).setDocumentFilter(new DecimalFilter());
		txtQtde.setColumns(10);
		txtQtde.setBounds(391, 204, 211, 42);
		contentPane.add(txtQtde);

		txtPreco = new JTextField();
		((AbstractDocument) txtPreco.getDocument()).setDocumentFilter(new DecimalFilter());
		txtPreco.setColumns(10);
		txtPreco.setBounds(674, 204, 298, 41);
		contentPane.add(txtPreco);
		JButton btnInserir = new JButton("Inserir");
		btnInserir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					if (!validarCampos()) {
						return;
					}

					String produto = (String) cbProdutos.getSelectedItem();
					Produto produtoEncontrado = produtoService.buscarPorNome(produto);
					Fornecedor fornecedor = (Fornecedor) cbFornecedores.getSelectedItem();
					UnidadeDeMedida unidadeMedida = (UnidadeDeMedida) cbUnidadeMedida.getSelectedItem();
					double quantidade = Double.parseDouble(txtQtde.getText());
					BigDecimal preco = new BigDecimal(txtPreco.getText());

					if (modoEdicao == false) {
						adicionarEntrada(produtoEncontrado, fornecedor, unidadeMedida, quantidade, preco);
					} else {
						atualizarEntrada(produtoEncontrado, fornecedor, unidadeMedida, quantidade, preco);
					}

					totalGeral = model.calcularTotal();
					atualizarTotal(totalGeral);
					limparCampos(false);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}

		});
		btnInserir.setBackground(Color.WHITE);
		btnInserir.setBounds(1029, 193, 197, 62);
		contentPane.add(btnInserir);

		JLabel lblNomeProduto = new JLabel("Nome do Produto");
		lblNomeProduto.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblNomeProduto.setBounds(78, 96, 211, 24);
		contentPane.add(lblNomeProduto);

		JLabel lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblFornecedor.setBounds(578, 96, 132, 24);
		contentPane.add(lblFornecedor);

		JLabel lblUnidMedida = new JLabel("Unid. Medida");
		lblUnidMedida.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblUnidMedida.setBounds(78, 177, 211, 24);
		contentPane.add(lblUnidMedida);

		JLabel lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblQuantidade.setBounds(391, 186, 211, 17);
		contentPane.add(lblQuantidade);

		JLabel lblPreo = new JLabel("Preço");
		lblPreo.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblPreo.setBounds(674, 184, 211, 17);
		contentPane.add(lblPreo);

		JLabel lblDataDeEntrada = new JLabel("Data de Entrada");
		lblDataDeEntrada.setFont(new Font("Dialog", Font.PLAIN, 20));
		lblDataDeEntrada.setBounds(1060, 96, 211, 24);
		contentPane.add(lblDataDeEntrada);

		JButton btnLimparLinha = new JButton("Limpar Linha");
		btnLimparLinha.setBackground(Color.WHITE);
		btnLimparLinha.setBounds(274, 643, 197, 62);
		btnLimparLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableEntrada.getSelectedRow();
				if (selectedRow != -1) {
					int confirmacao = JOptionPane.showConfirmDialog(contentPane,
							"Tem certeza que deseja excluir esta linha?", "Confirmação", JOptionPane.YES_NO_OPTION);
					if (confirmacao == JOptionPane.YES_OPTION) {
						EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
						model.removerEntrada(selectedRow);
						totalGeral = model.calcularTotal();
						lblTotal.setText("Total: R$" + totalGeral);
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para excluir.");
				}
			}
		});
		contentPane.add(btnLimparLinha);

		JButton btnEditarLinha = new JButton("Editar Linha");
		btnEditarLinha.setBackground(Color.WHITE);
		btnEditarLinha.setBounds(483, 643, 180, 62);
		btnEditarLinha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = tableEntrada.getSelectedRow();
				if (selectedRow != -1) {
					int confirmacao = JOptionPane.showConfirmDialog(contentPane,
							"Tem certeza que deseja editar esta linha?", "Confirmação", JOptionPane.YES_NO_OPTION);
					if (confirmacao == JOptionPane.YES_OPTION) {
						EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
						Entrada entradaEditada = model.getPor(selectedRow);
						recarregarCamposPor(entradaEditada);
						modoEdicao = true;
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para editar.");
				}
			}
		});
		contentPane.add(btnEditarLinha);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(Color.WHITE);
		btnCancelar.setBounds(677, 643, 180, 62);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
				if (model.getRowCount() == 0) {
					JOptionPane.showMessageDialog(contentPane, "Não há entradas para cancelar.");
					return;
				}

				int confirmacao = JOptionPane.showConfirmDialog(contentPane,
						"Tem certeza que deseja cancelar todas as inserções?", "Confirmação",
						JOptionPane.YES_NO_OPTION);
				if (confirmacao == JOptionPane.YES_OPTION) {
					limparTudo();
				}
			}
		});
		contentPane.add(btnCancelar);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.setBounds(870, 643, 180, 62);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
				if (model.getRowCount() == 0) {
					JOptionPane.showMessageDialog(contentPane, "Não há entradas para salvar.");
					return;
				}

				int confirmacao = JOptionPane.showConfirmDialog(contentPane,
						"Tem certeza que deseja salvar as entradas?", "Confirmação", JOptionPane.YES_NO_OPTION);
				if (confirmacao == JOptionPane.YES_OPTION) {
					salvarEntradas();
					JOptionPane.showMessageDialog(contentPane, "Entradas cadastradas com sucesso!");
				}
			}
		});
		contentPane.add(btnSalvar);

		lblTotal = new JLabel("Total: R$" + totalGeral);
		lblTotal.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTotal.setBounds(1085, 649, 226, 46);
		contentPane.add(lblTotal);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		JScrollPane scrollPane = new JScrollPane(tableEntrada);
		scrollPane.setBounds(60, 275, 1265, 346);
		tableEntrada.setDefaultRenderer(Object.class, centerRenderer);
		tableEntrada.setRowHeight(40);
		tableEntrada.getTableHeader().setPreferredSize(new Dimension(tableEntrada.getTableHeader().getWidth(), 50));
		tableEntrada.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableEntrada.getTableHeader().setReorderingAllowed(false);
		tableEntrada.getTableHeader().setResizingAllowed(false);

		contentPane.add(scrollPane);

		txtDataEntrada = new JDateChooser();
		txtDataEntrada.setDateFormatString("Selecione uma data");
		txtDataEntrada.setToolTipText("Selecione a data");
		txtDataEntrada.setBounds(1040, 122, 211, 43);
		contentPane.add(txtDataEntrada);
		txtDataEntrada.setFocusTraversalPolicy(
				new FocusTraversalOnArray(new java.awt.Component[] { txtDataEntrada.getCalendarButton() }));
	}

	// VALIDAÇÕES

	private boolean validarCampos() {
		return validarProduto() && validarFornecedor() && validarData()
				&& validarQuantidadeEPreco() && validarUnidadeDeMedida();
	}

	private boolean validarQuantidadeEPreco() {
		UnidadeDeMedida unidadeMedida = (UnidadeDeMedida) cbUnidadeMedida.getSelectedItem();
		double quantidade;
		BigDecimal preco;
		try {
			quantidade = Double.parseDouble(txtQtde.getText());
			preco = new BigDecimal(txtPreco.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(contentPane, "Digite valores numéricos válidos para quantidade e preço.");
			return false;
		}

		if (quantidade <= 0 || preco.compareTo(BigDecimal.ZERO) <= 0) {
			JOptionPane.showMessageDialog(contentPane,
					"Digite valores numéricos maior que zero para quantidade e preço.");
			return false;
		}
		
		if (txtQtde.getText().contains(".") && !(unidadeMedida == UnidadeDeMedida.Kg)) {
			JOptionPane.showMessageDialog(contentPane,
					"Apenas a unid. medida KG aceita valor com vírgula.");
			return false;
		}
		return true;
	}

	private boolean validarFornecedor() {
		if (!(cbFornecedores.getSelectedItem() instanceof Fornecedor)
				|| "Selecione...".equals(((Fornecedor) cbFornecedores.getSelectedItem()).getNome())) {
			JOptionPane.showMessageDialog(contentPane, "Selecione um fornecedor válido.");
			return false;
		}
		return true;
	}

	public LocalDate extrairData(JDateChooser campoData) {
		String textoData = new SimpleDateFormat("dd/MM/yyyy").format(campoData.getDate());

		try {
			return LocalDate.parse(textoData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		} catch (DateTimeParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean validarData() {
		if (txtDataEntrada.getDate() == null) {
			JOptionPane.showMessageDialog(contentPane, "Preencha a data.");
			return false;
		}

		LocalDate dataEntrada = extrairData(txtDataEntrada);
		if (dataEntrada != null && dataEntrada.isAfter(LocalDate.now())) {
			JOptionPane.showMessageDialog(contentPane, "A data de entrada não pode ser no futuro.");
			return false;
		}
		return true;
	}

	private boolean validarProduto() {
		String produto = (String) cbProdutos.getSelectedItem();

		if ("Selecione...".equals(produto)) {
			JOptionPane.showMessageDialog(contentPane, "Selecione um produto válido.");
			return false;
		}
		return true;
	}

	private boolean validarUnidadeDeMedida() {
		UnidadeDeMedida unidadeMedida = (UnidadeDeMedida) cbUnidadeMedida.getSelectedItem();

		if (unidadeMedidaProdutoSelecionado != null && !unidadeMedidaProdutoSelecionado.equals(unidadeMedida)) {
			JOptionPane.showMessageDialog(contentPane,
					"A unidade de medida selecionada não é compatível com o produto. \n"
							+ "Unidade de Medida esperada: "
							+ unidadeMedidaProdutoSelecionado.toString().toUpperCase());
			return false;
		}
		return true;
	}

	// LIMPAR CAMPOS

	private void limparTudo() {
		EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
		model.limparEntradas();
		limparCampos(true);
		totalGeral = BigDecimal.ZERO;
		atualizarTotal(totalGeral);
	}

	private void limparCampos(boolean isLimparCampoDeData) {

		cbProdutos.setSelectedIndex(0);
		cbFornecedores.setSelectedIndex(0);
		cbUnidadeMedida.setSelectedIndex(0);
		txtQtde.setText(null);
		txtPreco.setText(null);
		modoEdicao = false;

		if (isLimparCampoDeData == true) {
			txtDataEntrada.setDate(null);
		}

	}

	// AÇÕES DOS BOTÕES

	private void adicionarEntrada(Produto produto, Fornecedor fornecedor, UnidadeDeMedida unidadeMedida,
			double quantidade, BigDecimal preco) {
		LocalDate dataEntrada = extrairData(txtDataEntrada);
		Entrada entrada = new Entrada(produto, fornecedor, dataEntrada, unidadeMedida, quantidade, preco);
		EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
		model.adicionarEntrada(entrada);
		limparCampos(false);
	}

	private void atualizarEntrada(Produto produto, Fornecedor fornecedor, UnidadeDeMedida unidadeMedida,
			double quantidade, BigDecimal preco) {
		EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
		Entrada entradaEdicao = model.getPor(tableEntrada.getSelectedRow());
		entradaEdicao.setFornecedor(fornecedor);
		entradaEdicao.setProduto(produto);
		entradaEdicao.setPreco(preco);
		entradaEdicao.setQuantidade(quantidade);
		entradaEdicao.setDataDeEntrada(extrairData(txtDataEntrada));
		entradaEdicao.setUnidadeDeMedida(unidadeMedida);
		model.atualizarEntrada(entradaEdicao);
	}

	private void salvarEntradas() {
		EntradaTableModel model = (EntradaTableModel) tableEntrada.getModel();
		List<Entrada> entradas = model.getEntradas();

		for (Entrada entrada : entradas) {
			Entrada entradaNova = new Entrada();
			entradaNova.setFornecedor(entrada.getFornecedor());
			entradaNova.setProduto(entrada.getProduto());
			entradaNova.setPreco(entrada.getPreco());
			entradaNova.setQuantidade(entrada.getQuantidade());
			entradaNova.setDataDeEntrada(entrada.getDataDeEntrada());
			entradaNova.setUnidadeDeMedida(entrada.getUnidadeDeMedida());
			entradaNova.setTotal(entrada.getTotal());
			service.salvar(entradaNova);
		}

		limparTudo();
	}

	private void recarregarCamposPor(Entrada entrada) {
		cbProdutos.setSelectedItem(entrada.getProduto().getNome());
		cbFornecedores.setSelectedItem(entrada.getFornecedor());
		cbUnidadeMedida.setSelectedItem(entrada.getUnidadeDeMedida());
		txtQtde.setText(String.valueOf(entrada.getQuantidade()));
		txtPreco.setText(String.valueOf(entrada.getPreco()));
		Date dataConvertida = java.sql.Date.valueOf(entrada.getDataDeEntrada());
		txtDataEntrada.setDate(dataConvertida);
	}

	private void atualizarTotal(BigDecimal total) {
		lblTotal.setText("Total: R$" + total);
	}

}
