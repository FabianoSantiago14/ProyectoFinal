package pkg2proyectofinal;


public interface Crud<T> {
    void crear(T vehiculo);
    void leer();
    void actualizar(T vehiculo);
    void eliminar(T vehiculo) throws VehiculoNoEncontrado;
    
}
