package br.com.mercadocancelier.views.vendas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.ItemVenda;
import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.entity.Venda;
import br.com.mercadocancelier.entity.enums.Status;
import br.com.mercadocancelier.entity.enums.TipoDePagamento;
import br.com.mercadocancelier.entity.enums.UnidadeDeMedida;
import br.com.mercadocancelier.service.ItemVendaService;
import br.com.mercadocancelier.service.ProdutoService;
import br.com.mercadocancelier.service.VendaService;
import br.com.mercadocancelier.util.Conversor;
import br.com.mercadocancelier.views.components.MensagemDeAviso;
import br.com.mercadocancelier.views.components.MensagemDeSucesso;
import br.com.mercadocancelier.views.table.ItensVendaTableModel;

@Component
public class TelaDeVenda extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtCodigo;

	private JTextField txtTotalRecebido;

	private JLabel lblNomeDoProduto;

	private JLabel lblValorUnitario;

	private JLabel lblTotal;

	private JLabel lblTroco;

	private List<ItemVenda> itensVenda = new ArrayList<ItemVenda>();

	private JComboBox<TipoDePagamento> cbxTipoDePagamento;

	@Autowired
	private VendaService vendaService;

	@Autowired
	private ItemVendaService itemVendaService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private MensagemDeAviso mensagemDeAviso;

	@Autowired
	private MensagemDeSucesso mensagemDeSucesso;

	private JTable tbItensVenda;

	public TelaDeVenda() {
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente fechar? nenhum item será salvo.",
						"Atenção! ", JOptionPane.YES_NO_OPTION);

				if (op == 0) {
					itensVenda.clear();
					setLabels(null);
					dispose();
				}
			}
		});
		ItensVendaTableModel model = new ItensVendaTableModel();

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(20, 20));

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(10, 20));

		JLabel lblVenda = new JLabel("Venda");
		lblVenda.setFont(new Font("Dialog", Font.PLAIN, 40));
		lblVenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblVenda.setForeground(new Color(255, 255, 255));
		panel.add(lblVenda, BorderLayout.CENTER);

		JPanel panelInfos = new JPanel();
		contentPane.add(panelInfos, BorderLayout.WEST);
		panelInfos.setLayout(new GridLayout(6, 1, 10, 10));

		JPanel panelCodigo = new JPanel();
		panelCodigo.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "C\u00F3digo", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelInfos.add(panelCodigo);
		panelCodigo.setLayout(new BorderLayout(0, 0));

		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					adicionarItemVenda();
				}
			}
		});
		txtCodigo.setFont(new Font("Dialog", Font.PLAIN, 25));
		panelCodigo.add(txtCodigo, BorderLayout.CENTER);
		txtCodigo.setColumns(20);

		JPanel panelNomeDoProduto = new JPanel();
		panelInfos.add(panelNomeDoProduto);
		panelNomeDoProduto.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Nome do produto",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelNomeDoProduto.setLayout(new BorderLayout(0, 0));

		lblNomeDoProduto = new JLabel("");
		lblNomeDoProduto.setForeground(Color.RED);
		lblNomeDoProduto.setFont(new Font("Dialog", Font.PLAIN, 25));
		panelNomeDoProduto.add(lblNomeDoProduto, BorderLayout.CENTER);

		JPanel panelValorUnitario = new JPanel();
		panelValorUnitario.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Valor Unit\u00E1rio",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelInfos.add(panelValorUnitario);
		panelValorUnitario.setLayout(new BorderLayout(0, 0));

		lblValorUnitario = new JLabel("R$ 0,00");
		lblValorUnitario.setForeground(SystemColor.activeCaption);
		lblValorUnitario.setFont(new Font("Dialog", Font.PLAIN, 25));
		panelValorUnitario.add(lblValorUnitario, BorderLayout.CENTER);

		JPanel panelTotal = new JPanel();
		panelTotal.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Total da venda",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelInfos.add(panelTotal);
		panelTotal.setLayout(new BorderLayout(0, 0));

		lblTotal = new JLabel("R$ 0,00");
		lblTotal.setForeground(SystemColor.desktop);
		lblTotal.setFont(new Font("Dialog", Font.PLAIN, 25));
		panelTotal.add(lblTotal, BorderLayout.CENTER);

		JPanel panelTipoDePagamento = new JPanel();
		panelTipoDePagamento.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Forma de pagamento",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelInfos.add(panelTipoDePagamento);
		panelTipoDePagamento.setLayout(new BorderLayout(0, 0));

		cbxTipoDePagamento = new JComboBox<TipoDePagamento>();
		cbxTipoDePagamento.setModel(new DefaultComboBoxModel<TipoDePagamento>(TipoDePagamento.values()));
		cbxTipoDePagamento.setSelectedIndex(-1);
		panelTipoDePagamento.add(cbxTipoDePagamento, BorderLayout.CENTER);

		JPanel panelAcoes = new JPanel();
		panelAcoes.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "A\u00E7\u00F5es",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 255, 255)));
		panelAcoes.setBackground(SystemColor.activeCaption);
		panelInfos.add(panelAcoes);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (itensVenda.isEmpty()) {
					mensagemDeAviso.abrirTela("Nenhum produto adicionado a venda. ");
				} else {
					salvar();
				}
			}
		});
		btnSalvar.setBackground(Color.WHITE);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removerProduto();
			}
		});
		btnExcluir.setBackground(Color.WHITE);
		GroupLayout gl_panelAcoes = new GroupLayout(panelAcoes);
		gl_panelAcoes.setAutoCreateGaps(true);
		gl_panelAcoes.setAutoCreateContainerGaps(true);
		gl_panelAcoes.setHorizontalGroup(
				gl_panelAcoes.createParallelGroup(Alignment.TRAILING).addGap(0, 407, Short.MAX_VALUE)
						.addGroup(gl_panelAcoes.createSequentialGroup().addContainerGap()
								.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 161, Short.MAX_VALUE)
								.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		gl_panelAcoes.setVerticalGroup(gl_panelAcoes.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 101, Short.MAX_VALUE)
				.addGroup(gl_panelAcoes.createSequentialGroup().addContainerGap(24, Short.MAX_VALUE)
						.addGroup(gl_panelAcoes.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panelAcoes.setLayout(gl_panelAcoes);

		JPanel panelScroll = new JPanel();
		panelScroll.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Itens", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		panelScroll.setBackground(new Color(255, 255, 255));
		contentPane.add(panelScroll, BorderLayout.CENTER);
		panelScroll.setLayout(new BorderLayout(50, 10));

		this.tbItensVenda = new JTable(model);
		tbItensVenda.setForeground(Color.BLACK);
		tbItensVenda.setFont(new Font("Dialog", Font.BOLD, 14));
		tbItensVenda.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbItensVenda.getTableHeader().setOpaque(false);
		tbItensVenda.getTableHeader().setBackground(SystemColor.activeCaption);
		tbItensVenda.getTableHeader().setForeground(Color.WHITE);
		tbItensVenda.getTableHeader().setReorderingAllowed(false);
		tbItensVenda.getTableHeader().setResizingAllowed(false);
		tbItensVenda.getTableHeader().setPreferredSize(new Dimension(tbItensVenda.getTableHeader().getWidth(), 50));
		
		JScrollPane scrollPane = new JScrollPane(tbItensVenda);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbItensVenda.getTableHeader().setPreferredSize(new Dimension(tbItensVenda.getTableHeader().getWidth(), 50));
		tbItensVenda.setRowHeight(40);
		tbItensVenda.setDefaultRenderer(Object.class, centerRenderer);
		panelScroll.add(scrollPane, BorderLayout.CENTER);

		JPanel panelInferior = new JPanel();
		panelInferior.setBackground(Color.WHITE);
		panelScroll.add(panelInferior, BorderLayout.SOUTH);
		panelInferior.setLayout(new GridLayout(0, 2, 10, 0));

		JPanel panelTotalRecebido = new JPanel();
		panelTotalRecebido.setBorder(
				new TitledBorder(null, "Total recebido", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInferior.add(panelTotalRecebido);
		panelTotalRecebido.setLayout(new BorderLayout(0, 0));

		txtTotalRecebido = new JTextField();
		txtTotalRecebido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					exibirTroco();
				}
			}
		});
		txtTotalRecebido.setFont(new Font("Dialog", Font.PLAIN, 25));
		panelTotalRecebido.add(txtTotalRecebido);
		txtTotalRecebido.setColumns(20);

		JPanel panelTroco = new JPanel();
		panelTroco.setBorder(new TitledBorder(null, "Troco", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelInferior.add(panelTroco);
		panelTroco.setLayout(new BorderLayout(0, 0));

		lblTroco = new JLabel("R$ 0,00");
		lblTroco.setFont(new Font("Dialog", Font.PLAIN, 25));
		panelTroco.add(lblTroco, BorderLayout.NORTH);
	}

	private void adicionarItemVenda() {
		if (txtCodigo.getText().length() >= 3) {
			try {
				Produto produto = produtoService.buscarPor(txtCodigo.getText());
				if (produto != null) {
					int quantidade = 1;
					boolean produtoJaNaLista = false;

					if (produto.getUnidadeDeMedida() != UnidadeDeMedida.Un) {
						adicionarItemDiferenteDeUnidade(produto);
						produtoJaNaLista = true;
					}
					
					for (ItemVenda item : itensVenda) {
						if (item.getProduto().equals(produto) && produto.getUnidadeDeMedida() == UnidadeDeMedida.Un) {
							adicionarQuantidadeProdutoExistente(item, produto, quantidade);
							produtoJaNaLista = true;
						}
					}

					if (!produtoJaNaLista) {
						adicionarNovoItem(produto);
					}

					setLabels(itensVenda.get(itensVenda.size() - 1));
					carregarProdutoNaTabela(itensVenda);

				} else {
					mensagemDeAviso.abrirTela("Nenhum produto encontrado para o código: " + txtCodigo.getText());
				}
			} catch (Exception e) {
				System.out.println("Erro ao adicionar item venda, motivo: " + e.getMessage());
			}
		}
	}
	
	private void adicionarNovoItem(Produto produto) {
		ItemVenda itemVenda = new ItemVenda();
		itemVenda.setProduto(produto);
		itemVenda.setQuantidade(BigDecimal.ONE);
		itemVenda.setPrecoTotal(produto.getPreco());
		itensVenda.add(itemVenda);
	}
	
	private void adicionarQuantidadeProdutoExistente(ItemVenda item, Produto produto, int quantidade) {
		item.setQuantidade(BigDecimal.valueOf(item.getQuantidade().doubleValue() + quantidade));
		BigDecimal precoTotal = BigDecimal.valueOf(produto.getPreco().doubleValue() * item.getQuantidade().doubleValue());
		item.setPrecoTotal(precoTotal);
	}
	
	private void adicionarItemDiferenteDeUnidade(Produto produto) {
		Double peso = Conversor.stringParaNumero(JOptionPane.showInputDialog(contentPane, "Digite o peso do " + produto.getNome()));
		ItemVenda itemvenda = new ItemVenda();
		itemvenda.setProduto(produto);
		itemvenda.setPrecoTotal(produto.getPreco().multiply(BigDecimal.valueOf(peso)));
		itemvenda.setQuantidade(BigDecimal.valueOf(peso));
		itensVenda.add(itemvenda);
	}

	private void carregarProdutoNaTabela(List<ItemVenda> itensVenda) {
		ItensVendaTableModel model = new ItensVendaTableModel(itensVenda);
		tbItensVenda.setModel(model);

		if (!itensVenda.isEmpty()) {
			setLabels(itensVenda.get(itensVenda.size() - 1));
		} else {
			setLabels(null);
		}
	}

	private void setLabels(ItemVenda itemVenda) {
		if (itemVenda != null) {
			lblNomeDoProduto.setText(itemVenda.getProduto().getNome());
			lblValorUnitario
					.setText("R$ " + Conversor.numeroParaString(itemVenda.getProduto().getPreco().doubleValue()));
			lblTotal.setText("R$ " + Conversor.numeroParaString(calcularValorTotal().doubleValue()));
		} else {
			lblNomeDoProduto.setText("");
			lblValorUnitario.setText("R$ 0,00");
			lblTotal.setText("R$ " + Conversor.numeroParaString(calcularValorTotal().doubleValue()));
			lblTroco.setText("R$ 0,00");
			txtCodigo.setText("");
			txtTotalRecebido.setText("");
		}
	}

	private BigDecimal calcularValorTotal() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (ItemVenda itemVenda : itensVenda) {
			BigDecimal preco = new BigDecimal(itemVenda.getProduto().getPreco().toString());
			BigDecimal quantidade = itemVenda.getQuantidade();
			valorTotal = valorTotal.add(preco.multiply(quantidade));
		}
		return valorTotal;
	}

	private void exibirTroco() {
		lblTroco.setText("R$ " + Conversor.numeroParaString(calcularTroco().doubleValue()));
	}

	private BigDecimal calcularTroco() {
		Double total = Conversor.stringParaNumero(txtTotalRecebido.getText());
		BigDecimal totalRecebido = new BigDecimal(total);
		BigDecimal totalDaVenda = calcularValorTotal();
		return totalRecebido.subtract(totalDaVenda);
	}

	private void removerProduto() {
		int linhaSelecionada = tbItensVenda.getSelectedRow();
		if (linhaSelecionada < 0) {
			mensagemDeAviso.abrirTela("Escolha uma linha para continuar. ");
			return;
		}

		int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente remover o item?", "Atenção! ",
				JOptionPane.YES_NO_OPTION);

		if (op == JOptionPane.YES_OPTION) {
			try {
				ItemVenda itemSelecionado = itensVenda.get(linhaSelecionada);
				processarRemocaoItem(itemSelecionado);
			} catch (Exception ex) {
				System.out.println("Ocorreu um erro ao remover produto, motivo: " + ex.getMessage());
			}
		}
	}

	private void processarRemocaoItem(ItemVenda itemSelecionado) {
		if (itemSelecionado.getQuantidade().doubleValue() > 1) {
			processarRemocaoItemQuantidadeMaiorQueUm(itemSelecionado);
		} else {
			itensVenda.remove(tbItensVenda.getSelectedRow());
			mensagemDeAviso.abrirTela("O Item '" + itemSelecionado.getProduto().getNome() + "' foi removido.");
		}
		tbItensVenda.clearSelection();
		carregarProdutoNaTabela(itensVenda);
	}

	private void processarRemocaoItemQuantidadeMaiorQueUm(ItemVenda itemSelecionado) {
		double qtdeParaRemover = Double.parseDouble(JOptionPane.showInputDialog(contentPane,
				"O item encontrado tem mais de uma quantidade! Deseja remover quantas?"));

		if (qtdeParaRemover == itemSelecionado.getQuantidade().doubleValue()) {
			itensVenda.remove(tbItensVenda.getSelectedRow());
			mensagemDeAviso.abrirTela("O Item '" + itemSelecionado.getProduto().getNome() + "' foi removido.");

		} else if (qtdeParaRemover > itemSelecionado.getQuantidade().doubleValue()) {
			mensagemDeAviso.abrirTela("A quantidade de itens para remoção é maior do que a quantidade de itens do produto. ");

		} else if (qtdeParaRemover >= 0.1 ) {
			itemSelecionado.setQuantidade(BigDecimal.valueOf(itemSelecionado.getQuantidade().doubleValue() - qtdeParaRemover));
			BigDecimal novoPreco = calcularNovoPreco(itemSelecionado);
			itemSelecionado.setPrecoTotal(novoPreco);
			exibirMensagemRemocao(itemSelecionado, qtdeParaRemover);

		}
	}

	private BigDecimal calcularNovoPreco(ItemVenda itemSelecionado) {
		return itemSelecionado.getProduto().getPreco().multiply(itemSelecionado.getQuantidade());
	}

	private void exibirMensagemRemocao(ItemVenda itemSelecionado, double qtdeParaRemover) {
		String mensagem = qtdeParaRemover + " unidade(s) do produto: " + itemSelecionado.getProduto().getNome()
				+ " foram removidas";
		mensagemDeAviso.abrirTela(mensagem);
		tbItensVenda.updateUI();
	}

	private void salvar() {
		if (cbxTipoDePagamento.getSelectedIndex() == -1) {
			mensagemDeAviso.abrirTela("A forma de pagamento é obrigatória");
		} else {
			Venda venda = new Venda();
			venda.setDataDeVenda(LocalDateTime.now());
			venda.setStatus(Status.A);
			venda.setValorTotal(calcularValorTotal());
			String tipoDePagamento = cbxTipoDePagamento.getSelectedItem().toString();
			venda.setTipoDePagamento(TipoDePagamento.valueOf(tipoDePagamento));
			Venda vendaSalva = vendaService.salvar(venda);

			for (ItemVenda itemVenda : itensVenda) {
				itemVenda.setVenda(vendaSalva);
				BigDecimal quantidadeVendida = itemVenda.getQuantidade();
				BigDecimal estoque = itemVenda.getProduto().getEstoque();
				Produto produtoEncontrado = itemVenda.getProduto();
				produtoService.atualizarEstoquePor(produtoEncontrado.getId(), estoque.subtract(quantidadeVendida));
				itemVendaService.salvar(itemVenda);
			}
			itensVenda.removeAll(itensVenda);
			carregarProdutoNaTabela(itensVenda);
			mensagemDeSucesso.abrirTela("Venda salva.");
		}
	}
}
