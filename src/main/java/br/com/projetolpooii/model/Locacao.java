package br.com.projetolpooii.model;

import java.util.Calendar;

public class Locacao {

    private Integer id;
    private int dias;
    private double valor;
    private Calendar data;
    private Cliente cliente;

    public Locacao(Integer id, int dias, double valor, Calendar data, Cliente cliente) {
        this.id = id;
        this.dias = dias;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
    }

    public Locacao(int dias, double valor, Calendar data, Cliente cliente) {
        this(null, dias, valor, data, cliente);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDias() {
        return dias;
    }

    public double getValor() {
        return valor;
    }

    public Calendar getData() {
        return data;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
