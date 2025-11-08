package br.com.projetolpooii.model;

import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;

import java.util.Calendar;

public interface VeiculoI {

    void locar(int dias, Calendar data, Cliente cliente);

    void vender();

    void devolver();

    Estado getEstado();

    Marca getMarca();

    Categoria getCategoria();

    Locacao getLocacao();

    String getPlaca();

    int getAno();

    double getValorParaVenda();

    double getValorDiariaLocacao();
}
