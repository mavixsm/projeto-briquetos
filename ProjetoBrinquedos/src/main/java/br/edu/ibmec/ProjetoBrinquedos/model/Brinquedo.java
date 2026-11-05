package br.edu.ibmec.ProjetoBrinquedos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table (name = "TDS_TB_Brinquedos")
public class Brinquedo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 2, max = 100)
    private String nome;

    @NotBlank
    private String tipo;

    @Min(0)
    @Max(15)
    private int classificacao;

    @NotBlank
    @Pattern(regexp = "P|M|G")
    private String tamanho;

    @NotNull
    @Positive
    private double preco;

    public Brinquedo() {}

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
