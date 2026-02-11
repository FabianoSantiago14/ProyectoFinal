package pkg2proyectofinal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class GestionVehiculo<T extends Vehiculo> implements Crud<T>, Iterable<T> {

    private ArrayList<T> vehiculos;

    public GestionVehiculo() {
        this.vehiculos = new ArrayList<>();
    }
    
    public List<T> obtenerLista() {
        return vehiculos;
    }

    //--------------CRUD----------------------
    @Override
    public void crear(T vehiculo) {
        vehiculos.add(vehiculo);
    }

    @Override
    public void leer() {
        for (T vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    @Override
    public void actualizar(T vehiculo) {
        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getPatente().equals(vehiculo.getPatente())) {
                vehiculos.set(i, vehiculo);
            }
        }

    }

//    @Override
//    public void eliminar(T vehiculo) {        
//        vehiculos.remove(vehiculo);
//    }
    
    @Override
    public void eliminar(T vehiculo) throws VehiculoNoEncontrado {
        if (!vehiculos.remove(vehiculo)) {
            throw new VehiculoNoEncontrado("El vehiculo no existe");
        }
    }

    //-----------Iterator--------------------
    @Override
    public Iterator<T> iterator() {
        return new IteratorVehiculo<>(vehiculos);
    }

    //------------Ordenamiento---------------
    public void ordenarNatural() {
        Collections.sort(vehiculos);
    }

    public void ordenarMarca() {
        Collections.sort(vehiculos, new CompararMarca());
    }

    public void ordenarCombustible() {
        Collections.sort(vehiculos, new CompararCombustible());
    }

    //----------Wildcards---------------------
    public List<Vehiculo> filtrarCombustible(List<? extends Vehiculo> list, Combustible combustible) {
        List<Vehiculo> resultado = new ArrayList<>();

        for (Vehiculo vehiculo : list) {
            if (vehiculo.getCombustible() == combustible) {
                resultado.add(vehiculo);
            }
        }

        return resultado;

    //-------------Interfaces funcionales-------------
        /*
    Consumer<T> → recibe algo, no devuelve nada
    Function<T, R> → recibe algo, devuelve algo
    Predicate<T> → devuelve true/false
         */
    }

    public void aplicarAccion(Consumer<? super T> accion) {
        vehiculos.forEach(accion);
    }
    
    //-----------------Persistencia de Datos--------------------------------
    //------------------Binario-------------------------------
    public void guardarBinario(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        
        if (archivo.exists()) {
            System.out.println("El archivo ya existe: " + archivo.getName());
            return;
        }
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(vehiculos);
            System.out.println("El archivo se guardo correctamente");
        } catch (IOException e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
        
    }
    
    public void cargarBinario(String nombreArchivo) throws ArchivoInvalido{
        File archivo = new File(nombreArchivo);
        
        if (!archivo.exists()) {
            throw new ArchivoInvalido("El archivo no existe");
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            vehiculos = (ArrayList<T>) ois.readObject();
            System.out.println("Archivo cargado correctamente");
            
        } catch(IOException  | ClassNotFoundException e) {
            throw new ArchivoInvalido("Error al cargar el archivo");
        }
    }
    //-----------------------------CSV--------------------------------------
    public void guardarCSV(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        
        if (archivo.exists()) {
            System.out.println("El archivo ya exsite");
            return;
        }
        
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("tipo,patente,marca,combustible\n");
            
            for (T v : vehiculos) {
                if (v instanceof Vehiculo) {
                    writer.write(((Vehiculo) v).toCSV() + "\n");
                }
            }
            
            System.out.println("El CSV se guardo correctamente");
            
        } catch (IOException e) {
            System.out.println("Error al guardar CSV: " + e.getMessage() );
        }
        
    }
    //-------------------------TXT----------------------------------------
    public void exportarTXT(String nombreArchivo, Predicate<T> filtro) {
        File archivo = new File(nombreArchivo);
        
        try (FileWriter writer = new FileWriter(archivo)) {
            
            writer.write("Listado de Vehiculos\n");
            writer.write("---------------------\n");
            
            for (T v : vehiculos) {
                if (filtro.test(v)) { 
                    writer.write(v.toString() + "\n");
                }
            }
            System.out.println("El archivo TXT fue exportado correctamente");
            
        } catch (IOException e) {
            System.out.println("Error al exportar TXT: " + e.getMessage());
        }
        
    }
    
    //-------------------------JSON---------------------------------
    public void guardarJSON(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        
        if (archivo.exists()) {
            System.out.println("El archivo JSON ya existe");
            return;
        }
        
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("[\n");
            
            for (int i = 0; i < vehiculos.size(); i++) {
                T v = vehiculos.get(i);
                
                if (v instanceof Vehiculo) {
                    writer.write("  " + ((Vehiculo) v).toJSON());
                    
                    if (i < vehiculos.size() - 1) {
                        writer.write(",");
                    }
                    writer.write("\n");
                }
                
                
            }
            writer.write("]");
            System.out.println("El JSON se guardo correctamente");
        } catch (IOException e) {
            System.out.println("Error al guardar el JSON: " + e.getMessage());
        }
    } 
}
    

