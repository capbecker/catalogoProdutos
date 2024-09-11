Microserviço desenvolvido para o desafio da equipe Compass UOL, para a criação, alteração, vizualização e exclusão de produtos

Utiliza base de dados h2database, para possibilitar uma base de dados acoplada ao projeto
Não possui front-end, para testes utilizar o Postman

Porta utilizada é o 9999

Possui os seguintes endpoints:
````
 _______________________________________________________
|Verbo__|HTTP_______________|Descrição__________________|
|GET	|/products/{id}	    |Busca de um produto por ID |
|GET	|/products          |Lista de produtos          |
|GET	|/products/search   |Lista de produtos filtrados|
|POST	|/products          |Criação de um produto      |
|PUT	|/products/         |Atualização de um produto  |
|DELETE	|/products/         |Deleção de um produto      |
````
