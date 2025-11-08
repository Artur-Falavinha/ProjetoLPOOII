package br.com.projetolpooii.view.modelo;

import br.com.projetolpooii.model.Automovel;
import br.com.projetolpooii.model.Motocicleta;
import br.com.projetolpooii.model.Van;
import br.com.projetolpooii.model.Veiculo;
import br.com.projetolpooii.util.DataUtil;
import br.com.projetolpooii.util.FormatadorMonetario;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TabelaVeiculosLocadosModelo extends AbstractTableModel {

    private final List<Veiculo> veiculos = new ArrayList<>();
    private final String[] colunas = {"Cliente", "Placa", "Marca", "Modelo", "Ano", "Data locação", "Valor diária", "Dias", "Valor total"};

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
            case 0 -> veiculo.getLocacao().getCliente().toString();
            case 1 -> veiculo.getPlaca();
            case 2 -> veiculo.getMarca().name();
            case 3 -> obterModeloTexto(veiculo);
            case 4 -> veiculo.getAno();
            case 5 -> DataUtil.formatar(veiculo.getLocacao().getData());
            case 6 -> FormatadorMonetario.formatar(veiculo.getValorDiariaLocacao());
            case 7 -> veiculo.getLocacao().getDias();
            case 8 -> FormatadorMonetario.formatar(veiculo.getLocacao().getValor());
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
