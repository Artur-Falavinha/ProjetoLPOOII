package br.com.projetolpooii.model;

// Classe específica pra automóveis
import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;
import br.com.projetolpooii.model.enums.ModeloAutomovel;

public class Automovel extends Veiculo {

    private ModeloAutomovel modelo;

    public Automovel(Integer id,
                     Marca marca,
                     Estado estado,
                     Categoria categoria,
                     double valorDeCompra,
                     String placa,
                     int ano,
                     ModeloAutomovel modelo) {
        super(id, marca, estado, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    public Automovel(Marca marca,
                     Estado estado,
                     Categoria categoria,
                     double valorDeCompra,
                     String placa,
                     int ano,
                     ModeloAutomovel modelo) {
        this(null, marca, estado, categoria, valorDeCompra, placa, ano, modelo);
    }

    public ModeloAutomovel getModelo() {
        return modelo;
    }

    // Valor da diária varia conforme a categoria do carro
    @Override
    public double getValorDiariaLocacao() {
        return switch (getCategoria()) {
            case POPULAR -> 100.0;
            case INTERMEDIARIO -> 300.0;
            case LUXO -> 450.0;
        };
    }
}
