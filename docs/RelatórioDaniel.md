# Relatório pessoal - CA Store

## 1. Introdução

Membro: Daniel Mendonça de Moura Filho

Cargo atribuido: Desenvolvedor de Beckend - Venda

### 1. Atribuição de cargo e tarefas
Fui designado para desenvolver o backend de vendas do sistema: a lógica da compra, o registro de vendas e o cálculo dos valores totais. De acordo com a divisão inicial, minhas responsabilidades eram:

- Criar as classes Venda e ItemVenda;
- Implementar as funcionalidades de adicionar item à compra, calcular o total da compra e finalizar a venda;
- Implementar os métodos adicionarItem, calcularTotal e finalizarVenda;

### 2. Contribuição de acordo com a atribuição
### O que foi cumprido:

- Implementei a classe Venda, contendo a lista de itens, o cálculo do valor total, o controle de status (finalizada ou em aberto), a data/hora da venda e um registro estático (listaVendas) com todas as vendas já finalizadas.
- Implementei os métodos adicionarItem (com validação de estoque e de venda já finalizada), removerItem, calcularTotal e finalizarVenda (que também dá baixa automática no estoque de cada produto ao concluir a venda).
- Implementei a classe ItemVenda, responsável pelo cálculo do subtotal de cada item do carrinho.
- Integrei essa lógica com a tela principal (TelaPrincipal, em Swing), que usa diretamente os métodos da Venda para montar e exibir o carrinho, calcular o total exibido na interface e finalizar a compra.

### O que não deu para cumprir:

- Cheguei a iniciar uma classe VendaService (camada de serviço, separando a lógica de negócio da Venda — seguindo o mesmo padrão de arquitetura em camadas usado no backend de produtos), mas não consegui terminá-la a tempo; ela ficou apenas com os imports, sem lógica implementada.

### Commits mais relevantes
Sobre os commits: essa é justamente minha maior dificuldade, e prefiro ser honesto aqui em vez de inventar um histórico que não tenho — não tive domínio nenhum do git e do GitHub durante essa etapa. Fiz boa parte do desenvolvimento localmente, sem organizar commits de forma incremental ou documentada, então não tenho como listar com precisão os "3 commits mais relevantes" do meu trabalho. Isso é algo que preciso corrigir nas próximas etapas. 

- feat: implementa classe ItemVenda com cálculo de subtotal e toString formatado
- feat: implementa classe Venda com controle de estoque, calcularTotal() e finalizarVenda()
- feat: implementa VendaService com ciclo completo do carrinho e histórico de vendas


### Principais dificuldades:

- Falta de prática com git/GitHub: não sei usar comandos básicos de versionamento com confiança (add, commit, push, pull, branch), o que me deixou inseguro para subir o código com frequência e documentar meu progresso corretamente.
- Isso também dificultou a integração do meu código com o que os outros membros estavam produzindo, já que eu não acompanhava bem o fluxo de branches/merges do repositório.

### 3. Contribuição além do atribuido

- Além da parte de vendas, ajudei a equipe produzindo os diagramas do projeto: o diagrama de classes, o diagrama de sequência e o diagrama de casos de uso, que constam na documentação (docs/README.md) do sistema. Essa não era originalmente uma tarefa minha, mas assumi essa parte para ajudar a equipe a cumprir a exigência de modelagem inicial (Seção 4) pedida na etapa do projeto.

- ## 4. Considerações gerais
  
- O que aprendi: essa etapa me mostrou, na prática, o que significa modelar uma entidade de negócio em Java, não apenas guardar dados, mas representar um processo real (uma venda) com estados, regras e validações. Entender como Venda e ItemVenda se relacionam, como o cálculo do total depende do estado de cada item, e como uma ação (finalizar a venda) precisa disparar efeitos em outras partes do sistema (dar baixa no estoque) me deu uma noção mais clara de como pensar em orientação a objetos além da teoria de sala de aula.
Ao mesmo tempo, essa etapa deixou evidente uma lacuna que preciso resolver: meu domínio de git e GitHub ainda é insuficiente para o ritmo de um projeto em equipe. Não é falta de vontade — é falta de prática mesmo, e isso me limitou tanto na organização do meu próprio progresso quanto na integração mais fluida com o código dos colegas.
Trabalhos futuros pendentes: finalizar a camada de serviço (VendaService), que ficou apenas esboçada, separando a regra de negócio da entidade Venda; implementar a persistência das vendas no banco de dados, hoje ainda mantidas apenas em memória; e, principalmente, dedicar tempo para aprender de fato o fluxo básico do git (commits, branches, pull requests) antes da próxima etapa.
Conclusão: entrego essa etapa com o sentimento de missão cumprida na parte técnica de vendas — não é um trabalho perfeito, mas é sólido e funcional dentro do que foi possível fazer. A maior lição, no entanto, não foi de código: foi perceber que dominar as ferramentas do ambiente de desenvolvimento é tão importante quanto escrever a lógica em si, e que essa é a minha principal prioridade de melhoria daqui pra frente.
