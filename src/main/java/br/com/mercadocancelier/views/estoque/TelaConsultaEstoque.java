package br.com.mercadocancelier.views.estoque;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import br.com.mercadocancelier.entity.enums.UnidadeDeMedida;
import br.com.mercadocancelier.views.table.EstoqueTableModel;

public class TelaConsultaEstoque extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaConsultaEstoque frame = new TelaConsultaEstoque();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private JTable tbEstoque;

	public TelaConsultaEstoque() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1366, 768);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		EstoqueTableModel model = new EstoqueTableModel();
		this.tbEstoque = new JTable(model);
		tbEstoque.setForeground(Color.BLACK);
		tbEstoque.setFont(new Font("Dialog", Font.BOLD, 14));
		tbEstoque.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbEstoque.getTableHeader().setOpaque(false);
		tbEstoque.getTableHeader().setBackground(SystemColor.activeCaption);
		tbEstoque.getTableHeader().setForeground(Color.WHITE);
		tbEstoque.getTableHeader().setReorderingAllowed(false);
		tbEstoque.getTableHeader().setResizingAllowed(false);
		tbEstoque.getTableHeader().setPreferredSize(new Dimension(tbEstoque.getTableHeader().getWidth(), 50));
		
		setContentPane(contentPane);
		
		JLabel lblFiltros = new JLabel("Filtros");
		lblFiltros.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblNomeDoProduto = new JLabel("Nome do produto");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblUnidadeDeMedida = new JLabel("Unidade de medida");
		
		JComboBox<UnidadeDeMedida> comboBox = new JComboBox<UnidadeDeMedida>();
		comboBox.setModel(new DefaultComboBoxModel<UnidadeDeMedida>(UnidadeDeMedida.values()));
		
		JLabel lblFornecedor = new JLabel("Fornecedor");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel lblCdigo = new JLabel("Código");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		JCheckBox chckbxItensEmFalta = new JCheckBox("Itens em falta");
		
		JButton btnNewButton = new JButton("Filtrar");
		
		JScrollPane scrollPane = new JScrollPane(tbEstoque);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0)), "Resumo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnExcluirRegistro = new JButton("Excluir registro");
		
		JButton btnAlterarRegistro = new JButton("Alterar registro");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 445, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 565, Short.MAX_VALUE)
							.addComponent(btnAlterarRegistro, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnExcluirRegistro, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblFiltros)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblFornecedor, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
												.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE))
											.addGap(50)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
												.addComponent(lblCdigo, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
										.addComponent(lblNomeDoProduto)
										.addComponent(textField, GroupLayout.PREFERRED_SIZE, 783, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(18)
											.addComponent(chckbxItensEmFalta)
											.addGap(291)
											.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGap(183)
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 366, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblUnidadeDeMedida))))))
							.addGap(12))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 1332, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblFiltros)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNomeDoProduto)
								.addComponent(lblUnidadeDeMedida))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(comboBox)
								.addComponent(textField, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblFornecedor)
								.addComponent(lblCdigo))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
								.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
								.addComponent(textField_1, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)))
						.addComponent(chckbxItensEmFalta))
					.addGap(29)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAlterarRegistro, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnExcluirRegistro, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))))
					.addGap(12))
		);
		
		JLabel lblQtdeTotalDe = new JLabel("Qtde total de itens em estoque:");
		lblQtdeTotalDe.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		JLabel lblCustoTotalDe = new JLabel("Custo total de itens em estoque:");
		lblCustoTotalDe.setFont(new Font("Dialog", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCustoTotalDe, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblQtdeTotalDe, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(202, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblQtdeTotalDe)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCustoTotalDe)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
