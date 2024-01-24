package br.com.mercadocancelier.views.components;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.mercadocancelier.views.fornecedor.TelaConsultaDeFornecedor;
import br.com.mercadocancelier.views.produtos.TelaConsultaDeProdutos;
import br.com.mercadocancelier.views.vendas.TelaConsultaDeVenda;
import br.com.mercadocancelier.views.vendas.TelaDeVenda;

@Component
public class MenuBar extends JMenuBar {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Lazy
    private TelaConsultaDeProdutos telaConsultaDeProdutos;

    @Autowired
    @Lazy
    private TelaDeVenda telaDeVenda;

    @Autowired
    @Lazy
    private TelaConsultaDeFornecedor telaConsultaDeFornecedor;
    
    @Autowired
    @Lazy
    private TelaConsultaDeVenda telaConsultaDeVenda;

    public MenuBar() {
        initComponents();
    }

    private void initComponents() {
        setMargin(new Insets(0, 10, 0, 10));
        setFont(new Font("Segoe UI", Font.PLAIN, 17));
        setBorderPainted(false);

        add(createMenuCadastros());
        add(createMenuMovimentacao());
        add(createMenuEstoque());
        add(createMenuVenda());
        add(createMenuMercado());
    }

    private JMenu createMenuCadastros() {
        JMenu menuCadastros = createMenu("Cadastros");
        menuCadastros.add(createMenuItem("Produtos", e -> telaConsultaDeProdutos.abrirTela()));
        menuCadastros.add(createMenuItem("Fornecedor", e -> telaConsultaDeFornecedor.setVisible(true)));
        return menuCadastros;
    }

    private JMenu createMenuMovimentacao() {
        JMenu menuMovimentacao = createMenu("Movimentação");
        return menuMovimentacao;
    }

    private JMenu createMenuEstoque() {
        JMenu menuEstoque = createMenu("Estoque");
        return menuEstoque;
    }

    private JMenu createMenuVenda() {
        JMenu menuVenda = createMenu("Venda");
        menuVenda.add(createMenuItem("Nova venda", e -> telaDeVenda.setVisible(true)));
        menuVenda.add(createMenuItem("Relatório de venda", e -> telaConsultaDeVenda.setVisible(true)));
        return menuVenda;
    }

    private JMenu createMenuMercado() {
        JMenu menuMercado = createMenu("Mercado");
        return menuMercado;
    }

    private JMenu createMenu(String title) {
        JMenu menu = new JMenu(title);
        menu.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        return menu;
    }

    private JMenuItem createMenuItem(String title, ActionListener actionListener) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(actionListener);
        return menuItem;
    }
}
