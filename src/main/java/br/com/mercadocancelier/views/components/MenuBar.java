package br.com.mercadocancelier.views.components;

import java.awt.Font;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.views.fornecedor.TelaConsultaDeFornecedor;
import br.com.mercadocancelier.views.produtos.TelaConsultaDeProdutos;

@Component
public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = 1L;
    
    @Autowired
    @Lazy
    private TelaConsultaDeProdutos telaConsultaDeProdutos;
    

    @Autowired
    @Lazy
    private TelaConsultaDeFornecedor telaConsultaDeFornecedor;

    public MenuBar() {
        createMenus(
        		new JMenuBar(),
        		new JMenu(),
        		new JMenuItem(),
        		new JMenuItem(),
        		new JMenu(),
        		new JMenuItem(),
        		new JMenuItem(),
        		new JMenu(),
        		new JMenu(),
        		new JMenu()
        		);
    }

    private void createMenus(
    		JMenuBar menuBar,
    		JMenu menuCadastros,
    		JMenuItem menuItemProdutos,
    		JMenuItem menuItemFornecedor,
    		JMenu menuMovimentacao,
    		JMenuItem menuItemEntradas,
    		JMenuItem menuItemSaidas,
    		JMenu menuEstoque,
    		JMenu menuVenda,
    		JMenu menuMercado
    		) {
    	
    	menuBar = new JMenuBar();
		menuBar.setMargin(new Insets(0, 10, 0, 10));
		menuBar.setFont(new Font("Segoe UI", Font.PLAIN, 17));
		menuBar.setBorderPainted(false);
		
		menuCadastros = new JMenu("Cadastros");
		menuCadastros.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		menuBar.add(menuCadastros);
		
		menuItemProdutos = new JMenuItem("Produtos");
		menuItemProdutos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	telaConsultaDeProdutos.abrirTela();
            }
        });
		menuCadastros.add(menuItemProdutos);
		
		menuItemFornecedor = new JMenuItem("Fornecedor");
		menuItemFornecedor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	telaConsultaDeFornecedor.setVisible(true);
            }
        });
		menuCadastros.add(menuItemFornecedor);
		
		menuMovimentacao = new JMenu("Movimentação");
		menuMovimentacao.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		menuBar.add(menuMovimentacao);
		
		menuItemEntradas = new JMenuItem("Entradas");
		menuMovimentacao.add(menuItemEntradas);
		
		menuItemSaidas = new JMenuItem("Saidas");
		menuMovimentacao.add(menuItemSaidas);
		
		menuEstoque = new JMenu("Estoque");
		menuEstoque.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		menuBar.add(menuEstoque);
		
		menuVenda = new JMenu("Venda");
		menuVenda.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		menuBar.add(menuVenda);
		
		menuMercado = new JMenu("Mercado");
		menuMercado.setForeground(SystemColor.text);
		menuMercado.setBackground(SystemColor.textHighlight);
		menuMercado.setEnabled(false);
		menuMercado.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		menuMercado.setHorizontalAlignment(SwingConstants.CENTER);
		menuBar.add(menuMercado);
		
		add(menuBar);
        
    }
}
