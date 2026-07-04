package service;

import model.GrupoPagamentoPix;
import model.ItemVenda;
import model.Produto;
import model.Venda;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VendaService {

    private final ProdutoService produtoService;

    public VendaService(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public Venda novaVenda() {
        return new Venda();
    }

    public void adicionarItem(Venda venda, Produto produto, int quantidade) {
        venda.adicionarItem(produto, quantidade);
    }

    public void removerItem(Venda venda, int index) {
        venda.removerItem(index);
    }
    public List<GrupoPagamentoPix> finalizarVenda(Venda venda) throws SQLException {
        venda.finalizarVenda();

        for (ItemVenda item : venda.getItens()) {
            produtoService.atualizarEstoque(item.getProduto());
        }

        return agruparPorChavePix(venda);
    }

    public List<GrupoPagamentoPix> agruparPorChavePix(Venda venda) {
        Map<String, GrupoPagamentoPix> grupos = new LinkedHashMap<>();

        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();
            String chave = (produto.getChavePix() != null && !produto.getChavePix().isBlank())
                    ? produto.getChavePix()
                    : "chave-nao-definida";

            grupos.computeIfAbsent(chave, k -> new GrupoPagamentoPix(chave, produto.getCategoria()))
                    .adicionar(item);
        }

        return new ArrayList<>(grupos.values());
    }
}
