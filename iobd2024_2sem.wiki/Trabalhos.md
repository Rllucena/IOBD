# Trabalho 1

## Descrição

Vamos criar um BD para um portal artigos, semelhante ao Medium. Este portal permitirá que usuários publiquem artigos, comentem e curtam artigos, e que haja categorias para organizar os conteúdos.

## Explicação

* Usuário: Armazena informações sobre os usuários do portal.
    * Email
    * Nome
    * Senha
    * Data de Cadastro
    * Data de Nascimento
    * telefones
    * Endereço: bairro, complemento, nro, cep, rua
    
* Artigo: Armazena os artigos escritos pelos usuários
    * Titulo
    * Data/hora da Publicação
    * Tem relacionamento com Categoria e com Usuário

* Categoria: Armazena as categorias dos artigos.
    * Nome
    
* Comentário: Armazena os comentários feitos nos artigos, com referência ao autor do comentário (Usuário) e ao artigo comentado.
    * Conteudo
    * Data e Hora do Comentário
    * Tem relacionamento com Usuário e Artigo
    
* Curtida: Armazena as curtidas nos artigos, com referência ao usuário que curtiu e ao artigo curtido.
   * Data e Hora
   * Relacionamento com Artigo (qual artigo foi curtido) e Usuário (quem curtiu)
   
obs: 

* artigos podem ser escritos por mais de um usuário

* minimizar a complexidade do trabalho, comentários não são cabíveis de curtida - somente os artigos

## Exigências

### 1) Modelo Relacional

**Principais Tabelas:**

* Usuário: Representa os escritores e leitores.
* Artigo: Representa os artigos escritos pelos usuários.
* Categoria: Representa as categorias dos artigos.
* Comentário: Representa os comentários feitos nos artigos.
* Curtida: Representa as curtidas nos artigos.

**Relacionamentos:**

* Um Usuário pode escrever vários Artigos.
* Um Artigo pertence a uma Categoria.
* Um Artigo pode ter vários Comentários.
* Um Usuário pode fazer vários Comentários.
* Um Usuário pode curtir vários Artigos.

### 2) Implementação Física

* script.sql

### 3) Consultas

* Quais usuários escreveram mais artigos? Em caso de empate mostrar todos
* O título de cada Artigo e o nome de cada usuário envolvido na escrita de cada Artigo
* Listar os Usuários com e sem endereços. Caso tenha endereço, coloque o endereço. Se não tiver, coloque "Sem endereço cadastrado"
* Construa um view
* Construa 2 schemas


## Valor: 4

## Entrega: a combinar