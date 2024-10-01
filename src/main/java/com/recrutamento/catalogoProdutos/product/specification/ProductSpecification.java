package com.recrutamento.catalogoProdutos.product.specification;

import com.recrutamento.catalogoProdutos.product.model.Produto;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Produto> nome(String nome) {
        return (root, criteriaQuery, criteriaBuilder) ->
                nome == null ? null : criteriaBuilder.like(root.get("nome"), "%"+nome+"%");
    }

    public static Specification<Produto> greaterOrEqualThan(Double min_price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                min_price == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("preco"), min_price);
    }

    public static Specification<Produto> lessOrEqualThan(Double max_price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                max_price == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("preco"), max_price);
    }
}
