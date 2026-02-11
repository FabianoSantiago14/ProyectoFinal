package pkg2proyectofinal;

import java.util.Comparator;


public class CompararCombustible implements Comparator<Vehiculo>{
    
    @Override
    public int compare(Vehiculo a, Vehiculo b) {
        return a.getCombustible().compareTo(b.getCombustible());
    }
    
}
