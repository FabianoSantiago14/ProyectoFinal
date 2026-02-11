package pkg2proyectofinal;

public class Auto extends Vehiculo {

    private String color;
    private String modelo;

    public Auto(String patente, String marca, Combustible combustible, String color, String modelo) {
        super(patente, marca, combustible);
        this.color = color;
        this.modelo = modelo;
    }

    public Auto(String marca, Combustible combustible, String color, String modelo) {
        this("AAA000", marca, combustible, color, modelo);
    }

    public Auto(Combustible combustible, String color, String modelo) {
        this("AAA000", "GENERICO", combustible, color, modelo);
    }
}
