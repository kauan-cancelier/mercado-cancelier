package br.com.mercadocancelier.views.fornecedor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));

		JPanel panelSuperior = new JPanel();
		contentPane.add(panelSuperior, BorderLayout.NORTH);
		panelSuperior.setLayout(new BorderLayout(0, 20));

		JPanel panelCadastrar = new JPanel();
		panelSuperior.add(panelCadastrar, BorderLayout.EAST);
		panelCadastrar.setLayout(new BorderLayout(0, 0));

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				telaCadastroDeFornecedor.exibirTelaModoInsercao();
				telaCadastroDeFornecedor.setVisible(true);
			}
		});
		
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
		
		btnAdicionar.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAdicionar.setFont(new Font("Dialog", Font.PLAIN, 20));
		panelCadastrar.add(btnAdicionar, BorderLayout.NORTH);

		JPanel panelFiltrar = new JPanel();
		panelSuperior.add(panelFiltrar, BorderLayout.SOUTH);
		panelFiltrar.setLayout(new BorderLayout(20, 0));

		JLabel lblNomeDoProduto = new JLabel("Nome do fornecedor");
		lblNomeDoProduto.setFont(new Font("Dialog", Font.PLAIN, 17));
		panelFiltrar.add(lblNomeDoProduto, BorderLayout.NORTH);

		JButton btnFiltrar = new JButton("Listar todos");
		btnFiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					listarTodosFornecedores();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(contentPane, ex.getMessage());
				}
			}
		});
		
		panelFiltrar.add(btnFiltrar, BorderLayout.EAST);
		btnFiltrar.setFont(new Font("Dialog", Font.PLAIN, 20));
		
		cbFornecedores = new JComboBox<Fornecedor>();
		panelFiltrar.add(cbFornecedores, BorderLayout.CENTER);
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

		JPanel panelMeio = new JPanel();
		contentPane.add(panelMeio, BorderLayout.CENTER);
		panelMeio.setLayout(new BorderLayout(0, 0));

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
				  ListSelectionModel selectionModel = tableFornecedor.getSelectionModel();
			        selectionModel.addListSelectionListener(new ListSelectionListener() {
			            @Override
			            public void valueChanged(ListSelectionEvent e) {
			                if (!e.getValueIsAdjusting()) {
			                    int selectedRow = tableFornecedor.getSelectedRow();
			                    if (selectedRow >= 0) {
			                        FornecedorTableModel model = (FornecedorTableModel) tableFornecedor.getModel();
			                        Fornecedor fornecedorSelecionado = model.getPor(selectedRow);
			                        atualizarTextoBotao(fornecedorSelecionado);
			                    }
			                }
			            }
			        });
				tableFornecedor.getTableHeader().setReorderingAllowed(false);
				tableFornecedor.getTableHeader().setResizingAllowed(false);
		panelMeio.add(scrollPane);

		JPanel panelAcoes = new JPanel();
		contentPane.add(panelAcoes, BorderLayout.SOUTH);
		panelAcoes.setLayout(new BorderLayout(0, 10));

		JPanel panelBotoes = new JPanel();
		panelAcoes.add(panelBotoes, BorderLayout.EAST);
		panelBotoes.setLayout(new GridLayout(0, 3, 0, 0));

		JButton btnInativarOuAtivar = new JButton("Excluir");
		btnInativarOuAtivar.setForeground(Color.WHITE);
		btnInativarOuAtivar.setFont(new Font("Dialog", Font.PLAIN, 20));
		btnInativarOuAtivar.setBackground(Color.RED);
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
		setLocationRelativeTo(null);
		panelBotoes.add(btnInativarOuAtivar);
		
				JButton btnEditar = new JButton("Editar");
				btnEditar.setBackground(SystemColor.info);
				btnEditar.setFont(new Font("Dialog", Font.PLAIN, 20));
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
				
				java.awt.Component horizontalStrut = Box.createHorizontalStrut(10);
				horizontalStrut.setEnabled(false);
				panelBotoes.add(horizontalStrut);
				panelBotoes.add(btnEditar);
				
				java.awt.Component verticalStrut = Box.createVerticalStrut(45);
				panelAcoes.add(verticalStrut, BorderLayout.CENTER);
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
