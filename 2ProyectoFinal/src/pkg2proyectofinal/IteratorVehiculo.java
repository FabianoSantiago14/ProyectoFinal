package pkg2proyectofinal;

import java.util.Iterator;
import java.util.List;


public class IteratorVehiculo<T> implements Iterator<T> {
    private List<T> lista;
    private int indice = 0;
    
    public IteratorVehiculo(List<T> lista) {
        this.lista = lista;
    }
    
    @Override
    public boolean hasNext() {
        return indice < lista.size();
    }
    
    @Override
    public T next() {
        return lista.get(indice++);
    }
    
}
