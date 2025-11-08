package br.com.projetolpooii.model;

import java.util.Objects;

public class Cliente {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;

    public Cliente(Integer id, String nome, String sobrenome, String rg, String cpf, String endereco) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
    }

    public Cliente(String nome, String sobrenome, String rg, String cpf, String endereco) {
        this(null, nome, sobrenome, rg, cpf, endereco);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getRg() {
        return rg;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return nome + " " + sobrenome;
    }
}
