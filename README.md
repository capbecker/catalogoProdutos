Microserviço desenvolvido para o desafio da equipe Compass UOL, para a criação, alteração, vizualização e exclusão de produtos

Utiliza base de dados h2database, para possibilitar uma base de dados acoplada ao projeto
Não possui front-end, para testes utilizar o Postman

Porta utilizada é o 7277--9999

Possui os seguintes endpoints:
````
//OLD
 _______________________________________________________
|Verbo__|HTTP_______________|Descrição__________________|
|GET	|/produtos/{id}	    |Busca de um produto por ID |
|GET	|/produtos          |Lista de produtos          |
|GET	|/produtos/search   |Lista de produtos filtrados|
|POST	|/produtos          |Criação de um produto      |
|PUT	|/produtos/         |Atualização de um produto  |
|DELETE	|/produtos/         |Deleção de um produto      |

 ____________________________________________________________________
|Verbo__|HTTP____________________________|Descrição__________________|
|GET	|api/produto/obterProduto/{id}   |Busca de um produto por ID |
|GET	|api/produto                     |Lista de produtos          |
|GET	|api/produto/buscarProdutos      |Lista de produtos filtrados|
|POST	|api/produto/salvarProduto       |Criação de um produto      |
|PUT	|api/produto/atualizarProduto    |Atualização de um produto  |
|DELETE	|api/produto/excluirProduto      |Deleção de um produto      |
````
````

