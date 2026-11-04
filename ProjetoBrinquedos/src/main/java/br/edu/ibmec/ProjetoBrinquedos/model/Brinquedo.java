package br.edu.ibmec.ProjetoBrinquedos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Brinquedo
{
    @id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nome;
    private String tipo;
    private int classificacao;
    private String tamanho;
    private double preco;

    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public String getTipo()
    {
        return tipo;
    }
    public void setTipo(String tipo)
    {
        this.tipo = tipo;
    }
    public int getClassificacao()
    {
        return classificacao;
    }
    public void setClassificacao(int classificacao)
    {
        this.classificacao = classificacao;
    }
    public String getTamanho()
    {
        return tamanho;
    }
    public void setTamanho(String tamanho)
    {
        this.tamanho = tamanho;
    }
    public double getPreco()
    {
        return preco;
    }
    public void setPreco(double preco)
    {
        this.preco = preco;
    }
}
