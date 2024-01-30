package br.com.mercadocancelier.views.fornecedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.mercadocancelier.entity.Fornecedor;
import br.com.mercadocancelier.service.FornecedorService;

@org.springframework.stereotype.Component
public class TelaCadastroDeFornecedor extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	private JTextField txtNome;
	
	@Autowired
	private FornecedorService service;

	@Autowired
	@Lazy
	private TelaConsultaDeFornecedor telaConsultaDeFornecedor;
	
	private JFormattedTextField txtCnpj;
	
	private boolean isEdicaoFornecedor;
	
	private Integer idFornecedorSelecionado;

	public TelaCadastroDeFornecedor() {
		setModal(true);
		setLocationRelativeTo(null);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				telaConsultaDeFornecedor.atualizarValoresConsulta();;
				dispose();
			}
		});

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(20, 20));
		
		JPanel panelPesquisar = new JPanel();
		contentPane.add(panelPesquisar, BorderLayout.NORTH);
		panelPesquisar.setLayout(new BorderLayout(0, 0));
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaConsultaDeFornecedor.atualizarValoresConsulta();
				telaConsultaDeFornecedor.setVisible(true);
				dispose();
			}
		});
		btnPesquisar.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnPesquisar.setHorizontalAlignment(SwingConstants.LEFT);
		panelPesquisar.add(btnPesquisar, BorderLayout.EAST);
		
		Component verticalStrut_1 = Box.createVerticalStrut(35);
		panelPesquisar.add(verticalStrut_1, BorderLayout.CENTER);
		
		JPanel panelAcoes = new JPanel();
		contentPane.add(panelAcoes, BorderLayout.SOUTH);
		panelAcoes.setLayout(new BorderLayout(0, 50));
		
		JPanel panelBotoes = new JPanel();
		panelAcoes.add(panelBotoes, BorderLayout.EAST);
		panelBotoes.setLayout(new GridLayout(1, 1, 20, 0));
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int isCancelar = JOptionPane.showConfirmDialog(contentPane,
							"Tem certeza que deseja cancelar a inserção?");
					if (isCancelar == 0) {
						txtNome.setText(null);
						txtCnpj.setText(null);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		panelBotoes.add(btnCancelar);
		btnCancelar.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		Component verticalStrut = Box.createVerticalStrut(35);
		panelAcoes.add(verticalStrut, BorderLayout.CENTER);
		
		JPanel panelMeio = new JPanel();
		panelMeio.setBorder(null);
		contentPane.add(panelMeio, BorderLayout.CENTER);
		panelMeio.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panelNomeDoFornecedor = new JPanel();
		panelNomeDoFornecedor.setBorder(null);
		panelMeio.add(panelNomeDoFornecedor);
		panelNomeDoFornecedor.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblNomeDoFornecedor = new JLabel("Nome do fornecedor");
		lblNomeDoFornecedor.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelNomeDoFornecedor.add(lblNomeDoFornecedor);
		
		txtNome = new JTextField();
		txtNome.setColumns(100);
		txtNome.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!txtNome.getText().isEmpty()) {
					txtNome.setCaretPosition(txtNome.getDocument().getLength());
				}
			}
		});
		panelNomeDoFornecedor.add(txtNome);
		
		JPanel panelCnpj = new JPanel();
		panelCnpj.setBorder(null);
		panelMeio.add(panelCnpj);
		panelCnpj.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblCnpj = new JLabel("Cnpj");
		lblCnpj.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelCnpj.add(lblCnpj);
		
		txtCnpj = new JFormattedTextField();
		txtCnpj = new JFormattedTextField();
		try {
			MaskFormatter cnpjMask = new MaskFormatter("##.###.###/####-##");
			cnpjMask.install((JFormattedTextField) txtCnpj);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		txtCnpj.setColumns(10);
		txtCnpj.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (txtCnpj.getText().equals("  .   .   /    -  ")) {
					txtCnpj.setCaretPosition(0);
				}
			}
		});
		panelCnpj.add(txtCnpj);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String nome = txtNome.getText();
					String cnpj = txtCnpj.getText();
					String cnpjNumerico = cnpj.replaceAll("\\D", "");
					if (nome.isEmpty() || cnpjNumerico.isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Todos os campos são obrigatórios.");
					} else {
						if (!isEdicaoFornecedor) {
							Fornecedor fornecedorNovo = new Fornecedor();
							fornecedorNovo.setNome(nome.toUpperCase());
							fornecedorNovo.setCnpj(cnpjNumerico);
							service.salvar(fornecedorNovo);
							JOptionPane.showMessageDialog(contentPane, "Fornecedor salvo com sucesso!");
							txtNome.setText(null);
							txtCnpj.setText(null);
						} else {
							Fornecedor fornecedorParaEditar = new Fornecedor();
							fornecedorParaEditar.setId(idFornecedorSelecionado);
							fornecedorParaEditar.setNome(nome);
							fornecedorParaEditar.setCnpj(cnpjNumerico);
							service.alterar(fornecedorParaEditar);
				            telaConsultaDeFornecedor.atualizarValoresConsulta();
							JOptionPane.showMessageDialog(contentPane, "Fornecedor alterado.");
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		btnSalvar.setForeground(Color.WHITE);
		btnSalvar.setBackground(SystemColor.desktop);
		panelBotoes.add(btnSalvar);
		btnSalvar.setFont(new Font("Dialog", Font.PLAIN, 20)); 
		
	}
	
	public void setFornecedor(Fornecedor fornecedor) {
		this.txtNome.setText(fornecedor.getNome());
		this.txtCnpj.setText(fornecedor.getCnpj());
	}

	public void setEdicaoFornecedor(boolean isEdicaoFornecedor) {
		this.isEdicaoFornecedor = isEdicaoFornecedor;
	}

	public void exibirTelaModoEdicao(Integer idFornecedorSelecionado) {
		this.idFornecedorSelecionado = idFornecedorSelecionado;
	}
	
	public void exibirTelaModoInsercao() {
		isEdicaoFornecedor = false;
		txtNome.setText(null);
		txtCnpj.setText(null);	
	}

}
