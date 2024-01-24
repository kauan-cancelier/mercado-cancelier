package br.com.mercadocancelier.views.fornecedor;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
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
import java.awt.Color;

@Component
public class TelaConsultaDeFornecedor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<Fornecedor> cbFornecedores;
	private JTable tableFornecedor;
	private JButton btnInativarOuAtivar = new JButton();
	private static final int OPCAO_SIM = 0;

	@Autowired
	private FornecedorService service;

	@Autowired
	@Lazy
	private TelaCadastroDeFornecedor telaCadastroDeFornecedor;

	@PostConstruct
	public void carregarComboFornecedor() {
		cbFornecedores.removeAllItems();
		List<Fornecedor> fornecedores = service.listarTodos();
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
		btnAdicionar.setBackground(new Color(255, 255, 255));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				telaCadastroDeFornecedor.exibirTelaModoInsercao();
				telaCadastroDeFornecedor.setVisible(true);
				dispose();
			}
		});
		btnAdicionar.setBounds(1118, 22, 207, 44);
		contentPane.add(btnAdicionar);

		cbFornecedores = new JComboBox<Fornecedor>();
		cbFornecedores.addItem(new Fornecedor());
		cbFornecedores.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					Fornecedor fornecedor = (Fornecedor) cbFornecedores.getSelectedItem();
					atualizarTabelaPor(fornecedor.getNome());
					atualizarTextoBotao(fornecedor);
				}

			}
		});
		cbFornecedores.setBounds(45, 107, 1050, 44);
		contentPane.add(cbFornecedores);

		JButton btnListar = new JButton("Listar todos");
		btnListar.setBackground(new Color(255, 255, 255));
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					listarTodosFornecedores();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		btnListar.setBounds(1118, 107, 207, 44);
		contentPane.add(btnListar);

		// Centraliza o conteúdo das células na tabela
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);

		JScrollPane scrollPane = new JScrollPane(tableFornecedor);
		scrollPane.setBounds(45, 188, 1293, 422);

		// Configuração da tabela
		tableFornecedor.setDefaultRenderer(Object.class, centerRenderer);
		tableFornecedor.setRowHeight(40);
		tableFornecedor.getTableHeader()
				.setPreferredSize(new Dimension(tableFornecedor.getTableHeader().getWidth(), 50));
		tableFornecedor.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableFornecedor.getTableHeader().setReorderingAllowed(false);
		tableFornecedor.getTableHeader().setResizingAllowed(false);

		contentPane.add(scrollPane);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBackground(new Color(255, 255, 255));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableFornecedor.getSelectedRow();
				if (linhaSelecionada >= 0) {
					FornecedorTableModel model = (FornecedorTableModel) tableFornecedor.getModel();
					Fornecedor fornecedorSelecionado = model.getPor(linhaSelecionada);
					telaCadastroDeFornecedor.setFornecedor(fornecedorSelecionado);
					telaCadastroDeFornecedor.setEdicaoFornecedor(true);
					telaCadastroDeFornecedor.setVisible(true);
					telaCadastroDeFornecedor.exibirTelaModoEdicao(fornecedorSelecionado.getId());
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma linha para edição.");
				}
			}
		});
		btnEditar.setBounds(1118, 654, 207, 44);
		contentPane.add(btnEditar);
		btnInativarOuAtivar.setBackground(new Color(255, 255, 255));

		btnInativarOuAtivar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int linhaSelecionada = tableFornecedor.getSelectedRow();
				FornecedorTableModel model = (FornecedorTableModel) tableFornecedor.getModel();

				if (linhaSelecionada >= 0 && !model.isVazio()) {
					Fornecedor fornecedorSelecionado = model.getPor(linhaSelecionada);
					try {
						processarAtivacaoInativacaoDoFornecedor(fornecedorSelecionado);
						atualizarTabelaPor(fornecedorSelecionado.getNome());
						model.atualizarStatusNaTabela();
					} catch (Exception ex) {
						exibirMensagemErro("Erro ao processar a ativação/inativação do fornecedor: " + ex.getMessage());
					}
				} else {
					exibirMensagem("Selecione uma linha para ativação/inativação.");
				}
			}
		});
		btnInativarOuAtivar.setBounds(899, 654, 207, 44);
		contentPane.add(btnInativarOuAtivar);

		JLabel lblNome = new JLabel("Nome do Fornecedor");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNome.setBounds(45, 85, 231, 15);
		contentPane.add(lblNome);
	}

	public void atualizarTabelaPor(String nome) {
		List<Fornecedor> fornecedorEncontrado = service.listarPor(nome);
		FornecedorTableModel model = new FornecedorTableModel(fornecedorEncontrado);
		tableFornecedor.setModel(model);
	}

	public void atualizarValoresConsulta() {
		carregarComboFornecedor();
		List<Fornecedor> listaVaziaFornecedor = Collections.emptyList();
		FornecedorTableModel model = new FornecedorTableModel(listaVaziaFornecedor);
		tableFornecedor.setModel(model);
	}

	private void atualizarTextoBotao(Fornecedor fornecedor) {
		if (fornecedor != null) {
			if (fornecedor.isAtivo()) {
				btnInativarOuAtivar.setText("Inativar");
			} else {
				btnInativarOuAtivar.setText("Ativar");
			}
		}
	}

	private void listarTodosFornecedores() {
		atualizarValoresConsulta();
		List<Fornecedor> fornecedorEncontrado = service.listarTodos();
		FornecedorTableModel model = new FornecedorTableModel(fornecedorEncontrado);
		tableFornecedor.setModel(model);
	}

	private void processarAtivacaoInativacaoDoFornecedor(Fornecedor fornecedorSelecionado) {
		if (fornecedorSelecionado.isAtivo()) {
			int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja inativar o fornecedor?", "Inativação",
					JOptionPane.YES_NO_OPTION);

			if (opcao == OPCAO_SIM) {
				Integer fornecedorInativado = service.inativarPor(fornecedorSelecionado.getId());
				if (fornecedorInativado > 0) {
					Fornecedor fornecedorStatusAtual = service.buscarPor(fornecedorSelecionado.getId());
					atualizarTextoBotao(fornecedorStatusAtual);
					exibirMensagem("Fornecedor inativado.");
				} else {
					exibirMensagem("Fornecedor já está inativo.");
				}
			}
		} else {
			int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja ativar o fornecedor?", "Ativação",
					JOptionPane.YES_NO_OPTION);
			if (opcao == OPCAO_SIM) {
				Integer fornecedorAtivado = service.ativarPor(fornecedorSelecionado.getId());
				if (fornecedorAtivado > 0) {
					Fornecedor fornecedorStatusAtual = service.buscarPor(fornecedorSelecionado.getId());
					atualizarTextoBotao(fornecedorStatusAtual);
					exibirMensagem("Fornecedor ativado.");
				} else {
					exibirMensagem("Fornecedor já está ativo.");
				}
			}
		}
	}

	private void exibirMensagem(String mensagem) {
		JOptionPane.showMessageDialog(contentPane, mensagem);
	}

	private void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(contentPane, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}

}
