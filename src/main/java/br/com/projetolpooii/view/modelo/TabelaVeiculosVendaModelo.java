package br.com.projetolpooii.view.modelo;

import br.com.projetolpooii.model.Automovel;
import br.com.projetolpooii.model.Motocicleta;
import br.com.projetolpooii.model.Van;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.util.FormatadorMonetario;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TabelaVeiculosVendaModelo extends AbstractTableModel {

    private final List<Veiculo> veiculos = new ArrayList<>();
    private final String[] colunas = {"Placa", "Marca", "Modelo", "Ano", "Preço de venda"};

    public void atualizarDados(List<Veiculo> novosVeiculos) {
        veiculos.clear();
        veiculos.addAll(novosVeiculos);
        fireTableDataChanged();
    }

    public Veiculo obterVeiculo(int linha) {
        return veiculos.get(linha);
    }

    @Override
    public int getRowCount() {
        return veiculos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Veiculo veiculo = veiculos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> veiculo.getPlaca();
            case 1 -> veiculo.getMarca().name();
            case 2 -> obterModeloTexto(veiculo);
            case 3 -> veiculo.getAno();
            case 4 -> FormatadorMonetario.formatar(veiculo.getValorParaVenda());
            default -> "";
        };
    }

    private String obterModeloTexto(Veiculo veiculo) {
        if (veiculo instanceof Automovel automovel) {
            return automovel.getModelo().name();
        }
        if (veiculo instanceof Motocicleta moto) {
            return moto.getModelo().name();
        }
        if (veiculo instanceof Van van) {
            return van.getModelo().name();
        }
        return "-";
    }
}
