package br.com.example.fecapayplus.utils;

import java.util.ArrayList;
import java.util.List;

import br.com.example.fecapayplus.data.model.ItemCarrinho;
import br.com.example.fecapayplus.data.model.Produto;

public class CartManager {
    private static CartManager instance;
    private List<ItemCarrinho> itens = new ArrayList<>();
    private CartListener listener;

    public interface CartListener {
        void onCartUpdated(List<ItemCarrinho> itens);
    }

    private CartManager() {}

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addListener(CartListener listener) {
        this.listener = listener;
    }

    public void addItem(Produto produto) {
        // verifica se o item ja está no carrinho
        for (ItemCarrinho item : itens) {
            if (item.getProdutoId() == produto.getId()) {
                item.setQuantidade(item.getQuantidade() + 1);
                produto.setLoja(item.getLoja());
                notifyCartUpdated();
                return;
            }
        }


        itens.add(ItemCarrinho.fromProduto(produto));
        notifyCartUpdated();
    }

    public void updateItemQuantity(int produtoId, int novaQuantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProdutoId() == produtoId) {
                item.setQuantidade(novaQuantidade);
                break;
            }
        }
        notifyCartUpdated();
    }

    public void removeItem(int produtoId) {
        itens.removeIf(item -> item.getProdutoId() == produtoId);
        notifyCartUpdated();
    }

    public List<ItemCarrinho> getItens() {
        return new ArrayList<>(itens);
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            try {
                String cleaned = item.getPreco().replace("R$", "").replace(",", ".").trim();
                total += Double.parseDouble(cleaned) * item.getQuantidade();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    public void clearCart() {
        itens.clear();
        notifyCartUpdated();
    }

    private void notifyCartUpdated() {
        if (listener != null) {
            listener.onCartUpdated(new ArrayList<>(itens));
        }
    }
}