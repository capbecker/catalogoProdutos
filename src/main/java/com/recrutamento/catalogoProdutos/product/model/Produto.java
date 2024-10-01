package com.recrutamento.catalogoProdutos.product.model;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Table
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Name is required")
    private String nome;
    private LocalDate validade;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double preco;

    public Produto() {
    }

    public Produto(Long id, String nome, LocalDate validade, Double preco) {
        this.id = id;
        this.nome = nome;
        this.validade = validade;
        this.preco = preco;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
