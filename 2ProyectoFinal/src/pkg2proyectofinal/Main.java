package pkg2proyectofinal;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        GestionVehiculo<Vehiculo> gestion = new GestionVehiculo<>();

        Label titulo = new Label("Sistema de Gestión de Vehículos");
        titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label lblMensaje = new Label();

        TextField txtPatente = new TextField();
        txtPatente.setPromptText("Ingrese la Patente");

        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Ingrese la Marca");

        txtPatente.setMaxWidth(250);
        txtMarca.setMaxWidth(250);

        ComboBox<String> cmbCombustible = new ComboBox<>();
        cmbCombustible.getItems().addAll(
                Combustible.DIESEL.name(),
                Combustible.ELECTRICO.name(),
                Combustible.NAFTA.name(),
                Combustible.QUEROSENO.name()
        );
        cmbCombustible.setPromptText("Seleccione Combustible");

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getItems().addAll("Auto", "Avion", "Barco");
        cmbTipo.setPromptText("Seleccione tipo de vehículo");

        Button btnAgregar = new Button("Agregar");
        Button btnEliminar = new Button("Eliminar");
        Button btnActualizar = new Button("Actualizar");
        Button btnOrdenar = new Button("Ordenar por Marca");
        Button btnFiltrar = new Button("Filtrar");
        Button btnAplicar = new Button("Aplicar acción");
        Button btnGuardar = new Button("Guardar");
        Button btnCargar = new Button("Cargar");


        ListView<Vehiculo> lista = new ListView<>();
        lista.setPrefHeight(180);



        btnAgregar.setOnAction(e -> {

            String patente = txtPatente.getText();
            String marca = txtMarca.getText();
            String combustibleTexto = cmbCombustible.getValue();
            String tipo = cmbTipo.getValue();

            if (patente.isEmpty() || marca.isEmpty() || combustibleTexto == null || tipo == null) {
                lblMensaje.setText("Complete todos los campos.");
                return;
            }

            Combustible combustible = Combustible.valueOf(combustibleTexto);

            Vehiculo vehiculo = null;

            if (tipo.equals("Auto")) {
                vehiculo = new Auto(patente, marca, combustible, "N/A", "Generico");
            } else if (tipo.equals("Avion")) {
                vehiculo = new Avion(combustible, 100, marca);
            } else if (tipo.equals("Barco")) {
                vehiculo = new Barco(patente, marca, combustible, "ModeloX", 200.0);
            }

            if (vehiculo != null) {
                gestion.crear(vehiculo);
                lista.getItems().add(vehiculo);
                lblMensaje.setText("Vehículo agregado correctamente.");


                txtPatente.clear();
                txtMarca.clear();
                cmbCombustible.setValue(null);
                cmbTipo.setValue(null);
            }
        });

        btnEliminar.setOnAction(e -> {

            Vehiculo seleccionado = lista.getSelectionModel().getSelectedItem();

            if (seleccionado != null) {
                try {
                    gestion.eliminar(seleccionado);
                    lista.getItems().remove(seleccionado);
                    lblMensaje.setText("Vehículo eliminado.");
                } catch (VehiculoNoEncontrado ex) {
                    lblMensaje.setText(ex.getMessage());
                }
            } else {
                lblMensaje.setText("Seleccione un vehículo.");
            }
        });

        btnActualizar.setOnAction(e -> {

            int indice = lista.getSelectionModel().getSelectedIndex();

            if (indice >= 0) {

                String patente = txtPatente.getText();
                String marca = txtMarca.getText();
                String combustibleTexto = cmbCombustible.getValue();
                String tipo = cmbTipo.getValue();

                if (patente.isEmpty() || marca.isEmpty() || combustibleTexto == null || tipo == null) {
                    lblMensaje.setText("Complete todos los campos.");
                    return;
                }

                Combustible combustible = Combustible.valueOf(combustibleTexto);

                Vehiculo actualizado = null;

                if (tipo.equals("Auto")) {
                    actualizado = new Auto(patente, marca, combustible, "N/A", "Generico");
                } else if (tipo.equals("Avion")) {
                    actualizado = new Avion(combustible, 100, marca);
                } else if (tipo.equals("Barco")) {
                    actualizado = new Barco(patente, marca, combustible, "ModeloX", 200.0);
                }

                if (actualizado != null) {
                    gestion.obtenerLista().set(indice, actualizado);
                    lista.getItems().set(indice, actualizado);
                    lblMensaje.setText("Vehículo actualizado.");
                }

            } else {
                lblMensaje.setText("Seleccione un vehículo para actualizar.");
            }
        });

        btnOrdenar.setOnAction(e -> {
            gestion.obtenerLista()
                    .sort((v1, v2) -> v1.getMarca().compareToIgnoreCase(v2.getMarca()));

            lista.getItems().setAll(gestion.obtenerLista());
            lblMensaje.setText("Lista ordenada por marca.");
        });

        btnFiltrar.setOnAction(e -> {

            String combustibleTexto = cmbCombustible.getValue();

            if (combustibleTexto == null) {
                lblMensaje.setText("Seleccione un combustible para filtrar.");
                return;
            }

            Combustible combustible = Combustible.valueOf(combustibleTexto);

            lista.getItems().clear();

            for (Vehiculo v : gestion.obtenerLista()) {
                if (v.getCombustible() == combustible) {
                    lista.getItems().add(v);
                }
            }

            lblMensaje.setText("Filtro aplicado.");
        });

        btnAplicar.setOnAction(e -> {
            gestion.aplicarAccion(v -> v.acelerar());
            lblMensaje.setText("Acción aplicada a todos los vehículos.");
        });

        btnGuardar.setOnAction(e -> {
            try {
                gestion.guardarBinario("vehiculos.dat");
                lblMensaje.setText("Datos guardados correctamente.");
            } catch (Exception ex) {
                lblMensaje.setText("Error al guardar: " + ex.getMessage());
            }
        });

        btnCargar.setOnAction(e -> {
            try {
                gestion.cargarBinario("vehiculos.dat");
                lista.getItems().setAll(gestion.obtenerLista());
                lblMensaje.setText("Datos cargados correctamente.");
            } catch (ArchivoInvalido ex) {
                lblMensaje.setText(ex.getMessage());
            }
        });


        HBox boxProcesos = new HBox(10, btnAgregar, btnEliminar, btnActualizar, btnOrdenar, btnFiltrar, btnAplicar);
        boxProcesos.setAlignment(Pos.CENTER);

        HBox boxArchivos = new HBox(10, btnGuardar, btnCargar);
        boxArchivos.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10,
                titulo,
                cmbTipo,
                txtPatente,
                txtMarca,
                cmbCombustible,
                boxProcesos,
                boxArchivos,
                lista,
                lblMensaje
        );

        layout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(layout, 650, 500);

        stage.setScene(scene);
        stage.setTitle("Gestión de Vehículos");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}