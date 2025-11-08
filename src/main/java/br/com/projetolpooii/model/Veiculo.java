package br.com.projetolpooii.model;

import br.com.projetolpooii.model.enums.Categoria;
import br.com.projetolpooii.model.enums.Estado;
import br.com.projetolpooii.model.enums.Marca;

import java.util.Calendar;

public abstract class Veiculo implements VeiculoI {

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

    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        if (estado == Estado.VENDIDO) {
            throw new IllegalStateException("Veículo vendido não pode ser locado");
        }
        if (estado == Estado.LOCADO) {
            throw new IllegalStateException("Veículo já está locado");
        }
        double valor = getValorDiariaLocacao() * dias;
        Locacao novaLocacao = new Locacao(dias, valor, data, cliente);
        setLocacao(novaLocacao);
        setEstado(Estado.LOCADO);
    }

    @Override
    public void vender() {
        if (estado == Estado.LOCADO) {
            throw new IllegalStateException("Veículo locado precisa ser devolvido antes da venda");
        }
        setLocacao(null);
        setEstado(Estado.VENDIDO);
    }

    @Override
    public void devolver() {
        if (estado != Estado.LOCADO) {
            throw new IllegalStateException("Somente veículos locados podem ser devolvidos");
        }
        setLocacao(null);
        setEstado(Estado.DISPONIVEL);
    }

    @Override
    public double getValorParaVenda() {
        int anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        int idade = Math.max(0, anoAtual - this.ano);
        double valorCalculado = valorDeCompra - idade * 0.15 * valorDeCompra;
        double minimo = valorDeCompra * 0.10;
        if (valorCalculado < minimo) {
            return minimo;
        }
        return valorCalculado;
    }
}
