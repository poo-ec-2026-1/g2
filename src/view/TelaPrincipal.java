import dao.ProdutoDAO;
import java.sql.SQLException;

package view;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JTextArea areaTexto;

    public TelaPrincipal() {

        setTitle("Sistema Ca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelBotoes = new JPanel();

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnListar = new JButton("Listar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");

        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnListar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);

        add(painelBotoes, BorderLayout.NORTH);
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        btnCadastrar.addActionListener(e -> cadastrarProduto());

        btnListar.addActionListener(e -> listarProdutos());

        btnEditar.addActionListener(e -> editarProduto());

        btnExcluir.addActionListener(e -> excluirProduto());
    }

    private void cadastrarProduto() {

        try {

            String nome = JOptionPane.showInputDialog("Nome:");

            if (nome == null) return;

            String descricao = JOptionPane.showInputDialog("Descrição:");

            double preco = Double.parseDouble(
                    JOptionPane.showInputDialog("Preço:")
            );

            int estoque = Integer.parseInt(
                    JOptionPane.showInputDialog("Quantidade:")
            );

            String categoria = JOptionPane.showInputDialog("Categoria:");

            Produto produto = new Produto(
                    nome,
                    descricao,
                    preco,
                    estoque,
                    categoria
            );

            ProdutoDAO dao = new ProdutoDAO();
            dao.inserir(produto);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto cadastrado com sucesso!"
            );

            listarProdutos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao cadastrar produto:\n" + e.getMessage()
            );
        }
    }

    private void listarProdutos() {

        try {

            areaTexto.setText("");

            ProdutoDAO dao = new ProdutoDAO();

            for (Produto p : dao.listar()) {

                areaTexto.append(p.toString());
                areaTexto.append("\n");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao listar produtos:\n" + e.getMessage()
            );
        }
    }

    private void editarProduto() {

        try {

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("ID do produto:")
            );

            ProdutoDAO dao = new ProdutoDAO();

            Produto produto = dao.buscarPorId(id);

            if (produto == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado."
                );

                return;
            }

            String nome = JOptionPane.showInputDialog(
                    "Novo nome:",
                    produto.getNome()
            );

            String descricao = JOptionPane.showInputDialog(
                    "Nova descrição:",
                    produto.getDescricao()
            );

            double preco = Double.parseDouble(
                    JOptionPane.showInputDialog(
                            "Novo preço:",
                            produto.getPreco()
                    )
            );

            int estoque = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            "Novo estoque:",
                            produto.getQuantidadeEstoque()
                    )
            );

            String categoria = JOptionPane.showInputDialog(
                    "Nova categoria:",
                    produto.getCategoria()
            );

            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            produto.setQuantidadeEstoque(estoque);
            produto.setCategoria(categoria);

            dao.atualizar(produto);

            JOptionPane.showMessageDialog(
                    this,
                    "Produto atualizado com sucesso!"
            );

            listarProdutos();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao editar produto:\n" + e.getMessage()
            );
        }
    }

    private void excluirProduto() {

        try {

            int id = Integer.parseInt(
                    JOptionPane.showInputDialog("ID do produto:")
            );

            ProdutoDAO dao = new ProdutoDAO();

            Produto produto = dao.buscarPorId(id);

            if (produto == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Produto não encontrado."
                );

                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir o produto \""
                            + produto.getNome()
                            + "\"?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacao == JOptionPane.YES_OPTION) {

                dao.excluir(id);

                JOptionPane.showMessageDialog(
                        this,
                        "Produto excluído com sucesso!"
                );

                listarProdutos();
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao excluir produto:\n" + e.getMessage()
            );
        }
    }
}
