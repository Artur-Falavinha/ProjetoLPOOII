package br.com.projetolpooii.view;

// Janela principal do sistema com o menu
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Dimension;

public class TelaPrincipal extends JFrame {

    private final ContextoAplicacao contexto;

    public TelaPrincipal(ContextoAplicacao contexto) {
        super("Locadora de Veículos - Projeto LPOOII");
        this.contexto = contexto;
        configurarJanela();
        criarMenu();
    }

    // Configurações básicas da janela
    private void configurarJanela() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(900, 600));
        setLocationRelativeTo(null); // centraliza na tela
    }

    // Monta a barra de menu com todas as opções do sistema
    private void criarMenu() {
        JMenuBar barra = new JMenuBar();

        // Menu de cadastros (clientes e veículos)
        JMenu menuCadastros = new JMenu("Cadastros");
        JMenuItem itemClientes = new JMenuItem("Clientes");
        itemClientes.addActionListener(e -> new TelaClientes(this, contexto.getClienteControlador()).setVisible(true));
        JMenuItem itemVeiculos = new JMenuItem("Veículos");
        itemVeiculos.addActionListener(e -> new TelaCadastroVeiculo(this, contexto.getVeiculoControlador()).setVisible(true));
        menuCadastros.add(itemClientes);
        menuCadastros.add(itemVeiculos);

        // Menu de operações (locar, devolver, vender)
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

        barra.add(menuCadastros);
        barra.add(menuOperacoes);

        setJMenuBar(barra);
    }
}
