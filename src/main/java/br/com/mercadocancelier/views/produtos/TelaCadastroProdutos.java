package br.com.mercadocancelier.views.produtos;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.entity.enums.UnidadeDeMedida;
import br.com.mercadocancelier.service.ProdutoService;
import br.com.mercadocancelier.util.Conversor;
import br.com.mercadocancelier.views.components.MensagemDeAviso;
import br.com.mercadocancelier.views.components.MensagemDeSucesso;

@Component
public class TelaCadastroProdutos extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtPreco;
	private JTextField txtNome;
	private JTextField txtEstoqueInicial;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private TelaConsultaProdutos telaConsultaDeProdutos;
	
	@Autowired
	private MensagemDeAviso mensagemDeAviso;
	
	@Autowired
	private MensagemDeSucesso mensagemDeSucesso;

	private Produto produto;

	public void abrirTela() {
		produto = null;
		this.limparCampos();
		this.setVisible(true);
	}
	
	public void abrirModoEdicao(Produto produto) {
		this.produto = produto;
		this.modoEdicao(produto);
		this.setVisible(true);
	}
	
	public TelaCadastroProdutos() {
		setLocationRelativeTo(null);
		setModal(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(20, 20));
		
		JPanel panelPesquisar = new JPanel();
		contentPane.add(panelPesquisar, BorderLayout.NORTH);
		panelPesquisar.setLayout(new BorderLayout(0, 0));
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaConsultaDeProdutos.abrirTela();
				dispose();
			}
		});
		btnPesquisar.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnPesquisar.setHorizontalAlignment(SwingConstants.LEFT);
		panelPesquisar.add(btnPesquisar, BorderLayout.EAST);
		
		JPanel panelFormulario = new JPanel();
		panelFormulario.setBackground(Color.WHITE);
		contentPane.add(panelFormulario, BorderLayout.CENTER);
		panelFormulario.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelSuperior = new JPanel();
		panelFormulario.add(panelSuperior);
		panelSuperior.setBackground(Color.WHITE);
		panelSuperior.setLayout(new GridLayout(0, 1, 50, 20));
		
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panelSuperior.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNomeDoProduto = new JLabel("Nome do produto");
		panel.add(lblNomeDoProduto);
		
		txtNome = new JTextField();
		panel.add(txtNome);
		txtNome.setColumns(100);
		
		JPanel panelMeio = new JPanel();
		panelFormulario.add(panelMeio);
		panelMeio.setLayout(new GridLayout(0, 2, 20, 20));
		
		JPanel panelCodigo = new JPanel();
		panelCodigo.setBorder(null);
		panelMeio.add(panelCodigo);
		panelCodigo.setLayout(new GridLayout(0, 1, 20, 0));
		
		JLabel lblCodigo = new JLabel("Código");
		panelCodigo.add(lblCodigo);
		
		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Dialog", Font.PLAIN, 12));
		panelCodigo.add(txtCodigo);
		txtCodigo.setColumns(5);
		
		JPanel panelPreco = new JPanel();
		panelPreco.setBorder(null);
		panelMeio.add(panelPreco);
		panelPreco.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblPreo = new JLabel("Preço");
		panelPreco.add(lblPreo);
		
		txtPreco = new JTextField();
		panelPreco.add(txtPreco);
		txtPreco.setColumns(10);
		
		JPanel panelInferior = new JPanel();
		panelFormulario.add(panelInferior);
		panelInferior.setLayout(new GridLayout(0, 1, 20, 20));
		
		JPanel panelComboEUnidade = new JPanel();
		panelInferior.add(panelComboEUnidade);
		panelComboEUnidade.setLayout(new GridLayout(1, 1, 20, 0));
		
		JPanel panelUnidade = new JPanel();
		panelUnidade.setBorder(null);
		panelComboEUnidade.add(panelUnidade);
		panelUnidade.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblTipoUnidade = new JLabel("Unidade de medida");
		panelUnidade.add(lblTipoUnidade);
		
		JComboBox<UnidadeDeMedida> cbxUnidadeDeMedida = new JComboBox<UnidadeDeMedida>();
		panelUnidade.add(cbxUnidadeDeMedida);
		cbxUnidadeDeMedida.setModel(new DefaultComboBoxModel<UnidadeDeMedida>(UnidadeDeMedida.values()));
		
		JPanel panelEstoque = new JPanel();
		panelEstoque.setBorder(null);
		panelComboEUnidade.add(panelEstoque);
		panelEstoque.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblEstoqueInicial = new JLabel("Estoque inicial");
		panelEstoque.add(lblEstoqueInicial);
		
		txtEstoqueInicial = new JTextField();
		panelEstoque.add(txtEstoqueInicial);
		txtEstoqueInicial.setColumns(10);
		
		JPanel panelAcoes = new JPanel();
		contentPane.add(panelAcoes, BorderLayout.SOUTH);
		panelAcoes.setLayout(new BorderLayout(0, 50));
		
		JPanel panelBotoes = new JPanel();
		panelAcoes.add(panelBotoes, BorderLayout.EAST);
		panelBotoes.setLayout(new GridLayout(1, 1, 20, 0));
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				limparCampos();
			}
		});
		panelBotoes.add(btnCancelar);
		btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String codigo = txtCodigo.getText();
					String nome = txtNome.getText();
					String preco = txtPreco.getText();
					String estoqueInicial = txtEstoqueInicial.getText();
					String unidadeDeMedida = cbxUnidadeDeMedida.getSelectedItem().toString();
					
					Produto produtoParaSalvar = montarProduto(codigo, nome, preco, estoqueInicial, unidadeDeMedida);
					if (produto != null && produto.getId() != null) {
		                produtoParaSalvar.setId(produto.getId());
		            }
					
					salvar(produtoParaSalvar);
					produto = null;
					limparCampos();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(SystemColor.desktop);
		panelBotoes.add(btnSalvar);
		btnSalvar.setFont(new Font("Dialog", Font.PLAIN, 20));
		
	}
	
	private void salvar(Produto produto) {
		produtoService.salvar(produto);
		mensagemDeSucesso.abrirTela("Produto salvo.");
		limparCampos();
	}

	private Produto montarProduto(String codigo, String nome, String preco, String estoqueInicial, String unidadeDeMedida) {
		
		if (codigo.isBlank()) {
			mensagemDeAviso.abrirTela("O código é obrigatório para salvar. ");
			return null;
		}
		
		if (nome.isBlank()) {
			mensagemDeAviso.abrirTela("O nome é obrigatório.");
			return null;
		}
		
		if (preco.isBlank()) {
			mensagemDeAviso.abrirTela("O preço é obrigatório. ");
			return null;
		}
		
		if (estoqueInicial.isBlank()) {
			mensagemDeAviso.abrirTela("O estoque inicial é obrigatória. ");
			return null;
		}
		
		if (unidadeDeMedida.isBlank()) {
			mensagemDeAviso.abrirTela("A unidade de medida é obrigatória. ");
			return null;
		}
		
		Produto produto = new Produto();
		produto.setCodigo(codigo);
		produto.setNome(nome);
		produto.setUnidadeDeMedida(UnidadeDeMedida.valueOf(unidadeDeMedida));
		
		try {
			Double precoDouble = Conversor.stringParaNumero(preco);
			produto.setPreco(BigDecimal.valueOf(precoDouble));
		} catch (Exception e) {
			mensagemDeAviso.abrirTela("Ocorreu um erro ao salvar o preço. ");
			return null;
		}
		
		try {
			
			produto.setEstoque(BigDecimal.valueOf(Conversor.stringParaNumero(estoqueInicial)));
		} catch (Exception e) {
			mensagemDeAviso.abrirTela("Ocorreu um erro ao salvar o estoque inicial. ");
			return null;
		}
		
		return produto;
	}
	
	private void limparCampos() {
		txtCodigo.setText("");
		txtNome.setText("");
		txtPreco.setText("");
		txtEstoqueInicial.setText("");
	}
	
	private void modoEdicao(Produto produto) {
		txtCodigo.setText(produto.getCodigo());
		txtNome.setText(produto.getNome());
		txtPreco.setText(produto.getPreco().toString());
		txtEstoqueInicial.setText(produto.getEstoque().toString());
	}

}
