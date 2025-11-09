package br.com.projetolpooii.view;

import br.com.projetolpooii.controller.VeiculoControlador;
import br.com.projetolpooii.controller.VendaControlador;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.TipoVeiculo;
import br.com.projetolpooii.util.FormatadorMonetario;
import br.com.projetolpooii.view.modelo.TabelaVeiculosVendaModelo;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.SQLException;

public class TelaVenda extends JDialog {

    private final VeiculoControlador veiculoControlador;
    private final VendaControlador vendaControlador;

    private final TabelaVeiculosVendaModelo tabelaModelo = new TabelaVeiculosVendaModelo();
    private JTable tabelaVeiculos;
    private final JTextField campoMarca = new JTextField();
    private final JComboBox<Categoria> comboCategoria = new JComboBox<>();
    private final JComboBox<TipoVeiculo> comboTipo = new JComboBox<>();

    public TelaVenda(Window janelaPai, ContextoAplicacao contexto) {
        super(janelaPai, "Venda de veículos", ModalityType.APPLICATION_MODAL);
        this.veiculoControlador = contexto.getVeiculoControlador();
        this.vendaControlador = contexto.getVendaControlador();
        configurarComponentes();
        inicializarCombos();
        carregarVeiculos();
        setSize(800, 450);
        setLocationRelativeTo(janelaPai);
    }

    private void configurarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(8, 8));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        painelPrincipal.add(criarPainelFiltros(), BorderLayout.NORTH);
        tabelaVeiculos = criarTabela();
        painelPrincipal.add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelAcoes(), BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridLayout(3, 3, 5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Filtros"));

        painel.add(TelaUtil.rotulo("Marca"));
        painel.add(TelaUtil.rotulo("Categoria"));
        painel.add(TelaUtil.rotulo("Tipo"));
        painel.add(campoMarca);
        painel.add(comboCategoria);
        painel.add(comboTipo);

        JButton botaoFiltrar = new JButton("Filtrar");
        botaoFiltrar.addActionListener(e -> carregarVeiculos());
        painel.add(new JLabel());
        painel.add(botaoFiltrar);
        painel.add(new JLabel());

        return painel;
    }

    private JTable criarTabela() {
        JTable tabela = new JTable(tabelaModelo);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return tabela;
    }

    private JPanel criarPainelAcoes() {
        JPanel painel = new JPanel();
        JButton botaoVender = new JButton("Realizar venda");
        botaoVender.addActionListener(e -> realizarVenda());
        painel.add(botaoVender);
        return painel;
    }

    private void carregarVeiculos() {
        try {
            String marca = campoMarca.getText().trim();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
            TipoVeiculo tipo = (TipoVeiculo) comboTipo.getSelectedItem();
            tabelaModelo.atualizarDados(veiculoControlador.listarDisponiveisParaVenda(
                    marca.isEmpty() ? null : marca,
                    categoria,
                    tipo
            ));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível carregar os veículos disponíveis.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarVenda() {
        int linha = tabelaVeiculos.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo.");
            return;
        }
        Veiculo veiculo = tabelaModelo.obterVeiculo(linha);
        try {
            double valorVenda = vendaControlador.venderVeiculo(veiculo);
            JOptionPane.showMessageDialog(this,
                    "Venda registrada no valor de " + FormatadorMonetario.formatar(valorVenda));
            carregarVeiculos();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao realizar venda.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarCombos() {
        comboCategoria.addItem(null);
        for (Categoria categoria : Categoria.values()) {
            comboCategoria.addItem(categoria);
        }
        comboCategoria.setRenderer(new TextoOpcionalRenderer("Categoria"));

        comboTipo.addItem(null);
        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            comboTipo.addItem(tipo);
        }
        comboTipo.setRenderer(new TextoOpcionalRenderer("Tipo"));
    }
}
