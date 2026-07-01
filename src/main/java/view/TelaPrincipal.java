package view;

import dao.ProdutoDAO;
import model.ItemVenda;
import model.Produto;
import model.Venda;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private final Color PRIMARY = new Color(30, 41, 59);
    private final Color SUCCESS = new Color(22, 163, 74);
    private final Color BACKGROUND = new Color(241, 245, 249);

    private JTable tabelaProdutos;
    private JTable tabelaCarrinho;

    private DefaultTableModel modeloProdutos;
    private DefaultTableModel modeloCarrinho;

    private JLabel labelTotal;

    private Venda vendaAtual = new Venda();

    public TelaPrincipal() {
        setTitle("CA STORE");
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(15, 15));
        painelPrincipal.setBackground(BACKGROUND);
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painelPrincipal.add(criarCabecalho(), BorderLayout.NORTH);
        painelPrincipal.add(criarConteudo(), BorderLayout.CENTER);
        painelPrincipal.add(criarRodape(), BorderLayout.SOUTH);

        add(painelPrincipal);

        atualizarTabelaProdutos();
        atualizarTabelaCarrinho();
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(PRIMARY);
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("@@ CA STORE @@");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Sistema de Vendas — Centro Acadêmico");
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(Color.WHITE);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(subtitulo);

        return painel;
    }

    private JPanel criarConteudo() {
        JPanel painel = new JPanel(new GridLayout(2, 1, 15, 15));
        painel.setBackground(BACKGROUND);

        painel.add(criarPainelProdutos());
        painel.add(criarPainelCarrinho());

        return painel;
    }

    private JPanel criarPainelProdutos() {
        JPanel painel = criarCard("Produtos");

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        botoes.setBackground(Color.WHITE);

        JButton btnCadastrar = criarBotao("Cadastrar", PRIMARY);
        JButton btnEditar = criarBotao("Editar", PRIMARY);
        JButton btnExcluir = criarBotao("Excluir", Color.RED.darker());
        JButton btnAtualizar = criarBotao("Atualizar", SUCCESS);
        JButton btnCarrinho = criarBotao("Adicionar ao Carrinho", SUCCESS);

        btnCadastrar.addActionListener(e -> cadastrarProduto());
        btnEditar.addActionListener(e -> editarProduto());
        btnExcluir.addActionListener(e -> excluirProduto());
        btnAtualizar.addActionListener(e -> atualizarTabelaProdutos());
        btnCarrinho.addActionListener(e -> adicionarAoCarrinho());

        botoes.add(btnCadastrar);
        botoes.add(btnEditar);
        botoes.add(btnExcluir);
        botoes.add(btnAtualizar);
        botoes.add(btnCarrinho);

        modeloProdutos = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Descrição", "Preço", "Estoque", "Categoria"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaProdutos = new JTable(modeloProdutos);
        estilizarTabela(tabelaProdutos);

        painel.add(botoes, BorderLayout.NORTH);
        painel.add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelCarrinho() {
        JPanel painel = criarCard("Carrinho");

        modeloCarrinho = new DefaultTableModel(
                new Object[]{"Produto", "Quantidade", "Preço Unit.", "Subtotal"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaCarrinho = new JTable(modeloCarrinho);
        estilizarTabela(tabelaCarrinho);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        botoes.setBackground(Color.WHITE);

        JButton btnRemover = criarBotao("Remover Item", Color.RED.darker());
        JButton btnFinalizar = criarBotao("Finalizar Venda", SUCCESS);
        JButton btnLimpar = criarBotao("Cancelar Carrinho", new Color(100, 116, 139));

        btnRemover.addActionListener(e -> removerItemCarrinho());
        btnFinalizar.addActionListener(e -> finalizarVenda());
        btnLimpar.addActionListener(e -> cancelarCarrinho());

        botoes.add(btnRemover);
        botoes.add(btnFinalizar);
        botoes.add(btnLimpar);

        painel.add(new JScrollPane(tabelaCarrinho), BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarRodape() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(BACKGROUND);

        labelTotal = new JLabel("Total: R$ 0,00");
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 22));
        labelTotal.setForeground(SUCCESS);

        JLabel banco = new JLabel("Banco: SQLite conectado");
        banco.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        banco.setForeground(PRIMARY);

        painel.add(labelTotal, BorderLayout.WEST);
        painel.add(banco, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarCard(String titulo) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBackground(Color.WHITE);
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel label = new JLabel(titulo);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(PRIMARY);

        painel.add(label, BorderLayout.WEST);

        return painel;
    }

    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Segoe UI", Font.BOLD, 13));
        botao.setPreferredSize(new Dimension(170, 38));
        return botao;
    }

    private void estilizarTabela(JTable tabela) {
        tabela.setRowHeight(28);
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabela.getTableHeader().setBackground(PRIMARY);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabela.setSelectionBackground(new Color(219, 234, 254));
        tabela.setSelectionForeground(Color.BLACK);
    }

    private void cadastrarProduto() {
        try {
            JTextField nome = new JTextField();
            JTextField descricao = new JTextField();
            JTextField preco = new JTextField();
            JTextField estoque = new JTextField();
            JTextField categoria = new JTextField();

            JPanel painel = new JPanel(new GridLayout(0, 1, 8, 8));
            painel.add(new JLabel("Nome:"));
            painel.add(nome);
            painel.add(new JLabel("Descrição:"));
            painel.add(descricao);
            painel.add(new JLabel("Preço:"));
            painel.add(preco);
            painel.add(new JLabel("Estoque:"));
            painel.add(estoque);
            painel.add(new JLabel("Categoria:"));
            painel.add(categoria);

            int resultado = JOptionPane.showConfirmDialog(
                    this, painel, "Cadastrar Produto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (resultado != JOptionPane.OK_OPTION) return;

            Produto produto = new Produto(
                    nome.getText(),
                    descricao.getText(),
                    Double.parseDouble(preco.getText()),
                    Integer.parseInt(estoque.getText()),
                    categoria.getText()
            );

            new ProdutoDAO().inserir(produto);

            atualizarTabelaProdutos();

        } catch (Exception e) {
            mostrarErro("Erro ao cadastrar produto", e);
        }
    }

    private void editarProduto() {
        try {
            int linha = tabelaProdutos.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para editar.");
                return;
            }

            int id = (int) modeloProdutos.getValueAt(linha, 0);
            ProdutoDAO dao = new ProdutoDAO();
            Produto produto = dao.buscarPorId(id);

            JTextField nome = new JTextField(produto.getNome());
            JTextField descricao = new JTextField(produto.getDescricao());
            JTextField preco = new JTextField(String.valueOf(produto.getPreco()));
            JTextField estoque = new JTextField(String.valueOf(produto.getQuantidadeEstoque()));
            JTextField categoria = new JTextField(produto.getCategoria());

            JPanel painel = new JPanel(new GridLayout(0, 1, 8, 8));
            painel.add(new JLabel("Nome:"));
            painel.add(nome);
            painel.add(new JLabel("Descrição:"));
            painel.add(descricao);
            painel.add(new JLabel("Preço:"));
            painel.add(preco);
            painel.add(new JLabel("Estoque:"));
            painel.add(estoque);
            painel.add(new JLabel("Categoria:"));
            painel.add(categoria);

            int resultado = JOptionPane.showConfirmDialog(
                    this, painel, "Editar Produto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (resultado != JOptionPane.OK_OPTION) return;

            produto.setNome(nome.getText());
            produto.setDescricao(descricao.getText());
            produto.setPreco(Double.parseDouble(preco.getText()));
            produto.setQuantidadeEstoque(Integer.parseInt(estoque.getText()));
            produto.setCategoria(categoria.getText());

            dao.atualizar(produto);

            atualizarTabelaProdutos();

        } catch (Exception e) {
            mostrarErro("Erro ao editar produto", e);
        }
    }

    private void excluirProduto() {
        try {
            int linha = tabelaProdutos.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto para excluir.");
                return;
            }

            int id = (int) modeloProdutos.getValueAt(linha, 0);

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir este produto?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta != JOptionPane.YES_OPTION) return;

            new ProdutoDAO().excluir(id);

            atualizarTabelaProdutos();

        } catch (Exception e) {
            mostrarErro("Erro ao excluir produto", e);
        }
    }

    private void adicionarAoCarrinho() {
        try {
            int linha = tabelaProdutos.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um produto.");
                return;
            }

            int id = (int) modeloProdutos.getValueAt(linha, 0);

            Produto produto = new ProdutoDAO().buscarPorId(id);

            String entrada = JOptionPane.showInputDialog(this, "Quantidade:");

            if (entrada == null) return;

            int quantidade = Integer.parseInt(entrada);

            boolean sucesso = vendaAtual.adicionarItem(produto, quantidade);

            if (!sucesso) {
                JOptionPane.showMessageDialog(this, "Não foi possível adicionar o item.");
                return;
            }

            atualizarTabelaCarrinho();

        } catch (Exception e) {
            mostrarErro("Erro ao adicionar ao carrinho", e);
        }
    }

    private void removerItemCarrinho() {
        try {
            int linha = tabelaCarrinho.getSelectedRow();

            if (linha == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um item do carrinho.");
                return;
            }

            vendaAtual.removerItem(linha);

            atualizarTabelaCarrinho();

        } catch (Exception e) {
            mostrarErro("Erro ao remover item", e);
        }
    }

    private void finalizarVenda() {
        try {
            boolean sucesso = vendaAtual.finalizarVenda();

            if (!sucesso) {
                JOptionPane.showMessageDialog(this, "Não foi possível finalizar a venda.");
                return;
            }

            ProdutoDAO dao = new ProdutoDAO();

            for (ItemVenda item : vendaAtual.getItens()) {
                dao.atualizar(item.getProduto());
            }

            JOptionPane.showMessageDialog(
                    this,
                    String.format("Venda finalizada com sucesso!\nTotal: R$ %.2f", vendaAtual.getValorTotal())
            );

            vendaAtual = new Venda();

            atualizarTabelaProdutos();
            atualizarTabelaCarrinho();

        } catch (Exception e) {
            mostrarErro("Erro ao finalizar venda", e);
        }
    }

    private void cancelarCarrinho() {
        vendaAtual = new Venda();
        atualizarTabelaCarrinho();
    }

    private void atualizarTabelaProdutos() {
        try {
            ProdutoDAO dao = new ProdutoDAO();
            List<Produto> produtos = dao.listar();

            modeloProdutos.setRowCount(0);

            for (Produto p : produtos) {
                modeloProdutos.addRow(new Object[]{
                        p.getId(),
                        p.getNome(),
                        p.getDescricao(),
                        String.format("R$ %.2f", p.getPreco()),
                        p.getQuantidadeEstoque(),
                        p.getCategoria()
                });
            }

        } catch (Exception e) {
            mostrarErro("Erro ao carregar produtos", e);
        }
    }

    private void atualizarTabelaCarrinho() {
        modeloCarrinho.setRowCount(0);

        for (ItemVenda item : vendaAtual.getItens()) {
            modeloCarrinho.addRow(new Object[]{
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    String.format("R$ %.2f", item.getProduto().getPreco()),
                    String.format("R$ %.2f", item.calcularSubtotal())
            });
        }

        labelTotal.setText(
                String.format("Total: R$ %.2f", vendaAtual.calcularTotal())
        );
    }

    private void mostrarErro(String mensagem, Exception e) {
        JOptionPane.showMessageDialog(
                this,
                mensagem + ":\n" + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
}