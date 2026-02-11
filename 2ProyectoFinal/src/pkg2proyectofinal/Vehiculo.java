package pkg2proyectofinal;

import java.io.Serializable;

public abstract class Vehiculo implements Comparable<Vehiculo>, Serializable {

    private String patente;
    private String marca;
    private Combustible combustible;

    public Vehiculo(String patente, String marca, Combustible combustible) {
        this.patente = patente;
        this.marca = marca;
        this.combustible = combustible;
    }

    public Vehiculo(String marca, Combustible combustible) {
        this("AAA000", marca, combustible);
    }

    public Vehiculo(Combustible combustible) {
        this("AAA000", "GENERICO", combustible);
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public Combustible getCombustible() {
        return combustible;
    }

    public void acelerar() {
        System.out.println("El vehiculo avanza");
    }

    public void frenar() {
        System.out.println("El vehiculo se detiene");
    }

    public void llenarCombustible(Combustible combustible) {
        if (this.combustible == combustible) {
            System.out.println("Se lleno de combustible");
        } else {
            System.out.println("El combustible no es el correcto");
        }
    }

    @Override
    public String toString() {
        return "Patente: " + patente
                + " / Marca: " + marca
                + " / Combustible: " + combustible;
    }

    @Override
    public int compareTo(Vehiculo b) {
        return this.patente.compareTo(b.getPatente());
    }

    //-------------------------persistencia de datos-----------------
    public String toCSV() {
        return getClass().getSimpleName() + ","
                + patente + "," + marca + "," + combustible;
    }

    public String toJSON() {
        return "{"
                + "\"tipo\": \"" + getClass().getSimpleName() + "\", "
                + "\"patente\": \"" + patente + "\", "
                + "\"marca\": \"" + marca + "\", "
                + "\"combustible\": \"" + combustible + "\""
                + "}";
    }

}
