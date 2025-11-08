package br.com.projetolpooii.view;

import br.com.projetolpooii.controller.LocacaoControlador;
import br.com.projetolpooii.controller.VeiculoControlador;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.view.modelo.TabelaVeiculosLocadosModelo;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Window;
import java.sql.SQLException;

public class TelaDevolucao extends JDialog {

    private final VeiculoControlador veiculoControlador;
    private final LocacaoControlador locacaoControlador;
    private final TabelaVeiculosLocadosModelo tabelaModelo = new TabelaVeiculosLocadosModelo();

    public TelaDevolucao(Window janelaPai, ContextoAplicacao contexto) {
        super(janelaPai, "Devolução de veículos", ModalityType.APPLICATION_MODAL);
        this.veiculoControlador = contexto.getVeiculoControlador();
        this.locacaoControlador = contexto.getLocacaoControlador();
        configurarComponentes();
        carregarVeiculosLocados();
        setSize(900, 450);
        setLocationRelativeTo(janelaPai);
    }

    private void configurarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(8, 8));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTable tabela = new JTable(tabelaModelo);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JButton botaoDevolver = new JButton("Devolver veículo selecionado");
        botaoDevolver.addActionListener(e -> devolverVeiculo(tabela));

        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(botaoDevolver, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private void carregarVeiculosLocados() {
        try {
            tabelaModelo.atualizarDados(veiculoControlador.listarLocados());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível listar os veículos locados.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void devolverVeiculo(JTable tabela) {
        int linha = tabela.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo.");
            return;
        }
        Veiculo veiculo = tabelaModelo.obterVeiculo(linha);
        try {
            locacaoControlador.devolverVeiculo(veiculo);
            JOptionPane.showMessageDialog(this, "Veículo devolvido com sucesso.");
            carregarVeiculosLocados();
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao devolver veículo.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
