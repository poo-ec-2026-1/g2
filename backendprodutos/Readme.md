# CA — Backend de Produtos

Módulo responsável pelo cadastro, edição, exclusão e listagem de produtos
do estoque do Centro Acadêmico. Reescrito a partir da classe `Produto`
original, separando responsabilidades em camadas (MVC), com validações de
negócio e testes automatizados.

## Como executar

Pré-requisitos: JDK 17+ e Maven 3.8+.

```bash
mvn compile exec:java     # roda a demonstração (Main.java)
mvn test                  # roda os testes automatizados (JUnit 5)
```

## O que mudou em relação ao código original

A classe `Produto` original acumulava **quatro responsabilidades diferentes**
na mesma classe: (1) representar um produto, (2) guardar a lista `static`
de todos os produtos já cadastrados, (3) aplicar as regras de
cadastro/edição/exclusão e (4) imprimir mensagens no console. É esse
acúmulo de responsabilidades em uma única classe que costuma ser apontado
como "sem arquitetura MVC" — não há Model, Controller e View separados,
está tudo junto.

Agora cada responsabilidade tem sua própria classe:

| Camada | Classe | Responsabilidade |
|---|---|---|
| Model | `Produto` | Só os dados de um produto e o cálculo do próprio preço (com promoção). |
| Repository (Model/dados) | `ProdutoRepositorio` | Guarda a coleção de produtos e gera os IDs — antes era uma `List` e um `static int contadorId` dentro da própria `Produto`. |
| Service (regras de negócio) | `ProdutoService` | Valida os dados (nome, preço, estoque, categoria) antes de gravar; é o único ponto de entrada para cadastrar/editar/excluir/listar. |
| View | `ProdutoConsoleView` | Concentra todo `System.out.println`; antes os prints estavam espalhados dentro dos métodos estáticos de `Produto`. |
| Demonstração / documentação executável | `Main` | Mostra as camadas conectadas de ponta a ponta — serve de documentação de como rodar o projeto. |

Outras mudanças pontuais:

- Os métodos deixaram de ser `static`: agora `ProdutoRepositorio` e
  `ProdutoService` são objetos (`new ProdutoRepositorio()`,
  `new ProdutoService(repositorio)`), o que permite ter múltiplos catálogos
  independentes e — principalmente — permite escrever testes automatizados
  sem que um teste interfira no estado de outro.
- Validações de negócio que não existiam antes (nome vazio, preço
  negativo, estoque negativo, categoria vazia) agora lançam
  `IllegalArgumentException` com mensagem clara, em vez de aceitar
  qualquer valor.
- Foram acrescentados três campos a `Produto` — `qtdPromo`, `precoPromo` e
  `chavePix` — para que esta mesma classe possa alimentar diretamente a
  vitrine e a tela de pagamento do frontend (ver seção **Integração com o
  frontend** abaixo). Produtos sem promoção simplesmente deixam `qtdPromo`
  como `0`.
- Foram adicionados **9 testes automatizados** (`ProdutoServiceTest`),
  cobrindo cadastro, validações, cálculo de promoção, edição, exclusão e
  busca por estoque baixo.

## Integração com o frontend (JavaFX)

O frontend já construído usa uma interface `ProdutoService` própria, com
uma classe `Produto` num pacote diferente (`com.castore.model.Produto`,
com os campos `precoUnit`/`qtdPromo`/`precoPromo`/`chavePix`) e uma
implementação temporária (`ProdutoServiceEmMemoria`) com produtos fixos no
código, criada só para a interface funcionar de forma independente nesta
etapa.

Com este módulo pronto, o próximo passo natural é o grupo unificar os dois
em um só `Produto` (o deste módulo já tem os campos que a vitrine precisa)
e trocar `ProdutoServiceEmMemoria` por uma implementação que delegue para
este `ProdutoService`/`ProdutoRepositorio` — nesse ponto o cardápio da loja
passaria a exibir exatamente os produtos cadastrados aqui, sem precisar de
nenhuma API HTTP no meio, o que resolve diretamente o apontamento de que
"uso de uma interface em HTML não permite conexão com o backend aprendido".

## Relação com o feedback do professor

| Apontamento do professor | Como foi tratado |
|---|---|
| "Não tem arquitetura MVC" | Model (`Produto`), dados (`ProdutoRepositorio`), regras (`ProdutoService`) e exibição (`ProdutoConsoleView`) agora são classes separadas. |
| "Sem documentação de rodar o projeto" | Este `README.md` + `Main.java` executável documentam o passo a passo. |
| "A proposta é boa e prática, mas não foi implementada de forma completa" | CRUD completo (cadastrar/editar/excluir/listar), validações de negócio e 9 testes automatizados. |
| "branch main vazia" | Este módulo precisa ser adicionado (ou mergeado) na branch `main` do repositório do grupo — nenhuma ferramenta externa resolve isso, é um passo manual de `git merge`/Pull Request que o grupo precisa fazer. |
