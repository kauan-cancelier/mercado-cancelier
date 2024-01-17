package br.com.mercadocancelier.views.fornecedor;

import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.table.DefaultTableCellRenderer;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.entity.Fornecedor;
import br.com.mercadocancelier.service.FornecedorService;
import br.com.mercadocancelier.views.table.FornecedorTableModel;
import jakarta.annotation.PostConstruct;

@Component
public class TelaConsultaDeFornecedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Fornecedor> cbFornecedores;
	private JTable tableFornecedor;
	
	@Autowired
	private FornecedorService service;
	
	@Autowired @Lazy
	private TelaCadastroDeFornecedor telaCadastroDeFornecedor;
	
	@PostConstruct
	public void carregarComboFornecedor() {
		List<Fornecedor> fornecedores = service.listarTodos();
		cbFornecedores.addItem(null);
		for (Fornecedor fornecedor : fornecedores) {
			cbFornecedores.addItem(fornecedor);
		}
	}

	@Autowired
	public TelaConsultaDeFornecedor() {
		FornecedorTableModel model = new FornecedorTableModel();
		this.tableFornecedor = new JTable(model);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaCadastroDeFornecedor.setVisible(true);
				dispose();
			}
		});
		btnAdicionar.setBounds(1118, 22, 207, 44);
		contentPane.add(btnAdicionar);
		
		cbFornecedores = new JComboBox<Fornecedor>();
		cbFornecedores.setBounds(45, 107, 1050, 44);
		contentPane.add(cbFornecedores);
		
		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Fornecedor fornecedorInformado = (Fornecedor) cbFornecedores.getSelectedItem();
					List<Fornecedor> fornecedorEncontrado = service.listarPor(fornecedorInformado.getNome());
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
		btnListar.setBounds(1118, 107, 207, 44);
		contentPane.add(btnListar);
		
		JScrollPane scrollPane = new JScrollPane(tableFornecedor);
		tableFornecedor.getTableHeader().setPreferredSize(new Dimension(tableFornecedor.getTableHeader().getWidth(), 50));
		tableFornecedor.setRowHeight(40);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		tableFornecedor.setDefaultRenderer(Object.class, centerRenderer);
		scrollPane.setBounds(45, 188, 1293, 422);
		tableFornecedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFornecedor.getTableHeader().setReorderingAllowed(false);
		tableFornecedor.getTableHeader().setResizingAllowed(false);
		contentPane.add(scrollPane);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditar.setBounds(1118, 654, 207, 44);
		contentPane.add(btnEditar);
		
		JButton btnInativar = new JButton("Inativar");
		btnInativar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableFornecedor.getSelectedRow();
				FornecedorTableModel model = (FornecedorTableModel) tableFornecedor.getModel();
				if(linhaSelecionada >= 0 && !model.isVazio()) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja inativar o fornecedor?",
							"Remoção", JOptionPane.YES_NO_OPTION);
					if(opcao == 0) {
						Fornecedor fornecedorSelecionado = model.getPor(linhaSelecionada);
						try {
							Integer fornecedorInativado = service.inativarPor(fornecedorSelecionado.getId());
							if (fornecedorInativado > 0) {
								JOptionPane.showMessageDialog(contentPane, "Fornecedor inativado.");								
							} else {
								JOptionPane.showMessageDialog(contentPane, "Fornecedor já está inativo.");								
							}
							atualizaTabela(fornecedorSelecionado.getNome());
							model.inativou();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(contentPane, ex.getMessage());
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para inativação.");
				}
				
			}
		});
		btnInativar.setBounds(899, 654, 207, 44);
		contentPane.add(btnInativar);
		
		JLabel lblNome = new JLabel("Nome do Fornecedor");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNome.setBounds(45, 85, 231, 15);
		contentPane.add(lblNome);
	}
	
	void atualizaTabela(String nome) {
		List<Fornecedor> fornecedorEncontrado = service.listarPor(nome);

		FornecedorTableModel model = new FornecedorTableModel(fornecedorEncontrado);
		tableFornecedor.setModel(model);
	}
	
}
