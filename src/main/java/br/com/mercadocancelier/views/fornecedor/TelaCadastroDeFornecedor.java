package br.com.mercadocancelier.views.fornecedor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
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
	
	@Autowired @Lazy
	private TelaConsultaDeFornecedor telaConsultaDeFornecedor;

	public TelaCadastroDeFornecedor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
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
					String cnpjNumerico = cnpj.replaceAll("\\D", "");
					if (nome.isEmpty() || cnpjNumerico.isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Todos os campos são obrigatórios.");
					} else {
						Fornecedor fornecedorNovo = new Fornecedor();
						fornecedorNovo.setNome(nome.toUpperCase());
						fornecedorNovo.setCnpj(cnpjNumerico);
						service.salvar(fornecedorNovo);
						JOptionPane.showMessageDialog(contentPane, "Fornecedor salvo com sucesso!");
					}
					txtNome.setText(null);
					txtCnpj.setText(null);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());				
				}
			}
		});
		btnSalvar.setBounds(1134, 648, 207, 44);
		contentPane.add(btnSalvar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int isCancelar  = JOptionPane.showConfirmDialog(contentPane, 
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
		btnCancelar.setBounds(896, 648, 207, 44);
		contentPane.add(btnCancelar);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaConsultaDeFornecedor.setVisible(true);
				dispose();
			}
		});
		btnPesquisar.setBounds(1134, 30, 207, 44);
		contentPane.add(btnPesquisar);
		
		txtNome = new JTextField();
		txtNome.setBounds(109, 198, 1205, 57);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNome.setBounds(109, 170, 70, 15);
		contentPane.add(lblNome);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCnpj.setBounds(109, 363, 70, 29);
		contentPane.add(lblCnpj);
		
		txtCnpj = new JFormattedTextField();
		try {
		    MaskFormatter cnpjMask = new MaskFormatter("##.###.###/####-##");
		    cnpjMask.install((JFormattedTextField) txtCnpj);
		} catch (ParseException e) {
		    e.printStackTrace();
		}
        txtCnpj.setColumns(10);
        txtCnpj.setBounds(109, 404, 1205, 63);
        contentPane.add(txtCnpj);
	}
}
