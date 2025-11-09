package br.com.projetolpooii.view;

import br.com.projetolpooii.controller.VeiculoControlador;
import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;
import br.com.projetolpooii.model.enums.ModeloAutomovel;
import br.com.projetolpooii.model.enums.ModeloMotocicleta;
import br.com.projetolpooii.model.enums.ModeloVan;
import br.com.projetolpooii.model.enums.TipoVeiculo;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.sql.SQLException;
import java.text.NumberFormat;

public class TelaCadastroVeiculo extends JDialog {

    private final VeiculoControlador veiculoControlador;

    private final javax.swing.JComboBox<TipoVeiculo> comboTipo = new javax.swing.JComboBox<>(TipoVeiculo.values());
    private final javax.swing.JComboBox<Marca> comboMarca = new javax.swing.JComboBox<>(Marca.values());
    private final javax.swing.JComboBox<Estado> comboEstado = new javax.swing.JComboBox<>(Estado.values());
    private final javax.swing.JComboBox<Categoria> comboCategoria = new javax.swing.JComboBox<>(Categoria.values());
    private final javax.swing.JComboBox<String> comboModelo = new javax.swing.JComboBox<>();
    private final JFormattedTextField campoValorCompra;
    private final JFormattedTextField campoPlaca;
    private final JTextField campoAno = new JTextField();

    public TelaCadastroVeiculo(Window janelaPai, VeiculoControlador veiculoControlador) {
        super(janelaPai, "Cadastro de veículos", ModalityType.APPLICATION_MODAL);
        this.veiculoControlador = veiculoControlador;
        this.campoValorCompra = criarCampoMonetario();
        this.campoPlaca = criarCampoPlaca();
        configurarComponentes();
        atualizarModelos();
        setSize(600, 400);
        setLocationRelativeTo(janelaPai);
    }

    private void configurarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(8, 2, 8, 8));
        painelFormulario.setBorder(BorderFactory.createTitledBorder("Dados do veículo"));
        painelFormulario.add(TelaUtil.rotulo("Tipo"));
        painelFormulario.add(comboTipo);
        painelFormulario.add(TelaUtil.rotulo("Marca"));
        painelFormulario.add(comboMarca);
        painelFormulario.add(TelaUtil.rotulo("Estado"));
        painelFormulario.add(comboEstado);
        painelFormulario.add(TelaUtil.rotulo("Categoria"));
        painelFormulario.add(comboCategoria);
        painelFormulario.add(TelaUtil.rotulo("Modelo"));
        painelFormulario.add(comboModelo);
        painelFormulario.add(TelaUtil.rotulo("Valor de compra"));
        painelFormulario.add(campoValorCompra);
        painelFormulario.add(TelaUtil.rotulo("Placa"));
        painelFormulario.add(campoPlaca);
        painelFormulario.add(TelaUtil.rotulo("Ano"));
        painelFormulario.add(campoAno);

        comboTipo.addActionListener(e -> atualizarModelos());

        JPanel painelBotoes = new JPanel();
        JButton botaoSalvar = new JButton("Salvar veículo");
        botaoSalvar.addActionListener(e -> salvarVeiculo());
        JButton botaoLimpar = new JButton("Limpar");
        botaoLimpar.addActionListener(e -> limparFormulario());
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoLimpar);

        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private void atualizarModelos() {
        TipoVeiculo tipoSelecionado = (TipoVeiculo) comboTipo.getSelectedItem();
        if (tipoSelecionado == null) {
            return;
        }
        switch (tipoSelecionado) {
            case AUTOMOVEL -> comboModelo.setModel(new DefaultComboBoxModel<>(
                    textoModelos(ModeloAutomovel.values())));
            case MOTOCICLETA -> comboModelo.setModel(new DefaultComboBoxModel<>(
                    textoModelos(ModeloMotocicleta.values())));
            case VAN -> comboModelo.setModel(new DefaultComboBoxModel<>(
                    textoModelos(ModeloVan.values())));
        }
    }

    private String[] textoModelos(Enum<?>[] valores) {
        String[] textos = new String[valores.length];
        for (int i = 0; i < valores.length; i++) {
            textos[i] = valores[i].name();
        }
        return textos;
    }

    private void salvarVeiculo() {
        try {
            double valorCompra = ((Number) campoValorCompra.getValue()).doubleValue();
            int ano = Integer.parseInt(campoAno.getText());
            String placa = campoPlaca.getText().toUpperCase();

            TipoVeiculo tipo = (TipoVeiculo) comboTipo.getSelectedItem();
            Marca marca = (Marca) comboMarca.getSelectedItem();
            Estado estado = (Estado) comboEstado.getSelectedItem();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();
            String modeloTexto = (String) comboModelo.getSelectedItem();

            if (tipo == TipoVeiculo.AUTOMOVEL) {
                veiculoControlador.cadastrarAutomovel(marca, estado, categoria, valorCompra, placa, ano,
                        ModeloAutomovel.valueOf(modeloTexto));
            } else if (tipo == TipoVeiculo.MOTOCICLETA) {
                veiculoControlador.cadastrarMotocicleta(marca, estado, categoria, valorCompra, placa, ano,
                        ModeloMotocicleta.valueOf(modeloTexto));
            } else {
                veiculoControlador.cadastrarVan(marca, estado, categoria, valorCompra, placa, ano,
                        ModeloVan.valueOf(modeloTexto));
            }
            JOptionPane.showMessageDialog(this, "Veículo cadastrado com sucesso.");
            limparFormulario();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe um ano válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Ocorreu um erro ao salvar o veículo.\nDetalhe: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparFormulario() {
        campoValorCompra.setValue(0.0);
        campoAno.setText("");
        campoPlaca.setValue(null);
        comboTipo.setSelectedIndex(0);
        comboMarca.setSelectedIndex(0);
        comboEstado.setSelectedItem(Estado.DISPONIVEL);
        comboCategoria.setSelectedIndex(0);
        atualizarModelos();
    }

    private JFormattedTextField criarCampoMonetario() {
        NumberFormat formato = NumberFormat.getNumberInstance();
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        JFormattedTextField campo = new JFormattedTextField(formato);
        campo.setValue(0.0);
        return campo;
    }

    private JFormattedTextField criarCampoPlaca() {
        try {
            MaskFormatter mascara = new MaskFormatter("UUU-####");
            mascara.setPlaceholderCharacter('_');
            // A máscara já garante o padrão XXX-0000 exigido pelo professor.
            return new JFormattedTextField(mascara);
        } catch (java.text.ParseException ex) {
            throw new IllegalStateException("Não foi possível criar máscara para placa", ex);
        }
    }
}
