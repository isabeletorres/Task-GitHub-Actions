package com.vemser.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProdutoResponse {

    private String nome;
    private Integer preco;
    private String descricao;
    private Integer quantidade;
    @JsonProperty("_id")
    private String id;

    public ProdutoResponse() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPreco() {
        return preco;
    }

    public void setPreco(Integer preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProdutoResponse{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", id='" + id + '\'' +
                '}';
    }
}
