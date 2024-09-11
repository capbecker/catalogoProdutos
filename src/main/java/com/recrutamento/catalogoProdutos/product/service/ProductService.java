package com.recrutamento.catalogoProdutos.product.service;

import com.recrutamento.catalogoProdutos.product.model.Product;
import com.recrutamento.catalogoProdutos.product.repository.ProductRepository;
import com.recrutamento.catalogoProdutos.product.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    /**
     * Insere um produto com os dados de {product}
     *
     * @param product:  dados do produto a ser inserido
     *
     * @return: produto inserido
     */
    public Product insert(Product product) {
        Product productSaved = productRepository.save(product);
        return productSaved;
    }

    /**
     * Atualiza um produto com id {id} com os dados de {product}, caso não encontre, lança uma exception
     * Caso {product} tenha os campos name, description ou price como null, será mantido o valor antigo
     *
     * @param id:       id do produto a ser excluído
     * @param product:  dados do produto a ser atualizado
     *
     * @return: produto atualizado
     * @throws ResponseStatusException
     */
    public Product update(Long id, Product product) throws ResponseStatusException{
        Optional<Product> checkProduct = productRepository.findById(id);
        if (!checkProduct.isPresent()) {
            throw new NoSuchElementException("Not found product with id " + id);
        }
        Product newProduct =
            new Product(id,
                coalesce(product.getName(), checkProduct.get().getName()),
                coalesce(product.getDescription(), checkProduct.get().getDescription()),
                coalesce(product.getPrice(), checkProduct.get().getPrice())
            );
        return productRepository.save(newProduct);
    }

    /**
     * Busca todos os produtos
     *
     * @return: lista de produtos
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }


    /**
     * Busca um produto de id {id}, caso não encontre, lança uma exception
     *
     * @param id: id do produto
     *
     * @throws ResponseStatusException
     */
    public Product findById(Long id) throws ResponseStatusException{
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new NoSuchElementException("Not found product with id " + id);
        }
       return product.get();
    }


    /**
     * Busca todos os produtos que contenham:
     *  > {q} em seu nome ou descrição
     *  > preço entre {min_price} e {max_price}
     *  Os parametros podem ser nulos
     *
     * @param q:            Texto a ser buscado em nome ou descrição (opcional)
     * @param min_price:    Preço minimo a ser utilizado no filtro (opcional)
     * @param max_price:    (opcional)
     *
     * @return lista de produtos filtrados por nome, descrição e/ou preço
     */
    public List<Product> search(String q, Double min_price, Double max_price) {
        return productRepository.findAll(
                Specification.where(
                    (ProductSpecification.name(q)
                            .or(ProductSpecification.description(q))
                    )
                    .and(ProductSpecification.greaterOrEqualThan(min_price)
                    .and(ProductSpecification.lessOrEqualThan(max_price)))));
    }

    /**
     * Remove um produto com id {id}, caso não encontre, lança uma exception
     *
     * @param id:   id do produto a ser excluído
     *
     * @throws ResponseStatusException
     */
    public void delete(Long id) throws ResponseStatusException{
        Optional<Product> checkProduct = productRepository.findById(id);
        if (!checkProduct.isPresent()) {
            throw new NoSuchElementException("Not found product with id " + id);
        }
        productRepository.delete(checkProduct.get());
    }

    /**
     * Obtém o primeiro objeto não nulo.
     *
     * @param objs Vetor de objetos.
     *
     * @return Primeiero objeto não nulo.
     *************************************************************************/
    public static <T> T coalesce(T ... objs)
    {
        for (T obj : objs)
        {
            if (obj != null)
                return obj;
        }
        return null;
    } //coalesce


}
