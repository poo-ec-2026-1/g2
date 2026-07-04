package view;

import model.GrupoPagamentoPix;
import model.ItemVenda;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TelaPagamento extends JDialog {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    private final Color primary = new Color(30, 41, 59);
    private final Color success = new Color(22, 163, 74);
    private final Color background = new Color(241, 245, 249);

    public TelaPagamento(JFrame dono, List<GrupoPagamentoPix> grupos, double total) {
        super(dono, "Pagamento", true);
        setSize(560, 620);
        setLocationRelativeTo(dono);

        JPanel raiz = new JPanel(new BorderLayout(10, 10));
        raiz.setBackground(background);
        raiz.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        raiz.add(criarCabecalho(), BorderLayout.NORTH);
        raiz.add(criarListaBlocos(grupos), BorderLayout.CENTER);
        raiz.add(criarRodape(total), BorderLayout.SOUTH);

        setContentPane(raiz);
    }

    private JPanel criarCabecalho() {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(background);

        JLabel titulo = new JLabel("Pagamento");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titulo.setForeground(primary);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dataHora = new JLabel("Pedido fechado em: "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        dataHora.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dataHora.setForeground(new Color(100, 116, 139));
        dataHora.setAlignmentX(Component.LEFT_ALIGNMENT);

        painel.add(titulo);
        painel.add(Box.createVerticalStrut(4));
        painel.add(dataHora);
        return painel;
    }

    private JScrollPane criarListaBlocos(List<GrupoPagamentoPix> grupos) {
        JPanel lista = new JPanel();
        lista.setLayout(new BoxLayout(lista, BoxLayout.Y_AXIS));
        lista.setBackground(background);
        lista.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        for (GrupoPagamentoPix grupo : grupos) {
            lista.add(criarBlocoPix(grupo));
            lista.add(Box.createVerticalStrut(12));
        }

        JScrollPane scroll = new JScrollPane(lista);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);
        return scroll;
    }

    private JPanel criarBlocoPix(GrupoPagamentoPix grupo) {
        JPanel bloco = new JPanel();
        bloco.setLayout(new BoxLayout(bloco, BoxLayout.Y_AXIS));
        bloco.setBackground(Color.WHITE);
        bloco.setAlignmentX(Component.LEFT_ALIGNMENT);
        bloco.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primary, 2, true),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel titulo = new JLabel("Destinatário categoria: " + grupo.getCategoria() + "s");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titulo.setForeground(primary);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        bloco.add(titulo);
        bloco.add(Box.createVerticalStrut(8));

        for (ItemVenda item : grupo.getItens()) {
            JPanel linha = new JPanel(new BorderLayout());
            linha.setBackground(Color.WHITE);
            linha.setAlignmentX(Component.LEFT_ALIGNMENT);
            linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));

            linha.add(new JLabel(item.getQuantidade() + "x " + item.getProduto().getNome()), BorderLayout.WEST);
            linha.add(new JLabel(String.format(PT_BR, "R$ %.2f", item.calcularSubtotal())), BorderLayout.EAST);
            bloco.add(linha);
        }

        bloco.add(Box.createVerticalStrut(8));

        JLabel subtotal = new JLabel(String.format(PT_BR, "Subtotal: R$ %.2f", grupo.getSubtotal()));
        subtotal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        subtotal.setForeground(success);
        subtotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        bloco.add(subtotal);
        bloco.add(Box.createVerticalStrut(10));

        bloco.add(criarLinhaPix(grupo.getChavePix()));
        return bloco;
    }

    private JPanel criarLinhaPix(String chavePix) {
        JPanel linha = new JPanel(new BorderLayout(8, 0));
        linha.setBackground(Color.WHITE);
        linha.setAlignmentX(Component.LEFT_ALIGNMENT);
        linha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));

        JTextField campoPix = new JTextField(chavePix);
        campoPix.setEditable(false);
        campoPix.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        campoPix.setBackground(new Color(226, 232, 240));

        JButton copiar = new JButton("Copiar Chave");
        copiar.setBackground(primary);
        copiar.setForeground(Color.WHITE);
        copiar.setFocusPainted(false);
        copiar.addActionListener(e -> {
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(chavePix), null);
            JOptionPane.showMessageDialog(this, "Chave Pix copiada para a área de transferência!");
        });

        linha.add(campoPix, BorderLayout.CENTER);
        linha.add(copiar, BorderLayout.EAST);
        return linha;
    }

    private JPanel criarRodape(double total) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBackground(background);
        painel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel labelTotal = new JLabel(String.format(PT_BR, "Total Geral: R$ %.2f", total));
        labelTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        labelTotal.setForeground(success);

        JButton concluir = new JButton("Concluir");
        concluir.setBackground(success);
        concluir.setForeground(Color.WHITE);
        concluir.setFocusPainted(false);
        concluir.addActionListener(e -> dispose());

        painel.add(labelTotal, BorderLayout.WEST);
        painel.add(concluir, BorderLayout.EAST);
        return painel;
    }
}
