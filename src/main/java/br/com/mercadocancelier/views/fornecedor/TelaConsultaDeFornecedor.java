package br.com.mercadocancelier.views.fornecedor;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Fornecedor;
import br.com.mercadocancelier.service.FornecedorService;
import br.com.mercadocancelier.views.table.FornecedorTableModel;

@Component
public class TelaConsultaDeFornecedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Fornecedor> cbFornecedores;
	private JTable tableFornecedor;
	
	@Autowired
	private FornecedorService service;
	
	public void carregarComboFornecedor(List<Fornecedor> fornecedores) {
		cbFornecedores.addItem(null);
		for (Fornecedor fornecedor : fornecedores) {
			cbFornecedores.addItem(fornecedor);
		}
	}

	public TelaConsultaDeFornecedor() {
		FornecedorTableModel model = new FornecedorTableModel();
		this.tableFornecedor = new JTable(model);
		tableFornecedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 549, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCadastroDeFornecedor view = new TelaCadastroDeFornecedor();
				view.setVisible(true);
				dispose();
			}
		});
		btnAdicionar.setBounds(393, 12, 128, 31);
		contentPane.add(btnAdicionar);
		
		cbFornecedores = new JComboBox<Fornecedor>();
		cbFornecedores.setBounds(22, 75, 359, 31);
		contentPane.add(cbFornecedores);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					List<Fornecedor> fornecedorEncontrado = service.listarPor(cbFornecedores.getName());
					FornecedorTableModel model = new FornecedorTableModel(fornecedorEncontrado);
					tableFornecedor.setModel(model);
					if (fornecedorEncontrado.isEmpty()) {
						JOptionPane.showMessageDialog(contentPane, "Não foi encontrado nenhum"
								+ " fornecedor com esse nome.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());				
				}
			}
		});
		btnListar.setBounds(404, 78, 117, 25);
		contentPane.add(btnListar);
		
		JScrollPane scrollPane = new JScrollPane(tableFornecedor);
		scrollPane.setBounds(22, 118, 499, 141);
		contentPane.add(scrollPane);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditar.setBounds(393, 269, 128, 31);
		contentPane.add(btnEditar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExcluir.setBounds(256, 269, 128, 31);
		contentPane.add(btnExcluir);
		
		JLabel lblNome = new JLabel("Nome do Fornecedor");
		lblNome.setBounds(26, 54, 147, 15);
		contentPane.add(lblNome);
	}
}
