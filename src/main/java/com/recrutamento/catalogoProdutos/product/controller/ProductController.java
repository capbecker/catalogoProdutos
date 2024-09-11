package com.recrutamento.catalogoProdutos.product.controller;

import com.recrutamento.catalogoProdutos.product.model.Product;
import com.recrutamento.catalogoProdutos.product.model.ProductDto;
import com.recrutamento.catalogoProdutos.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController()
@RequestMapping("products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Valida se os dados foram enviados corretamente e insere produto
     *
     * @param productDto:   Produto no formato ProductDto
     * @param ucb:          Objeto para gerar a url de retorno
     *
     * @return
     *  Se não ocorrer nenhum erro, retorna um response com o status 201 e o objeto criado no body
     *  Caso contrário, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     */
    @PostMapping
    @Transactional
    public ResponseEntity<Product> insert(
            @Valid @RequestBody ProductDto productDto,
            UriComponentsBuilder ucb)  {
        Product productSaved = productService.insert(convertToEntity(productDto));
        URI uri = ucb.path("/").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(productSaved);
    }

    /**
     * Atualiza um produto com id {id} com os dados de {product}
     *  Caso o produto de id {id} não exista, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     *  Caso {product} tenha os campos name, description ou price como null, será mantido o valor antigo
     *
     * @param id:           id do produto
     * @param productDto:   Produto no formato ProductDto
     *
     * @return
     *  Se não ocorrer nenhum erro, retorna um response com o status 200 e o objeto atualizado no body
     *  Caso contrário, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product productSaved = productService.update(id, product);
        return ResponseEntity.ok(productSaved);

    }

    /**
     * Busca todos os produtos
     *
     * @return um response com o status 200 e a lista de produtos no body
     */
    @GetMapping()
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    /**
     * Busca um produto com id {id}
     *  Caso o produto de id {id} não exista, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     *
     * @param id:  id do produto
     *
     * @return
     *  Se não ocorrer nenhum erro, retorna um response com o status 200
     *  Caso contrário, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product products = productService.findById(id);
        return ResponseEntity.ok(products);
    }

    /**
     * Busca todos os produtos que contenham {q} em sem nome ou descrição e com price entre min_price e max_price
     *
     *
     * @param q:            Parametro de busca de nome ou descrição de produtos (opcional)
     * @param min_price:    Parametro de valor minimo de price de produtos (opcional)
     * @param max_price:    Parametro de valor maximo de price de produtos (opcional)
     *
     * @return  um response com o status 200 e a lista de produtos no body
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam(name = "q", required = false) String q,
                                                @RequestParam(name = "min_price", required = false) Double min_price,
                                                @RequestParam(name = "max_price", required = false) Double max_price) {
        List<Product> products = productService.search(q, min_price, max_price);
        return ResponseEntity.ok(products);
    }

    /**
     * Remove um produto com id {id}
     *  Caso o produto de id {id} não exista, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     *
     * @param id:   id do produto
     *
     * @return
     *  Se não ocorrer nenhum erro, retorna um response com o status 200
     *  Caso contrário, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Converte um ProductDto para Product
     *
     * @param productDto: ProductDto a ser convertido
     *
     * @return Product convertido
     */
    private Product convertToEntity(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return product;
    }
}
