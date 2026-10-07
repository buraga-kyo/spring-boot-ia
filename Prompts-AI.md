


#### BLOCO 1 — Banco de dados com Neon via MCP

Comando:   claude mcp add --transport http neon https://mcp.neon.tech/mcp \ --header "Authorization: Bearer <YOUR_NEON_API_KEY>"

Doc: https://neon.com/guides/claude-code-mcp-neon

```

Use o MCP do Neon no projeto id falling-sunset-47410845 chamado "decoder-bookstore" e crie um database chamado "bookstore-db". Me mostre a connection string resultante.

Depois, no arquivo application.yaml configure:
- datasource apontando para o Neon, lendo as credenciais de variáveis de
  ambiente DB_URL, DB_USER e DB_PASSWORD
- ddl-auto como update
- open-in-view deixe o default do Spring: true, ou seja, não precisa inserir essa config
- pool do Hikari com máximo de 5 conexões
- format_sql habilitado
- no arquivo application.yaml deixe as credenciais neste formato: ${DB_URL}
- insira a config no arquivo application.yaml: config:import: optional:file:.env[.properties]

No arquivo env na raiz, insira as credenciais da base de dados acima.
  
```


#### BLOCO 2 — Model com JPA

```

Crie a entidade BookModel em models/.

Campos:
- id: UUID, chave primária, geração automática
- title: texto, obrigatório, máximo 150 caracteres
- author: texto, obrigatório, máximo 100 caracteres
- publisher: texto, obrigatório, máximo 100 caracteres
- publicationYear: inteiro, obrigatório
- review: texto longo, opcional, com columnDefinition =
  "TEXT"

Siga a skill jpa-conventions. Tabela tb_books.
Gere apenas este arquivo.

```


#### BLOCO 3 — Repository

```

Crie a interface BookRepository em repositories/, extendendo a interface JpaRepository, seguindo a skill jpa-conventions.

```


#### BLOCO 4 — Record DTO de entrada com validação

```

Crie o BookRecordDto em dtos/ como um Java record, seguindo a skill
api-conventions.

Campos de entrada: title, author, publisher, publicationYear.
Não inclua id nem review — o id é gerado pelo banco e o review será
gerado por IA.

Adicione as validações do Bean Validation adequadas a cada campo.

```


#### BLOCO 5 — Service e métodos

```

Crie o BookService em services/ seguindo a skill api-conventions.

Métodos: findAll com retorno List, findById com retorno Optional, save, update, delete sem retorno - void.

A conversão de BookRecordDto para BookModel DEVE ser feita via BeanUtils.copyProperties.

Use injeção por construtor. Gere apenas este arquivo. 

```


#### BLOCO 6 — Controller: POST, PUT e DELETE

```

Crie o BookController em controllers/ seguindo a skill api-conventions.

Por enquanto apenas 3 endpoints:
- POST /books recebendo BookRecordDto validado, devolvendo 201
- PUT /books/{id} recebendo BookRecordDto validado, devolvendo 200 ou 404
- DELETE /books/{id} devolvendo 200 ou 404

Retorne ResponseEntity com o status explícito. Gere apenas este arquivo.

```

```

Inclua a anotação @Autowired para definir o ponto de injeção para BookService.

```

Verificar o hook disparando e o Claude Code bloqueando essa edição.


#### BLOCO 7 — Controller: GET all e GET one

```

Complete o BookController com:
- GET /books devolvendo a lista completa
- GET /books/{id} devolvendo um livro, ou 404 se não existir

Siga a skill api-conventions para os status codes.


```


#### BLOCO 8 — Versionamento de APIs no Spring Framework 7

```

Vou adicionar o versionamento nativo de API do Spring Framework 7 nesta
aplicação. A API já está pronta e funcionando.

Primeiro passo: crie uma classe WebConfig em configs/ que implemente
WebMvcConfigurer e sobrescreva configureApiVersioning, usando o
ApiVersionConfigurer para resolver a versão a partir do header
X-API-VERSION, e declarando a versão v1 como suportada.

Não altere o controller ainda.

```

```
Agora adicione version = "v1" a todos os endpoints existentes do BookController, sem mudar path, comportamento ou status codes.

```


#### BLOCO 9 — Spring AI: conexão e integração

```

Crie o ReviewService em services/.

Responsabilidade: receber o título de um livro e devolver um review curto,
gerado pelo LLM a partir do conhecimento do próprio modelo — sem RAG e sem
consulta a fonte externa.

Regras:
- Use o ChatClient do Spring AI, injetado por construtor.
- O prompt deve pedir um resumo objetivo e direto do livro, sem opinião e
  sem repetir o título na resposta, o idioma da resposta deve ser português.
- O prompt deve pedir explicitamente no máximo 500 caracteres.
- Ainda assim, trunque a resposta em 500 caracteres antes de devolver:
  o modelo não garante o limite e a coluna review é VARCHAR(500).
- Envolva todo o fluxo em um try/catch (Exception).
- No catch: um System.out.println avisando que esse erro precisa ser
  tratado, mais um comentário indicando que aqui caberia tratamento
  adequado, envio para fila de erro, retentativa ou circuit breaker.
- Em caso de erro, retorne null.

```


#### BLOCO 10 — Integração com BookService - fluxo final

```

Injete o ReviewService no BookService por construtor.

Atualize o método save incluindo:
1. Chame o ReviewService passando o título, para obter o review
2. Atribua o retorno ao campo review — se vier null, o campo fica null,
   que é permitido
3. Persista o BookModel completo em uma única chamada ao repository

Não coloque try/catch aqui: o tratamento já está no ReviewService.

Altere apenas o BookService.

```

