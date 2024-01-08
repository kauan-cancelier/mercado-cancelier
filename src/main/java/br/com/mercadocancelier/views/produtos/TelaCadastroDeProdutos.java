package br.com.mercadocancelier.views.produtos;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Produto;
import br.com.mercadocancelier.service.ProdutoService;
import br.com.mercadocancelier.views.components.MenuBar;

@Component
public class TelaCadastroDeProdutos extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final JPanel contentPanel = new JPanel();
	
	private JTextField txtCodigo;
	
	private JTextField txtPreco;
	
	private JTextField txtNome;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private MenuBar menuBar;
	private JTextField txtEstoqueInicial;
	
	public void abrirTela() {
		setJMenuBar(menuBar);
		this.setVisible(true);
	}
	
	public TelaCadastroDeProdutos() {
		setTitle("Mercado Cancelier - Cadastro de produto");
		setResizable(false);
		setBounds(100, 100, 1366, 573);
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBackground(SystemColor.text);
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JLabel lblPreo = new JLabel("Preço");
		lblPreo.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		txtPreco = new JTextField();
		txtPreco.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(txtPreco, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblPreo)
							.addContainerGap(576, Short.MAX_VALUE))))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(22)
					.addComponent(lblPreo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtPreco, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 627, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNome)
							.addContainerGap(567, Short.MAX_VALUE))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 107, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(22)
					.addComponent(lblNome)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNome, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		
		txtEstoqueInicial = new JTextField();
		txtEstoqueInicial.setColumns(10);
		
		JLabel lblEstoqueInicial = new JLabel("Estoque inicial");
		lblEstoqueInicial.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 627, Short.MAX_VALUE)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(txtEstoqueInicial, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblEstoqueInicial)
							.addContainerGap(567, Short.MAX_VALUE))))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 107, Short.MAX_VALUE)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(22)
					.addComponent(lblEstoqueInicial)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtEstoqueInicial, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_4.setLayout(gl_panel_4);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 627, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 627, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 627, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 1306, Short.MAX_VALUE))
					.addGap(34))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(66)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnPesquisar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))
					.addGap(53))
		);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBackground(SystemColor.text);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String codigo = txtCodigo.getText();
					String nome = txtNome.getText();
					String preco = txtPreco.getText();
					String estoqueInicial = txtEstoqueInicial.getText();
					Produto produto = new Produto();
					produto.setCodigo(Integer.parseInt(codigo));
					produto.setNome(nome);
					produto.setPreco(BigDecimal.valueOf(Double.parseDouble(preco)));
					produto.setEstoque(Integer.parseInt(estoqueInicial));
					produtoService.salvar(produto);
					dispose();
					txtCodigo.setText("");
					txtNome.setText("");
					txtPreco.setText("");
					txtEstoqueInicial.setText("");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPanel, "Erro ao salvar produto. ");
					ex.printStackTrace();
				}
			}
		});
		btnSalvar.setBackground(SystemColor.text);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
					.addGap(30)
					.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
					.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap(25, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSalvar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancelar, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addGap(19))
		);
		panel_3.setLayout(gl_panel_3);
		
		JLabel lblNewLabel = new JLabel("Código");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtCodigo, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addContainerGap(567, Short.MAX_VALUE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(22)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtCodigo, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
	}
	
}
