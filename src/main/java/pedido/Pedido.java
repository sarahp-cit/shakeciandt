package pedido;

import ingredientes.Adicional;
import produto.Shake;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pedido{

    private final int id;
    private final ArrayList<ItemPedido> itens;
    private final Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens,Cliente cliente){
        this.id = id;
        this.itens=itens;
        this.cliente=cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId(){
        return this.id;
    }

    public Cliente getCliente(){
        return this.cliente;
    }

    public double calcularTotal(Cardapio cardapio){
        double total = 0.0;
        for(ItemPedido item:itens){
            Shake shake = item.getShake();
            double base = cardapio.buscarPreco(shake.getBase()) * shake.getTipoTamanho().multiplicador;
            double adicionaisTotal = 0.0;
            if(shake.getAdicionais()!= null){
                for(Adicional adicional:shake.getAdicionais()){
                    adicionaisTotal += cardapio.buscarPreco(adicional);
                }
            }
            total += (base + adicionaisTotal) * item.getQuantidade();
        }
        return total;
    }

    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado){
        List<Shake> shakes = itens.stream().map(ItemPedido::getShake).collect(Collectors.toList());
        if(shakes.contains(itemPedidoAdicionado.getShake())){
            alterarQuantidade(itemPedidoAdicionado, itemPedidoAdicionado.getQuantidade());
        } else{
            itens.add(itemPedidoAdicionado);
        }
    }

    public boolean removeItemPedido(ItemPedido itemPedidoRemovido) {
        List<Shake> shakes = itens.stream().map(ItemPedido::getShake).collect(Collectors.toList());
        if(shakes.contains(itemPedidoRemovido.getShake())){
            int index = getIndex(itemPedidoRemovido);
            if(itens.get(index).getQuantidade() > 1) {
                alterarQuantidade(itemPedidoRemovido, -1);
            }
            else
                itens.remove(itens.get(index));
        } else {
            throw new IllegalArgumentException("Item nao existe no pedido.");
        }
        return false;
    }

    private void alterarQuantidade (ItemPedido pedido, int valorAlteracao){
        int index = getIndex(pedido);
        int quantidade = itens.get(index).getQuantidade() + valorAlteracao;
        itens.get(index).setQuantidade(quantidade);
    }

    private int getIndex(ItemPedido pedido){
        int index = 0;
        for(ItemPedido item:itens){
            if(item.getShake().equals(pedido.getShake())){
                index = itens.indexOf(item);
            }
        }
        return index;
    }

    @Override
    public String toString() {
        return this.itens + " - " + this.cliente;
    }
}
