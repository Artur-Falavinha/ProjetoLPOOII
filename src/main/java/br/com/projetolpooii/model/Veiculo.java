package br.com.projetolpooii.model;

// Classe abstrata base pra todos os tipos de veículos
import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;

import java.util.Calendar;

public abstract class Veiculo implements VeiculoI {

    // Atributos básicos de qualquer veículo
    private Integer id;
    private Marca marca;
    private Estado estado;
    private Locacao locacao;
    private Categoria categoria;
    private double valorDeCompra;
    private String placa;
    private int ano;

    protected Veiculo(Integer id,
                      Marca marca,
                      Estado estado,
                      Categoria categoria,
                      double valorDeCompra,
                      String placa,
                      int ano) {
        this.id = id;
        this.marca = marca;
        this.estado = estado;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
    }

    protected Veiculo(Marca marca,
                      Estado estado,
                      Categoria categoria,
                      double valorDeCompra,
                      String placa,
                      int ano) {
        this(null, marca, estado, categoria, valorDeCompra, placa, ano);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Estado getEstado() {
        return estado;
    }

    protected void setEstado(Estado estado) {
        this.estado = estado;
    }

    @Override
    public Marca getMarca() {
        return marca;
    }

    @Override
    public Categoria getCategoria() {
        return categoria;
    }

    protected void setLocacao(Locacao locacao) {
        this.locacao = locacao;
    }

    public void atribuirLocacaoExistente(Locacao locacao) {
        this.locacao = locacao;
    }

    @Override
    public Locacao getLocacao() {
        return locacao;
    }

    @Override
    public String getPlaca() {
        return placa;
    }

    @Override
    public int getAno() {
        return ano;
    }

    public double getValorDeCompra() {
        return valorDeCompra;
    }

    // Método pra locar o veículo pra um cliente
    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        // Validações básicas antes de locar
        if (estado == Estado.VENDIDO) {
            throw new IllegalStateException("Veículo vendido não pode ser locado");
        }
        if (estado == Estado.LOCADO) {
            throw new IllegalStateException("Veículo já está locado");
        }
        // Calcula o valor total e cria a locação
        double valor = getValorDiariaLocacao() * dias;
        Locacao novaLocacao = new Locacao(dias, valor, data, cliente);
        setLocacao(novaLocacao);
        setEstado(Estado.LOCADO);
    }

    // Vende o veículo (não pode tá locado)
    @Override
    public void vender() {
        if (estado == Estado.LOCADO) {
            throw new IllegalStateException("Veículo locado precisa ser devolvido antes da venda");
        }
        setLocacao(null);
        setEstado(Estado.VENDIDO);
    }

    // Devolve o veículo e deixa ele disponível de novo
    @Override
    public void devolver() {
        if (estado != Estado.LOCADO) {
            throw new IllegalStateException("Somente veículos locados podem ser devolvidos");
        }
        setLocacao(null);
        setEstado(Estado.DISPONIVEL);
    }

    // Calcula o valor de venda baseado na depreciação (15% ao ano)
    @Override
    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idade = Math.max(0, anoAtual - this.ano);
        // Deprecia 15% por ano mas nunca fica abaixo de 10% do valor original
        double valorCalculado = valorDeCompra - idade * 0.15 * valorDeCompra;
        double minimo = valorDeCompra * 0.10;
        if (valorCalculado < minimo) {
            return minimo;
        }
        return valorCalculado;
    }
}
