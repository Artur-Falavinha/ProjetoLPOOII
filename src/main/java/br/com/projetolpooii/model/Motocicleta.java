package br.com.projetolpooii.model;

import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;
import br.com.projetolpooii.model.enums.ModeloMotocicleta;

public class Motocicleta extends Veiculo {

    private ModeloMotocicleta modelo;

    public Motocicleta(Integer id,
                       Marca marca,
                       Estado estado,
                       Categoria categoria,
                       double valorDeCompra,
                       String placa,
                       int ano,
                       ModeloMotocicleta modelo) {
        super(id, marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public Motocicleta(Marca marca,
                       Estado estado,
                       Categoria categoria,
                       double valorDeCompra,
                       String placa,
                       int ano,
                       ModeloMotocicleta modelo) {
        this(null, marca, estado, categoria, valorDeCompra, placa, ano, modelo);
    }

    public ModeloMotocicleta getModelo() {
        return modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        return switch (getCategoria()) {
            case POPULAR -> 70.0;
            case INTERMEDIARIO -> 200.0;
            case LUXO -> 350.0;
        };
    }
}
