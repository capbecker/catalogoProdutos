package com.recrutamento.catalogoProdutos.product.controller;

import com.recrutamento.catalogoProdutos.product.model.Produto;
import com.recrutamento.catalogoProdutos.product.model.ProdutoDto;
import com.recrutamento.catalogoProdutos.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController()
//@RequestMapping("products")
@RequestMapping("api/produto")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;


    /**
     * Valida se os dados foram enviados corretamente e insere produto
     *
     * @param produtoDto:   Produto no formato ProductDto
     * @param ucb:          Objeto para gerar a url de retorno
     *
     * @return
     *  Se não ocorrer nenhum erro, retorna um response com o status 201 e o objeto criado no body
     *  Caso contrário, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     */
    @PostMapping("salvarProduto")
    @Transactional
    public ResponseEntity<Produto> insert(
            @Valid @RequestBody ProdutoDto produtoDto,
            UriComponentsBuilder ucb)  {
        Produto produtoSaved = productService.insert(convertToEntity(produtoDto));
        URI uri = ucb.path("/").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(produtoSaved);
    }

    /**
     * Atualiza um produto com id {id} com os dados de {product}
     *  Caso o produto de id {id} não exista, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     *  Caso {product} tenha os campos name, description ou price como null, será mantido o valor antigo
     *
     * @param id:           id do produto
     * @param produtoDto:   Produto no formato ProductDto
     *
     * @return
     *  Se não ocorrer nenhum erro, retorna um response com o status 200 e o objeto atualizado no body
     *  Caso contrário, retorna um response com o status do erro e tratado no GlobalExceptionHandler
     */
    @PutMapping("/atualizarProduto/{id}")
    @Transactional
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody ProdutoDto produtoDto) {
        Produto produto = convertToEntity(produtoDto);
        Produto produtoSaved = productService.update(id, produto);
        return ResponseEntity.ok(produtoSaved);

    }

    /**
     * Busca todos os produtos
     *
     * @return um response com o status 200 e a lista de produtos no body
     */
    @GetMapping()
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> produtos = productService.findAll();
        return ResponseEntity.ok(produtos);
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
    @GetMapping("/obterProduto/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto products = productService.findById(id);
        return ResponseEntity.ok(products);
    }

    /**
     * Busca todos os produtos que contenham {q} em sem nome ou descrição e com price entre min_price e max_price
     *
     *
     * @param q:            Parametro de busca de nome ou descrição de produtos (opcional)
     * @param min_price:    Parametro de valor minimo de price de produtos (opcional)
     * @param max_price:    Parametro de valor maximo de price de produtos (opcional)
     * @param page:         Parametro de qual pagina se deve buscar;
     * @param size:         Parametro de quantos registros devem vir por pagina;
     *
     * @return  um response com o status 200 e a lista de produtos no body
     */
    @GetMapping("/buscarProdutos")
    public ResponseEntity<Page<Produto>> search(@RequestParam(name = "q", required = false) String q,
                                                @RequestParam(name = "min_price", required = false) Double min_price,
                                                @RequestParam(name = "max_price", required = false) Double max_price,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtos = productService.search(q, min_price, max_price, pageable);

        return ResponseEntity.ok(produtos);
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
    @DeleteMapping("excluirProduto/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Converte um ProductDto para Product
     *
     * @param produtoDto: ProductDto a ser convertido
     *
     * @return Product convertido
     */
    private Produto convertToEntity(ProdutoDto produtoDto) {
        Produto produto = modelMapper.map(produtoDto, Produto.class);
        return produto;
    }
}
