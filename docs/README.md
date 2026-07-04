UNIVERSIDADE FEDERAL DE GOIÁS

Curso de Engenharia de Computação

Sistema de Vendas – CA STORE

Integrantes:

PEDRO FERREIRA JUNQUEIRA DO VAL - Líder, testes, implementação do banco de dados e documentação

ANA CLARA ALCANTARA BORBA DOS SANTOS - implementação do front end e tela principal

DANIEL MENDONÇA DE MOURA FILHO - back-end (venda de produtos)

TIAGO SOUSA GUIMARAES - back-end (cadastro, edição e exclusão de produtos)

Professor:
MARCELO AKIRA INUZUKA

Data:
1/06/2026

1. Objetivo

O sistema Loja do CA foi desenvolvido em Java com o objetivo de facilitar o gerenciamento das vendas realizadas pelo Centro Acadêmico da universidade.

O sistema permite cadastrar produtos, editar informações, excluir produtos, controlar o estoque e realizar vendas por meio de um carrinho de compras. Todas as informações são armazenadas em um banco de dados SQLite utilizando o framework ORMLite, garantindo persistência dos dados.

2. Tecnologias Utilizadas

Durante o desenvolvimento foram utilizadas as seguintes tecnologias:

Java
Java Swing
Maven
SQLite
ORMLite
GitHub

Cada tecnologia possui uma função específica:

Java: desenvolvimento do sistema.
Swing: criação da interface gráfica.
SQLite: armazenamento permanente dos dados.
ORMLite: comunicação entre Java e o banco de dados.
Maven: gerenciamento das dependências do projeto.
GitHub: controle de versão e colaboração da equipe.

3. Estrutura do Projeto

O projeto foi organizado utilizando uma arquitetura em camadas.

src
└── main
    └── java
        ├── controller
        ├── dao
        ├── database
        ├── model
        └── view

Descrição de cada pasta:

model

Contém as classes responsáveis por representar os objetos do sistema, como Produto, Venda e ItemVenda.

dao

Responsável pelo acesso ao banco de dados utilizando ORMLite.

database

Realiza a conexão com o banco SQLite e cria automaticamente as tabelas.

view

Contém toda a interface gráfica desenvolvida em Swing.

controller

Responsável pela comunicação entre a interface e as regras de negócio.

4. Funcionalidades

O sistema possui as seguintes funcionalidades:

Cadastro de produtos;
Alteração de produtos;
Exclusão de produtos;
Listagem de produtos;
Controle de estoque;
Carrinho de compras;
Cálculo automático do valor total;
Finalização da venda;
Atualização automática do estoque após a venda.

5. Funcionamento

O funcionamento do sistema ocorre conforme o fluxo abaixo.

Cadastro de Produto
        │
        ▼
Banco SQLite
        │
        ▼
Listagem de Produtos
        │
        ▼
Adicionar ao Carrinho
        │
        ▼
Calcular Total
        │
        ▼
Finalizar Venda
        │
        ▼
Atualizar Estoque

Após a confirmação da venda, o sistema reduz automaticamente a quantidade disponível em estoque e registra os itens vendidos.

6. Banco de Dados

O projeto utiliza o banco de dados SQLite, escolhido por sua simplicidade e por não exigir instalação de um servidor.

A comunicação entre a aplicação e o banco é realizada através do framework ORMLite, que permite manipular objetos Java como registros do banco de dados.

A criação das tabelas ocorre automaticamente na inicialização do sistema.

7. Interface Gráfica

A interface foi desenvolvida utilizando Java Swing.

Ela permite ao usuário realizar todas as operações através de botões e tabelas, eliminando a necessidade de utilizar comandos no terminal.

As principais telas do sistema são:

Tela principal;
Cadastro de produtos;
Carrinho de compras.

A interface foi inspirada em um layout desenvolvido em HTML/CSS, proporcionando uma aparência mais moderna e intuitiva.

![Diagrama de caso de uso](//www.plantuml.com/plantuml/png/RLBDRW8X4Bxp59EzwoCOZR7NDcsFNYP0bOG5Emp6jEcx5t2r8rqF1Zw-uVjXrucHE8zUUVl1m1787OuCnf7Lx69G2ZL7Wk7vP0bDMHaB6jq51y04sxgQezZ4yJFRHjfTrqgfUYc6WxSmlBd4TiJAFTjW2it2YQB97DC0tmhAh_tbP3McCh4nJXT7IE3Av3vW18r4BXnZazaOivtsZuElTendUniptLWvdHzxSW6z-qAw6cpaEpRZRuib1HFIBSVKa7bfs-xsdN7SKIiqpxredrr7lcZ9xQrudJqIS6Jq9JEA3FNmlU9TlPolqVP1Fbw1f_Vh9698sVSHAdgpzQDK-pnWiLZ9nn5ewxv7HSbgagwmR5B2iZM9YuAKK8VbSlLWywvMJ_xAwN1HWn3yTzcwj9n7_mi0)

![Diagrama de sequencia](//www.plantuml.com/plantuml/png/jLB1Jkj03BpdAtnifTSVU0TK92uzKQJ2tTqrO6cJJHrlGNmzJjCisJQgk93REfxnZCVhJb4qrQ52fr7WkKie7AlMgkounKPXHm6tmat_3CMVhKIVD3xSFnRb5sey5fMDKZrKg_C4MDsLnF0VEWhaE3O8xK0C18TaUFRegLV8HrIw45IyZ7QYoZZtgNCesoWRls2_p2HJxHF0wbhMAARoD0U-cZ_ODnnwFhcHtltYpF9lucYYPbZEW3IA7Z7IDFJnyvwdoRWHup2u551sKJ4iSiECEPL4sLaMScdAW6DkvaiJ1lv2S2ZMyXx14bC3J7aAWfkfky0EWMfuvUR4At3iHVmwxz5qnW-Rkjfk5M9ieKNfbHk2rORwc6rKODzi16-aJtdTHbmqZarvBTHfF2GowdmwrbBzVKhbwMT1CXxDycQizCbx0ksrWCTJ1ZSINTllL8Tl)

![Diagrama de classe](//www.plantuml.com/plantuml/png/TLJ1QXin4BtlLuYSR5e7UZN593Y51XgQ4DDBwM4iJHA1LkEC9BSqz6jww8VanpgorjhrBhaabjxJqvitixe84JYcrZNQGGZg1gn_QTwfLlxFxYDR__ZzntofzcHDywVfM1jqS2kOjZjmwgLPg6_e3QXz_diP4xX6D3GO2961RvbCYbHK101ZjIKFV4bhOD5vejbSLVZ1Ud3sD_11T3QvVSqMU0DRb9LurNavPEKQrBcXd_vyTMfrgQoNg-6KCjHRAlq4BHolwfoloKScvv1phLziY1yxPALsvKTlhqEkBhzcyXMGCa0v3-i3ikLPHvZNWfq8bX9Ef0NPfg0bF-9hCxC-2gC31OEOIanJGVobNRASZupJAvtApWHK0dJrN4Ti3z1AsOW-oBwXj7Mezk28DnJ1oTwMo27usYC3MO0qk2k8kB4jAWCHFXD3C-nqbZ_wV5ziTLB2OcnfZuKpnaHP9mV5G8wek9gEoV1KIRG0OJf0jTOykiT4QoDBCiy9VBG633O35_Tf6oT67Z7ULROW-RniXvFjN5TsSFhfBF88BbQbAKjHOXG4tc3raxLKAK3Jrt7suKpHuYYZoGiocaSfzTZnybqhJotMXS6WsRx-U_qh2PJI917KiZgDurE8z9pwWneYFXBRt6z9uZQV6SOJZdjbOtvWtOtbQcnX2lRXbO9aqc0B0M_0WoGcXQt9UzJHah-dn1gLhXl5LK_85utfMNhu2DINeJvSMb0L3sWpUHF6_HdqiU_OLBC5RtV93Tie6jB9Ef5BnVZmjjGuY38K5wAKMlSV)

8. Conclusão

O projeto atingiu o objetivo proposto ao desenvolver um sistema capaz de gerenciar produtos e realizar vendas utilizando banco de dados.

Durante o desenvolvimento foram aplicados conceitos importantes da programação orientada a objetos, persistência de dados, arquitetura em camadas, interface gráfica e controle de versões com GitHub.

Como melhorias futuras, podem ser adicionados recursos como autenticação de usuários, emissão de comprovantes, geração de relatórios e histórico completo de vendas.
