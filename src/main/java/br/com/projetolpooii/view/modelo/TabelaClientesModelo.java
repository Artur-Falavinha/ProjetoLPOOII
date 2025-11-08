package br.com.projetolpooii.view.modelo;

import br.com.projetolpooii.model.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TabelaClientesModelo extends AbstractTableModel {

    private final List<Cliente> clientes = new ArrayList<>();
    private final String[] colunas = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    public void atualizarDados(List<Cliente> novosClientes) {
        clientes.clear();
        clientes.addAll(novosClientes);
        fireTableDataChanged();
    }

    public Cliente obterCliente(int linha) {
        return clientes.get(linha);
    }

    @Override
    public int getRowCount() {
        return clientes.size();
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
        Cliente cliente = clientes.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> cliente.getNome();
            case 1 -> cliente.getSobrenome();
            case 2 -> cliente.getRg();
            case 3 -> cliente.getCpf();
            case 4 -> cliente.getEndereco();
            default -> "";
        };
    }
}
