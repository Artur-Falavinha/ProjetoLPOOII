package br.com.projetolpooii.view;

import br.com.projetolpooii.controller.ClienteControlador;
import br.com.projetolpooii.model.Cliente;
import br.com.projetolpooii.view.modelo.TabelaClientesModelo;

import javax.swing.JButton;
import javax.swing.JDialog;
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

public class TelaClientes extends JDialog {

    private final ClienteControlador clienteControlador;
    private final TabelaClientesModelo tabelaModelo = new TabelaClientesModelo();

    private Cliente clienteEmEdicao;

    private final JTextField campoNome = new JTextField();
    private final JTextField campoSobrenome = new JTextField();
    private final JTextField campoRg = new JTextField();
    private final JTextField campoCpf = new JTextField();
    private final JTextField campoEndereco = new JTextField();

    public TelaClientes(Window janelaPai, ClienteControlador clienteControlador) {
        super(janelaPai, "Cadastro de clientes", ModalityType.APPLICATION_MODAL);
        this.clienteControlador = clienteControlador;
        configurarComponentes();
        carregarClientes();
        setSize(800, 500);
        setLocationRelativeTo(janelaPai);
    }

    private void configurarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        painelFormulario.add(TelaUtil.rotulo("Nome"));
        painelFormulario.add(campoNome);
        painelFormulario.add(TelaUtil.rotulo("Sobrenome"));
        painelFormulario.add(campoSobrenome);
        painelFormulario.add(TelaUtil.rotulo("RG"));
        painelFormulario.add(campoRg);
        painelFormulario.add(TelaUtil.rotulo("CPF"));
        painelFormulario.add(campoCpf);
        painelFormulario.add(TelaUtil.rotulo("Endereço"));
        painelFormulario.add(campoEndereco);

        JPanel painelBotoes = new JPanel();
        JButton botaoNovo = new JButton("Novo");
        botaoNovo.addActionListener(e -> prepararNovo());
        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(e -> salvarCliente());
        JButton botaoExcluir = new JButton("Excluir");
        botaoExcluir.addActionListener(e -> excluirCliente());
        painelBotoes.add(botaoNovo);
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoExcluir);

        JTable tabela = new JTable(tabelaModelo);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int linha = tabela.getSelectedRow();
                if (linha >= 0) {
                    // Ao clicar na linha trazemos os dados para edição rápida.
                    carregarClienteNaTela(tabelaModelo.obterCliente(linha));
                }
            }
        });

        painelPrincipal.add(painelFormulario, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private void carregarClientes() {
        try {
            tabelaModelo.atualizarDados(clienteControlador.listarTodos());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Não foi possível carregar os clientes cadastrados.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void prepararNovo() {
        clienteEmEdicao = null;
        campoNome.setText("");
        campoSobrenome.setText("");
        campoRg.setText("");
        campoCpf.setText("");
        campoEndereco.setText("");
        campoNome.requestFocus();
    }

    private void salvarCliente() {
        if (campoNome.getText().isBlank() || campoSobrenome.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe nome e sobrenome.");
            return;
        }
        try {
            if (clienteEmEdicao == null) {
                clienteEmEdicao = new Cliente(
                        campoNome.getText().trim(),
                        campoSobrenome.getText().trim(),
                        campoRg.getText().trim(),
                        campoCpf.getText().trim(),
                        campoEndereco.getText().trim()
                );
            } else {
                clienteEmEdicao = new Cliente(
                        clienteEmEdicao.getId(),
                        campoNome.getText().trim(),
                        campoSobrenome.getText().trim(),
                        campoRg.getText().trim(),
                        campoCpf.getText().trim(),
                        campoEndereco.getText().trim()
                );
            }
            clienteControlador.salvar(clienteEmEdicao);
            JOptionPane.showMessageDialog(this, "Cliente salvo com sucesso.");
            prepararNovo();
            carregarClientes();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocorreu um problema ao salvar o cliente.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        if (clienteEmEdicao == null || clienteEmEdicao.getId() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
            return;
        }
        int resposta = JOptionPane.showConfirmDialog(this,
                "Confirma exclusão do cliente selecionado?",
                "Confirmação",
                JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            try {
                clienteControlador.excluir(clienteEmEdicao);
                JOptionPane.showMessageDialog(this, "Cliente removido.");
                prepararNovo();
                carregarClientes();
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Não foi possível excluir o cliente.\nDetalhe: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void carregarClienteNaTela(Cliente cliente) {
        this.clienteEmEdicao = cliente;
        campoNome.setText(cliente.getNome());
        campoSobrenome.setText(cliente.getSobrenome());
        campoRg.setText(cliente.getRg());
        campoCpf.setText(cliente.getCpf());
        campoEndereco.setText(cliente.getEndereco());
    }
}
