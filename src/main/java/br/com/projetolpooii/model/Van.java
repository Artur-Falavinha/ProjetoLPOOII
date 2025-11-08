package br.com.projetolpooii.model;

import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;
import br.com.projetolpooii.model.enums.ModeloVan;

public class Van extends Veiculo {

    private ModeloVan modelo;

    public Van(Integer id,
               Marca marca,
               Estado estado,
               Categoria categoria,
               double valorDeCompra,
               String placa,
               int ano,
               ModeloVan modelo) {
        super(id, marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public Van(Marca marca,
               Estado estado,
               Categoria categoria,
               double valorDeCompra,
               String placa,
               int ano,
               ModeloVan modelo) {
        this(null, marca, estado, categoria, valorDeCompra, placa, ano, modelo);
    }

    public ModeloVan getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        return switch (getCategoria()) {
            case POPULAR -> 200.0;
            case INTERMEDIARIO -> 400.0;
            case LUXO -> 600.0;
        };
    }
}
