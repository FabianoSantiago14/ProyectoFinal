package pkg2proyectofinal;

public class Avion extends Vehiculo implements Mantenimiento {

    private int capasidadPasajeros;
    private String aerolinea;

    public Avion(String patente, String marca, Combustible combustible, int capasidadPasajeros, String aerolinea) {
        super(patente, marca, combustible);
        this.capasidadPasajeros = capasidadPasajeros;
        this.aerolinea = aerolinea;
    }

    public Avion(String marca, Combustible combustible, int capasidadPasajeros, String aerolinea) {
        this("AAA000", marca, combustible, capasidadPasajeros, aerolinea);
    }

    public Avion(Combustible combustible, int capasidadPasajeros, String aerolinea) {
        this("AAA000", "GENERICO", combustible, capasidadPasajeros, aerolinea);
    }

    @Override
    public void realizarMantenimiento() {
        System.out.println("Se realiza el mantenimiento al avion");
    }

}
