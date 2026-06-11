package view;

import model.Produto;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    private JTextArea areaTexto;

    public TelaPrincipal() {

        setTitle("Sistema Restaurante Universitário");
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

        String nome = JOptionPane.showInputDialog("Nome:");

        String descricao = JOptionPane.showInputDialog("Descrição:");

        double preco = Double.parseDouble(
                JOptionPane.showInputDialog("Preço:")
        );

        int estoque = Integer.parseInt(
                JOptionPane.showInputDialog("Quantidade:")
        );

        String categoria = JOptionPane.showInputDialog("Categoria:");

        Produto.adicionarProduto(
                nome,
                descricao,
                preco,
                estoque,
                categoria
        );

        listarProdutos();
    }

    private void listarProdutos() {

        areaTexto.setText("");

        for (Produto p : Produto.getListaProdutos()) {

            areaTexto.append(p.toString());
            areaTexto.append("\n");
        }
    }

    private void editarProduto() {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog("ID do produto:")
        );

        String nome = JOptionPane.showInputDialog("Novo nome:");

        String descricao = JOptionPane.showInputDialog("Nova descrição:");

        double preco = Double.parseDouble(
                JOptionPane.showInputDialog("Novo preço:")
        );

        int estoque = Integer.parseInt(
                JOptionPane.showInputDialog("Novo estoque:")
        );

        String categoria = JOptionPane.showInputDialog("Nova categoria:");

        Produto.editarProduto(
                id,
                nome,
                descricao,
                preco,
                estoque,
                categoria
        );

        listarProdutos();
    }

    private void excluirProduto() {

        int id = Integer.parseInt(
                JOptionPane.showInputDialog("ID do produto:")
        );

        Produto.excluirProduto(id);

        listarProdutos();
    }
}
