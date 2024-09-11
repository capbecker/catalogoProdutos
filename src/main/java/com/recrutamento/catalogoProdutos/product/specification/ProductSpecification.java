package com.recrutamento.catalogoProdutos.product.specification;

import com.recrutamento.catalogoProdutos.product.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    public static Specification<Product> name(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                name == null ? null : criteriaBuilder.like(root.get("name"), "%"+name+"%");
    }

    public static Specification<Product> description(String description) {
        return (root, criteriaQuery, criteriaBuilder) ->
                description == null ? null : criteriaBuilder.like(root.get("description"), "%"+description+"%");
    }

    public static Specification<Product> greaterOrEqualThan(Double min_price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                min_price == null ? null : criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min_price);
    }

    public static Specification<Product> lessOrEqualThan(Double max_price) {
        return (root, criteriaQuery, criteriaBuilder) ->
                max_price == null ? null : criteriaBuilder.lessThanOrEqualTo(root.get("price"), max_price);
    }
}
