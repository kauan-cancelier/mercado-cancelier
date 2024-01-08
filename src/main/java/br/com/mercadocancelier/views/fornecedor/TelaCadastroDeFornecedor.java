package br.com.mercadocancelier.views.fornecedor;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Fornecedor;
import br.com.mercadocancelier.service.FornecedorService;

@Component
public class TelaCadastroDeFornecedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtCnpj;
	
	@Autowired
	private FornecedorService service;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastroDeFornecedor frame = new TelaCadastroDeFornecedor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	public TelaCadastroDeFornecedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 476, 305);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = txtNome.getText();
					String cnpj = txtCnpj.getText();
					if (nome == null || cnpj == null) {
						JOptionPane.showMessageDialog(contentPane, "Todos os campos são obrigatórios.");
					} else {
						cnpj = cnpj.replaceAll("\\D", "");
						Fornecedor fornecedorNovo = new Fornecedor();
						fornecedorNovo.setNome(nome.toUpperCase());
						fornecedorNovo.setCnpj(cnpj);
						JOptionPane.showMessageDialog(contentPane, "Fornecedor salvo com sucesso!");
						service.salvar(fornecedorNovo);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());				}
			}
		});
		btnSalvar.setBounds(335, 226, 117, 25);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int isCancelar  = JOptionPane.showConfirmDialog(contentPane, "Tem certeza que deseja cancelar a inserção?");
					if (isCancelar == 1) {
						txtNome.setText(null);
						txtCnpj.setText(null);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		btnCancelar.setBounds(206, 226, 117, 25);
		contentPane.add(btnCancelar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaConsultaDeFornecedor view = new TelaConsultaDeFornecedor();
				view.setVisible(true);
				dispose();
			}
		});
		btnPesquisar.setBounds(321, 27, 117, 25);
		contentPane.add(btnPesquisar);
		
		txtNome = new JTextField();
		txtNome.setBounds(85, 84, 300, 36);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(89, 68, 70, 15);
		contentPane.add(lblNome);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(89, 144, 70, 15);
		contentPane.add(lblCnpj);
		
	   try {
            MaskFormatter cnpjMask = new MaskFormatter("##.###.###/####-##");
            txtCnpj = new JFormattedTextField(cnpjMask);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        txtCnpj.setColumns(10);
        txtCnpj.setBounds(85, 160, 300, 36);
        contentPane.add(txtCnpj);
	}
}
