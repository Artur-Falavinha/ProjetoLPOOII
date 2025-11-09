package br.com.projetolpooii.view;

import br.com.projetolpooii.controller.ClienteControlador;
import br.com.projetolpooii.controller.LocacaoControlador;
import br.com.projetolpooii.controller.VeiculoControlador;
import br.com.projetolpooii.model.Cliente;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.TipoVeiculo;
import br.com.projetolpooii.util.DataUtil;
import br.com.projetolpooii.view.modelo.TabelaVeiculosDisponiveisModelo;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TelaLocacao extends JDialog {

    private final ClienteControlador clienteControlador;
    private final VeiculoControlador veiculoControlador;
    private final LocacaoControlador locacaoControlador;

    private final TabelaVeiculosDisponiveisModelo tabelaModelo = new TabelaVeiculosDisponiveisModelo();
    private JTable tabelaVeiculos;

    private final JComboBox<Cliente> comboCliente = new JComboBox<>();
    private final JTextField campoFiltroCliente = new JTextField();
    private final JTextField campoFiltroMarca = new JTextField();
    private final JComboBox<Categoria> comboCategoria = new JComboBox<>();
    private final JComboBox<TipoVeiculo> comboTipo = new JComboBox<>();
    private final JSpinner campoDias = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
    private final JSpinner campoData = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));

    public TelaLocacao(Window janelaPai, ContextoAplicacao contexto) {
        super(janelaPai, "Locação de veículos", ModalityType.APPLICATION_MODAL);
        this.clienteControlador = contexto.getClienteControlador();
        this.veiculoControlador = contexto.getVeiculoControlador();
        this.locacaoControlador = contexto.getLocacaoControlador();
        configurarComponentes();
        inicializarCombosFiltro();
        carregarClientes("");
        carregarVeiculos();
        setSize(900, 500);
        setLocationRelativeTo(janelaPai);
    }

    private void configurarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(8, 8));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        painelPrincipal.add(criarPainelFiltros(), BorderLayout.NORTH);
        tabelaVeiculos = criarTabela();
        painelPrincipal.add(new JScrollPane(tabelaVeiculos), BorderLayout.CENTER);
        painelPrincipal.add(criarPainelConfirmacao(), BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private JPanel criarPainelFiltros() {
        JPanel painelFiltros = new JPanel(new GridLayout(3, 4, 5, 5));
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        JButton botaoPesquisarCliente = new JButton("Buscar cliente");
        botaoPesquisarCliente.addActionListener(e -> carregarClientes(campoFiltroCliente.getText()));

        painelFiltros.add(TelaUtil.rotulo("Cliente"));
        painelFiltros.add(comboCliente);
        painelFiltros.add(campoFiltroCliente);
        painelFiltros.add(botaoPesquisarCliente);

        JButton botaoFiltrarVeiculo = new JButton("Aplicar filtro de veículos");
        botaoFiltrarVeiculo.addActionListener(e -> carregarVeiculos());

        painelFiltros.add(TelaUtil.rotulo("Marca"));
        painelFiltros.add(campoFiltroMarca);
        painelFiltros.add(comboCategoria);
        painelFiltros.add(comboTipo);
        painelFiltros.add(new JLabel());
        painelFiltros.add(botaoFiltrarVeiculo);

        return painelFiltros;
    }

    private JTable criarTabela() {
        JTable tabela = new JTable(tabelaModelo);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        return tabela;
    }

    private JPanel criarPainelConfirmacao() {
        JPanel painel = new JPanel();
        painel.setBorder(BorderFactory.createTitledBorder("Informações da locação"));

        campoData.setEditor(new JSpinner.DateEditor(campoData, "dd/MM/yyyy"));

        painel.add(TelaUtil.rotulo("Dias"));
        painel.add(campoDias);
        painel.add(TelaUtil.rotulo("Data da locação"));
        painel.add(campoData);

        JButton botaoLocar = new JButton("Locar veículo selecionado");
        botaoLocar.addActionListener(e -> realizarLocacao());
        painel.add(botaoLocar);
        return painel;
    }

    private void carregarClientes(String termo) {
        try {
            List<Cliente> clientes = termo == null || termo.isBlank()
                    ? clienteControlador.listarTodos()
                    : clienteControlador.buscarPorTermo(termo);
            comboCliente.removeAllItems();
            for (Cliente cliente : clientes) {
                comboCliente.addItem(cliente);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao buscar clientes.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarVeiculos() {
        try {
            String filtroMarca = campoFiltroMarca.getText().trim();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
            TipoVeiculo tipo = (TipoVeiculo) comboTipo.getSelectedItem();
            tabelaModelo.atualizarDados(veiculoControlador.listarDisponiveisParaLocacao(
                    filtroMarca.isEmpty() ? null : filtroMarca,
                    categoria,
                    tipo
            ));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível carregar os veículos.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void realizarLocacao() {
        if (comboCliente.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.");
            return;
        }
        int linhaSelecionada = tabelaVeiculos.getSelectedRow();
        if (linhaSelecionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo na tabela.");
            return;
        }

        Cliente cliente = (Cliente) comboCliente.getSelectedItem();
        Veiculo veiculo = tabelaModelo.obterVeiculo(linhaSelecionada);
        int dias = (int) campoDias.getValue();
        Date data = (Date) campoData.getValue();
        Calendar calendario = DataUtil.paraCalendar(data);

        try {
            locacaoControlador.locarVeiculo(veiculo, cliente, dias, calendario);
            JOptionPane.showMessageDialog(this, "Locação registrada com sucesso.");
            carregarVeiculos();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocorreu um erro ao locar o veículo.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inicializarCombosFiltro() {
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
