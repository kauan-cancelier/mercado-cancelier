package br.com.mercadocancelier.views.vendas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
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

	private JTable tbItensVenda;

	private JLabel labelNomeDoProduto;

	private JLabel labelValorUnitario;

	private JLabel labelTotalDoItem;

	private JLabel labelTroco;

	private JLabel labelSubtotal;

	private List<ItemVenda> itensVenda = new ArrayList<ItemVenda>();

	private JComboBox<TipoDePagamento> cbxFormaDePagamento;

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

	public TelaDeVenda() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setTitle("Mercado Cancelier - venda");

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int op = JOptionPane.showConfirmDialog(contentPane, "Deseja realmente fechar? nenhum item será salvo.",
						"Atenção! ", JOptionPane.YES_NO_OPTION);

				if (op == 0) {
					dispose();
				}
			}
		});

		ItensVendaTableModel model = new ItensVendaTableModel();
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

		setBounds(0, 0, 1366, 768);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));

		setContentPane(contentPane);

		JPanel panelVenda = new JPanel();
		panelVenda.setBackground(SystemColor.activeCaption);
		panelVenda.setBorder(new LineBorder(new Color(0, 0, 0)));

		JPanel panelCodigo = new JPanel();
		panelCodigo.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "C\u00F3digo", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(51, 51, 51)));

		JPanel panelNomeDoProduto = new JPanel();
		panelNomeDoProduto.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Nome do produto",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		labelNomeDoProduto = new JLabel("");
		labelNomeDoProduto.setForeground(SystemColor.activeCaption);
		labelNomeDoProduto.setFont(new Font("Dialog", Font.BOLD, 20));
		GroupLayout gl_panelNomeDoProduto = new GroupLayout(panelNomeDoProduto);
		gl_panelNomeDoProduto
				.setHorizontalGroup(gl_panelNomeDoProduto.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelNomeDoProduto.createSequentialGroup().addContainerGap()
								.addComponent(labelNomeDoProduto, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panelNomeDoProduto.setVerticalGroup(
				gl_panelNomeDoProduto.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_panelNomeDoProduto.createSequentialGroup().addContainerGap()
								.addComponent(labelNomeDoProduto, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
								.addContainerGap()));
		panelNomeDoProduto.setLayout(gl_panelNomeDoProduto);

		JPanel panelValorCodigo = new JPanel();
		panelValorCodigo.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Valor unit\u00E1rio",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		labelValorUnitario = new JLabel("");
		labelValorUnitario.setForeground(SystemColor.desktop);
		labelValorUnitario.setFont(new Font("Dialog", Font.BOLD, 20));
		GroupLayout gl_panelValorCodigo = new GroupLayout(panelValorCodigo);
		gl_panelValorCodigo.setHorizontalGroup(gl_panelValorCodigo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelValorCodigo.createSequentialGroup().addContainerGap()
						.addComponent(labelValorUnitario, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panelValorCodigo.setVerticalGroup(
				gl_panelValorCodigo.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_panelValorCodigo.createSequentialGroup().addContainerGap()
								.addComponent(labelValorUnitario, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
								.addContainerGap()));
		panelValorCodigo.setLayout(gl_panelValorCodigo);

		JPanel panelTotalDoItem = new JPanel();
		panelTotalDoItem.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Total do item",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));

		labelTotalDoItem = new JLabel("");
		labelTotalDoItem.setForeground(Color.RED);
		labelTotalDoItem.setFont(new Font("Dialog", Font.BOLD, 20));
		GroupLayout gl_panelTotalDoItem = new GroupLayout(panelTotalDoItem);
		gl_panelTotalDoItem.setHorizontalGroup(gl_panelTotalDoItem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTotalDoItem.createSequentialGroup().addContainerGap()
						.addComponent(labelTotalDoItem, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
						.addContainerGap()));
		gl_panelTotalDoItem.setVerticalGroup(
				gl_panelTotalDoItem.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_panelTotalDoItem.createSequentialGroup().addContainerGap()
								.addComponent(labelTotalDoItem, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
								.addContainerGap()));
		panelTotalDoItem.setLayout(gl_panelTotalDoItem);

		JPanel panelMeioDePagamento = new JPanel();
		panelMeioDePagamento.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Forma de pagamento",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		cbxFormaDePagamento = new JComboBox<TipoDePagamento>();
		cbxFormaDePagamento.setModel(new DefaultComboBoxModel<TipoDePagamento>(TipoDePagamento.values()));
		cbxFormaDePagamento.setSelectedIndex(-1);

		GroupLayout gl_panelMeioDePagamento = new GroupLayout(panelMeioDePagamento);
		gl_panelMeioDePagamento.setHorizontalGroup(gl_panelMeioDePagamento.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMeioDePagamento.createSequentialGroup().addContainerGap()
						.addComponent(cbxFormaDePagamento, 0, 373, Short.MAX_VALUE).addContainerGap()));
		gl_panelMeioDePagamento.setVerticalGroup(gl_panelMeioDePagamento.createParallelGroup(Alignment.LEADING)
				.addComponent(cbxFormaDePagamento, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE));
		panelMeioDePagamento.setLayout(gl_panelMeioDePagamento);

		JPanel panelSubTotal = new JPanel();
		panelSubTotal.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Subtotal", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		JPanel panelTroco = new JPanel();
		panelTroco.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Troco", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(51, 51, 51)));

		JPanel panelTotalRecebido = new JPanel();
		panelTotalRecebido.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Total recebido",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));

		txtTotalRecebido = new JTextField();
		txtTotalRecebido.setColumns(10);
		txtTotalRecebido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					exibirTroco();
				}
			}
		});

		GroupLayout gl_panelTotalRecebido = new GroupLayout(panelTotalRecebido);
		gl_panelTotalRecebido.setHorizontalGroup(gl_panelTotalRecebido.createParallelGroup(Alignment.LEADING)
				.addComponent(txtTotalRecebido, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE));
		gl_panelTotalRecebido.setVerticalGroup(gl_panelTotalRecebido.createParallelGroup(Alignment.LEADING)
				.addComponent(txtTotalRecebido, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE));
		panelTotalRecebido.setLayout(gl_panelTotalRecebido);

		JScrollPane scrollPane = new JScrollPane(tbItensVenda);
		tbItensVenda.getTableHeader().setPreferredSize(new Dimension(tbItensVenda.getTableHeader().getWidth(), 50));
		tbItensVenda.setRowHeight(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tbItensVenda.setDefaultRenderer(Object.class, centerRenderer);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "A\u00E7\u00F5es", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(255, 255, 255)));

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelVenda, GroupLayout.DEFAULT_SIZE, 1332, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
								.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panelCodigo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelNomeDoProduto, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelValorCodigo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelTotalDoItem, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(panelMeioDePagamento, GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(panelSubTotal, 0, 0, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(panelTotalRecebido, GroupLayout.PREFERRED_SIZE, 441,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18).addComponent(panelTroco, GroupLayout.PREFERRED_SIZE, 441,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(scrollPane))))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addComponent(panelVenda, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
						.createSequentialGroup().addGap(12)
						.addComponent(panelCodigo, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panelNomeDoProduto, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelValorCodigo, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panelTotalDoItem, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(18)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 417, GroupLayout.PREFERRED_SIZE)))
				.addGap(31)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(panelSubTotal, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.addComponent(panelMeioDePagamento, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 82, Short.MAX_VALUE)
						.addComponent(panelTotalRecebido, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
						.addComponent(panelTroco, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE))
				.addContainerGap()));
		panelVenda.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("Venda");
		lblNewLabel.setBackground(Color.BLUE);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 30));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panelVenda.add(lblNewLabel, BorderLayout.CENTER);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBackground(Color.WHITE);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (itensVenda.isEmpty()) {
					mensagemDeAviso.abrirTela("Nenhum produto adicionado a venda. ");
				} else {
					salvar();
				}
			}
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBackground(Color.WHITE);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				removerProduto();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup().addContainerGap()
						.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 151, Short.MAX_VALUE)
						.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
				gl_panel.createSequentialGroup().addContainerGap(24, Short.MAX_VALUE)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnExcluir, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		panel.setLayout(gl_panel);

		labelTroco = new JLabel("");
		labelTroco.setForeground(new Color(0, 128, 0));
		labelTroco.setFont(new Font("Dialog", Font.BOLD, 20));
		GroupLayout gl_panelTroco = new GroupLayout(panelTroco);
		gl_panelTroco.setHorizontalGroup(gl_panelTroco.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelTroco.createSequentialGroup().addContainerGap()
						.addComponent(labelTroco, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE).addContainerGap()));
		gl_panelTroco.setVerticalGroup(
				gl_panelTroco.createParallelGroup(Alignment.LEADING).addGroup(gl_panelTroco.createSequentialGroup()
						.addComponent(labelTroco, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE).addContainerGap()));
		panelTroco.setLayout(gl_panelTroco);

		labelSubtotal = new JLabel("");
		labelSubtotal.setForeground(SystemColor.activeCaption);
		labelSubtotal.setFont(new Font("Dialog", Font.BOLD, 20));

		GroupLayout gl_panelSubTotal = new GroupLayout(panelSubTotal);
		gl_panelSubTotal
				.setHorizontalGroup(gl_panelSubTotal.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING,
						gl_panelSubTotal.createSequentialGroup().addContainerGap()
								.addComponent(labelSubtotal, GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
								.addContainerGap()));
		gl_panelSubTotal.setVerticalGroup(gl_panelSubTotal.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelSubTotal.createSequentialGroup().addContainerGap()
						.addComponent(labelSubtotal, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)));
		panelSubTotal.setLayout(gl_panelSubTotal);

		txtCodigo = new JTextField();
		txtCodigo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					adicionarItemVenda();
				}
			}
		});
		txtCodigo.setColumns(10);

		JButton btnPesquisar = new JButton("Pesquisar");
		GroupLayout gl_panelCodigo = new GroupLayout(panelCodigo);
		gl_panelCodigo
				.setHorizontalGroup(gl_panelCodigo.createParallelGroup(Alignment.TRAILING).addGroup(Alignment.LEADING,
						gl_panelCodigo.createSequentialGroup().addContainerGap()
								.addComponent(txtCodigo, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panelCodigo.setVerticalGroup(gl_panelCodigo.createParallelGroup(Alignment.LEADING).addGroup(gl_panelCodigo
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panelCodigo.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPesquisar, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
						.addComponent(txtCodigo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
				.addGap(16)));
		panelCodigo.setLayout(gl_panelCodigo);
		contentPane.setLayout(gl_contentPane);
	}

	private void adicionarItemVenda() {
		if (txtCodigo.getText().length() >= 3) {
			try {
				Produto produto = produtoService.buscarPor(txtCodigo.getText());

				if (produto == null) {
					mensagemDeAviso.abrirTela("Nenhum produto encontrado para o código: " + txtCodigo.getText());
				} else {
					int quantidade = 1;

					boolean produtoJaNaLista = false;
					
					if (produto.getUnidadeDeMedida() != UnidadeDeMedida.Un) {
						Double peso = Conversor.stringParaNumero(JOptionPane.showInputDialog(contentPane, "Digite o peso do " + produto.getNome()));
						ItemVenda itemvenda = new ItemVenda();
						itemvenda.setProduto(produto);
						itemvenda.setPrecoTotal(produto.getPreco().multiply(BigDecimal.valueOf(peso)));
						itemvenda.setQuantidade(BigDecimal.valueOf(peso));
						itensVenda.add(itemvenda);
						produtoJaNaLista = true;
					}
					for (ItemVenda item : itensVenda) {
						if (item.getProduto().equals(produto) && produto.getUnidadeDeMedida() == UnidadeDeMedida.Un) {
							item.setQuantidade(BigDecimal.valueOf(item.getQuantidade().doubleValue() + quantidade));
							BigDecimal precoTotal = BigDecimal.valueOf(produto.getPreco().doubleValue() * item.getQuantidade().doubleValue());
							item.setPrecoTotal(precoTotal);
							produtoJaNaLista = true;
						}
					}
					

					if (!produtoJaNaLista) {
						ItemVenda itemVenda = new ItemVenda();
						itemVenda.setProduto(produto);
						itemVenda.setQuantidade(BigDecimal.valueOf(quantidade));
						BigDecimal precoTotal = BigDecimal.valueOf(produto.getPreco().doubleValue() * quantidade);
						itemVenda.setPrecoTotal(precoTotal);
						itensVenda.add(itemVenda);
					}

					setLabels(itensVenda.get(itensVenda.size() -1));
					carregarProdutoNaTabela(itensVenda);
				}
			} catch (Exception e) {
				System.out.println("Erro ao adicionar item venda, motivo: " + e.getMessage());
			}
		}
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
			labelNomeDoProduto.setText(itemVenda.getProduto().getNome());
			labelValorUnitario.setText("R$ " + Conversor.numeroParaString(itemVenda.getProduto().getPreco().doubleValue()));
			labelTotalDoItem.setText("R$ " + Conversor.numeroParaString(itemVenda.getPrecoTotal().doubleValue()));
			labelSubtotal.setText("R$ " + Conversor.numeroParaString(calcularValorTotal().doubleValue()));
		} else {
			labelNomeDoProduto.setText("");
			labelValorUnitario.setText("R$ 0,00");
			labelTotalDoItem.setText("R$ 0,00");
			labelSubtotal.setText("R$ " + Conversor.numeroParaString(calcularValorTotal().doubleValue()));
			labelTroco.setText("R$ 0,00");
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
		labelTroco.setText("R$ " + Conversor.numeroParaString(calcularTroco().doubleValue()));
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
			
		} else if (qtdeParaRemover >= 0.1) {
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
		String mensagem = qtdeParaRemover + " unidade(s) do produto: " + itemSelecionado.getProduto().getNome() + " foram removidas";
		mensagemDeAviso.abrirTela(mensagem);
		tbItensVenda.updateUI();
	}

	private void salvar() {
		if (cbxFormaDePagamento.getSelectedIndex() == -1) {
			mensagemDeAviso.abrirTela("A forma de pagamento é obrigatória");
		} else {
			Venda venda = new Venda();
			venda.setDataDeVenda(LocalDateTime.now());
			venda.setStatus(Status.A);
			venda.setValorTotal(calcularValorTotal());
			String tipoDePagamento = cbxFormaDePagamento.getSelectedItem().toString();
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
