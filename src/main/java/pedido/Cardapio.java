package pedido;

import ingredientes.Ingrediente;
import java.util.TreeMap;

public class Cardapio{
    private final TreeMap<Ingrediente,Double> precos ;

    public Cardapio(){
        this.precos= new TreeMap<>();
    }

    public TreeMap<Ingrediente, Double> getPrecos(){
        return this.precos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente,Double preco){
        if (preco <= 0)
            throw new IllegalArgumentException("Preco invalido.");
        precos.put(ingrediente, preco);
    }

    public boolean atualizarIngrediente(Ingrediente ingrediente,Double preco){
        if (preco <= 0)
            throw new IllegalArgumentException("Preco invalido.");
        if (!precos.containsKey(ingrediente))
            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        precos.replace(ingrediente, preco);
        return true;
    }

    public boolean removerIngrediente(Ingrediente ingrediente){
        if (!precos.containsKey(ingrediente))
            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        precos.remove(ingrediente);
        return true;
    }

    public Double buscarPreco(Ingrediente ingrediente){
        if (!precos.containsKey(ingrediente))
            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        return precos.get(ingrediente);
    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

}
