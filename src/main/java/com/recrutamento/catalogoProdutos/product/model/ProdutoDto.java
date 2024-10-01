package com.recrutamento.catalogoProdutos.product.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

public class ProdutoDto {

    @NotEmpty(message = "Name is required")
    private String nome;
    private LocalDate validade;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double preco;

    public ProdutoDto() {
    }

    public ProdutoDto(String nome, LocalDate validade, Double preco) {
        this.nome = nome;
        this.validade = validade;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public @NotNull(message = "Price is required") @Positive(message = "Price must be greater than 0") Double getPreco() {
        return preco;
    }

    public void setPreco(@NotNull(message = "Price is required") @Positive(message = "Price must be greater than 0") Double preco) {
        this.preco = preco;
    }
}
