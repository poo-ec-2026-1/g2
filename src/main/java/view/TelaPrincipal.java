package view;

import controller.ProdutoController;
import controller.VendaController;
import model.GrupoPagamentoPix;
import model.ItemVenda;
import model.Produto;
import model.ProdutoException;
import model.VendaException;
import service.ProdutoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class TelaPrincipal extends JFrame {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final Color PRIMARY = new Color(30, 41, 59);
    private final Color SUCCESS = new Color(22, 163, 74);
    private final Color BACKGROUND = new Color(241, 245, 249);

    private JTable tabelaProdutos;
    private JTable tabelaCarrinho;

    private DefaultTableModel modeloProdutos;
    private DefaultTableModel modeloCarrinho;

    private JLabel labelTotal;

    private final ProdutoController produtoController;
    private final VendaController vendaController;

    public TelaPrincipal() {
        ProdutoController produtoControllerTemp;
        VendaController vendaControllerTemp;
        try {
            ProdutoService produtoService = new ProdutoService();
            produtoControllerTemp = new ProdutoController(produtoService);
            vendaControllerTemp = new VendaController(produtoService);
        } catch (SQLException e) {
            throw new RuntimeException("Não foi possível conectar ao banco de dados.", e);
        }
        this.produtoController = produtoControllerTemp;
        this.vendaController = vendaControllerTemp;

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
                new Object[]{"ID", "Nome", "Descrição", "Preço", "Estoque", "Categoria", "Promoção"}, 0
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
            JTextField qtdPromo = new JTextField("0");
            JTextField precoPromo = new JTextField("0");
            JTextField chavePix = new JTextField();

            JPanel painel = montarFormularioProduto(nome, descricao, preco, estoque, categoria,
                    qtdPromo, precoPromo, chavePix);

            int resultado = JOptionPane.showConfirmDialog(
                    this, painel, "Cadastrar Produto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (resultado != JOptionPane.OK_OPTION) return;

            produtoController.cadastrar(
                    nome.getText(),
                    descricao.getText(),
                    Double.parseDouble(preco.getText()),
                    Integer.parseInt(estoque.getText()),
                    categoria.getText(),
                    parseIntOuZero(qtdPromo.getText()),
                    parseDoubleOuZero(precoPromo.getText()),
                    chavePix.getText().isBlank() ? null : chavePix.getText()
            );

            atualizarTabelaProdutos();

        } catch (ProdutoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível cadastrar", JOptionPane.WARNING_MESSAGE);
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
            Produto produto = produtoController.buscarPorId(id);

            JTextField nome = new JTextField(produto.getNome());
            JTextField descricao = new JTextField(produto.getDescricao());
            JTextField preco = new JTextField(String.valueOf(produto.getPreco()));
            JTextField estoque = new JTextField(String.valueOf(produto.getQuantidadeEstoque()));
            JTextField categoria = new JTextField(produto.getCategoria());
            JTextField qtdPromo = new JTextField(String.valueOf(produto.getQtdPromo()));
            JTextField precoPromo = new JTextField(String.valueOf(produto.getPrecoPromo()));
            JTextField chavePix = new JTextField(produto.getChavePix() != null ? produto.getChavePix() : "");

            JPanel painel = montarFormularioProduto(nome, descricao, preco, estoque, categoria,
                    qtdPromo, precoPromo, chavePix);

            int resultado = JOptionPane.showConfirmDialog(
                    this, painel, "Editar Produto",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
            );

            if (resultado != JOptionPane.OK_OPTION) return;

            produtoController.editar(
                    id,
                    nome.getText(),
                    descricao.getText(),
                    Double.parseDouble(preco.getText()),
                    Integer.parseInt(estoque.getText()),
                    categoria.getText(),
                    parseIntOuZero(qtdPromo.getText()),
                    parseDoubleOuZero(precoPromo.getText()),
                    chavePix.getText().isBlank() ? null : chavePix.getText()
            );

            atualizarTabelaProdutos();

        } catch (ProdutoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível editar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            mostrarErro("Erro ao editar produto", e);
        }
    }

    private JPanel montarFormularioProduto(JTextField nome, JTextField descricao, JTextField preco,
                                            JTextField estoque, JTextField categoria, JTextField qtdPromo,
                                            JTextField precoPromo, JTextField chavePix) {
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
        painel.add(new JLabel("Qtd. p/ promoção (0 = sem promoção):"));
        painel.add(qtdPromo);
        painel.add(new JLabel("Preço promocional (do bloco):"));
        painel.add(precoPromo);
        painel.add(new JLabel("Chave Pix da categoria:"));
        painel.add(chavePix);
        return painel;
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

            produtoController.excluir(id);

            atualizarTabelaProdutos();

        } catch (ProdutoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível excluir", JOptionPane.WARNING_MESSAGE);
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
            Produto produto = produtoController.buscarPorId(id);

            String entrada = JOptionPane.showInputDialog(this, "Quantidade:");

            if (entrada == null) return;

            int quantidade = Integer.parseInt(entrada);
            vendaController.adicionarItem(produto, quantidade);

            atualizarTabelaCarrinho();

        } catch (VendaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível adicionar", JOptionPane.WARNING_MESSAGE);
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

            vendaController.removerItem(linha);

            atualizarTabelaCarrinho();

        } catch (VendaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível remover", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            mostrarErro("Erro ao remover item", e);
        }
    }

    private void finalizarVenda() {
        try {
            double totalFinalizado = vendaController.getVendaAtual().calcularTotal();
            List<GrupoPagamentoPix> recibo = vendaController.finalizarVenda();

            new TelaPagamento(this, recibo, totalFinalizado).setVisible(true);

            atualizarTabelaProdutos();
            atualizarTabelaCarrinho();

        } catch (VendaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Não foi possível finalizar", JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            mostrarErro("Erro ao finalizar venda", e);
        }
    }

    private void cancelarCarrinho() {
        vendaController.cancelarVenda();
        atualizarTabelaCarrinho();
    }

    private void atualizarTabelaProdutos() {
        try {
            List<Produto> produtos = produtoController.listar();

            modeloProdutos.setRowCount(0);

            for (Produto p : produtos) {
                String promo = p.temPromocao()
                        ? String.format(PT_BR, "Leve %d por R$ %.2f", p.getQtdPromo(), p.getPrecoPromo())
                        : "-";

                modeloProdutos.addRow(new Object[]{
                        p.getId(),
                        p.getNome(),
                        p.getDescricao(),
                        String.format(PT_BR, "R$ %.2f", p.getPreco()),
                        p.getQuantidadeEstoque(),
                        p.getCategoria(),
                        promo
                });
            }

        } catch (Exception e) {
            mostrarErro("Erro ao carregar produtos", e);
        }
    }

    private void atualizarTabelaCarrinho() {
        modeloCarrinho.setRowCount(0);

        for (ItemVenda item : vendaController.getVendaAtual().getItens()) {
            modeloCarrinho.addRow(new Object[]{
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    String.format(PT_BR, "R$ %.2f", item.getProduto().getPreco()),
                    String.format(PT_BR, "R$ %.2f", item.calcularSubtotal())
            });
        }

        labelTotal.setText(
                String.format(PT_BR, "Total: R$ %.2f", vendaController.getVendaAtual().calcularTotal())
        );
    }

    private int parseIntOuZero(String texto) {
        return (texto == null || texto.isBlank()) ? 0 : Integer.parseInt(texto);
    }

    private double parseDoubleOuZero(String texto) {
        return (texto == null || texto.isBlank()) ? 0.0 : Double.parseDouble(texto);
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
