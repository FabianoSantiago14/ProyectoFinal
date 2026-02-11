package pkg2proyectofinal;

import java.util.Comparator;


public class CompararMarca implements Comparator<Vehiculo>{
    
    @Override
    public int compare(Vehiculo a, Vehiculo b) {
        return a.getMarca().compareTo(b.getMarca());
    }
    
}
