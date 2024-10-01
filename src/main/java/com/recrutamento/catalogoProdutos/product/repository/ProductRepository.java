package com.recrutamento.catalogoProdutos.product.repository;

import com.recrutamento.catalogoProdutos.product.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

}
