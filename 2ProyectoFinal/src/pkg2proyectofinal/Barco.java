package pkg2proyectofinal;

public class Barco extends Vehiculo implements Mantenimiento{

    private String motor;
    private double precio;

    public Barco(String patente, String marca, Combustible combustible, String motor, double precio) {
        super(patente, marca, combustible);
        this.motor = motor;
        this.precio = precio;
    }

    public Barco(String marca, Combustible combustible, String motor, double precio) {
        this("AAA000",marca,combustible,motor,precio);
    }

    public Barco(Combustible combustible, String motor, double precio) {
        this("AAA000","GENERICO",combustible,motor,precio);
    }

    @Override
    public void realizarMantenimiento() {
        System.out.println("Se realiza mantenimiento al barco");
    }

}
