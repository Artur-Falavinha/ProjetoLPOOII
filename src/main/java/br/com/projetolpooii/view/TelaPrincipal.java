package br.com.projetolpooii.view;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.awt.Dimension;

public class TelaPrincipal extends JFrame {

    private final ContextoAplicacao contexto;

    public TelaPrincipal(ContextoAplicacao contexto) {
        super("Locadora de Veículos - Projeto LPOOII");
        this.contexto = contexto;
        configurarJanela();
        criarMenu();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
    }

    private void criarMenu() {
        JMenuBar barra = new JMenuBar();

        JMenu menuCadastros = new JMenu("Cadastros");
        JMenuItem itemClientes = new JMenuItem("Clientes");
        itemClientes.addActionListener(e -> new TelaClientes(this, contexto.getClienteControlador()).setVisible(true));
        JMenuItem itemVeiculos = new JMenuItem("Veículos");
        itemVeiculos.addActionListener(e -> new TelaCadastroVeiculo(this, contexto.getVeiculoControlador()).setVisible(true));
        menuCadastros.add(itemClientes);
        menuCadastros.add(itemVeiculos);

        JMenu menuOperacoes = new JMenu("Operações");
        JMenuItem itemLocacao = new JMenuItem("Locar veículo");
        itemLocacao.addActionListener(e -> new TelaLocacao(this, contexto).setVisible(true));
        JMenuItem itemDevolucao = new JMenuItem("Devolver veículo");
        itemDevolucao.addActionListener(e -> new TelaDevolucao(this, contexto).setVisible(true));
        JMenuItem itemVenda = new JMenuItem("Vender veículo");
        itemVenda.addActionListener(e -> new TelaVenda(this, contexto).setVisible(true));
        menuOperacoes.add(itemLocacao);
        menuOperacoes.add(itemDevolucao);
        menuOperacoes.add(itemVenda);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        itemSobre.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Sistema de locação de veículos desenvolvido para a disciplina LPOO II.",
                "Sobre",
                JOptionPane.INFORMATION_MESSAGE));
        menuAjuda.add(itemSobre);

        barra.add(menuCadastros);
        barra.add(menuOperacoes);
        barra.add(menuAjuda);

        setJMenuBar(barra);
    }
}
