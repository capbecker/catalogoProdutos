package com.recrutamento.catalogoProdutos.product.service;

import com.recrutamento.catalogoProdutos.product.model.Produto;
import com.recrutamento.catalogoProdutos.product.repository.ProductRepository;
import com.recrutamento.catalogoProdutos.product.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * @param produto:  dados do produto a ser inserido
     *
     * @return: produto inserido
     */
    public Produto insert(Produto produto) {
        Produto produtoSaved = productRepository.save(produto);
        return produtoSaved;
    }

    /**
     * Atualiza um produto com id {id} com os dados de {product}, caso não encontre, lança uma exception
     * Caso {product} tenha os campos name, description ou price como null, será mantido o valor antigo
     *
     * @param id:       id do produto a ser excluído
     * @param produto:  dados do produto a ser atualizado
     *
     * @return: produto atualizado
     * @throws ResponseStatusException
     */
    public Produto update(Long id, Produto produto) throws ResponseStatusException{
        Optional<Produto> checkProduct = productRepository.findById(id);
        if (!checkProduct.isPresent()) {
            throw new NoSuchElementException("Not found product with id " + id);
        }
        Produto newProduto =
            new Produto(id,
                coalesce(produto.getNome(), checkProduct.get().getNome()),
                coalesce(produto.getValidade(), checkProduct.get().getValidade()),
                coalesce(produto.getPreco(), checkProduct.get().getPreco())
            );
        return productRepository.save(newProduto);
    }

    /**
     * Busca todos os produtos
     *
     * @return: lista de produtos
     */
    public List<Produto> findAll() {
        return productRepository.findAll();
    }


    /**
     * Busca um produto de id {id}, caso não encontre, lança uma exception
     *
     * @param id: id do produto
     *
     * @throws ResponseStatusException
     */
    public Produto findById(Long id) throws ResponseStatusException{
        Optional<Produto> product = productRepository.findById(id);
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
    public Page<Produto> search(String q, Double min_price, Double max_price, Pageable pageable) {
        Specification<Produto> specification =
            Specification.where(
                (ProductSpecification.nome(q)
                       // .or(ProductSpecification.description(q))
                )
                .and(ProductSpecification.greaterOrEqualThan(min_price)
                .and(ProductSpecification.lessOrEqualThan(max_price))));
        return productRepository.findAll(specification, pageable);
    }

    /**
     * Remove um produto com id {id}, caso não encontre, lança uma exception
     *
     * @param id:   id do produto a ser excluído
     *
     * @throws ResponseStatusException
     */
    public void delete(Long id) throws ResponseStatusException{
        Optional<Produto> checkProduct = productRepository.findById(id);
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
