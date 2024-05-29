package fes.aragon.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Items;
import fes.aragon.modelo.SingletonProvedor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class tablaDistribuidorController implements Initializable {

    @FXML
    private TableColumn<Items, String> clmCorreo;

    @FXML
    private TableColumn<Items, String> clmDireccion;

    @FXML
    private TableColumn<Items, String> clmNombre;

    @FXML
    private TableColumn<Items, String> clmOperaciones;

    @FXML
    private TableColumn<Items, String> clmTelefono;

    @FXML
    private FontAwesomeIconView iconAbrirDistribuidor;

    @FXML
    private FontAwesomeIconView iconGuardarDistribuidor;

    @FXML
    private FontAwesomeIconView iconNuevoDistribuidor;

    @FXML
    private TableView<Items> tblDistribuidor;

    @FXML
    void eventoAbrirDistribuidor(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //       fileChooser.getExtensionFilters().addAll(
        //               new FileChooser.ExtensionFilter("FES", "*.fes"));
        File selectedFile = fileChooser.showOpenDialog(this.iconAbrirDistribuidor.getScene().
                getWindow());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                ObjectInputStream entrada = new ObjectInputStream(fo);
                ArrayList<Items> datos = (ArrayList<Items>) entrada.readObject();
                SingletonProvedor.getInstance().getLista().clear();
                /*for (Items us:datos) {
                    System.out.println(us.getImagen());
                    SingletonProvedor.getInstance().getLista().add(us);
                }*/
            }catch (IOException | ClassNotFoundException e) { //+FileNotFound
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void eventoGuardarDistribuidor(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //       fileChooser.getExtensionFilters().addAll(
        //               new FileChooser.ExtensionFilter("FES", "*.fes"));
        File selectedFile = fileChooser.showSaveDialog(this.iconAbrirDistribuidor.getScene().
                getWindow());
        if (selectedFile != null) {
            try {
                FileOutputStream fo = new FileOutputStream(selectedFile);
                ObjectOutputStream salida = new ObjectOutputStream(fo);
                ArrayList<Items> datos = SingletonProvedor.getInstance().getConversion();
                for (Items us : datos) {
                    System.out.println(us.getDistribuidor().getImagen());
                }
                salida.writeObject(datos);
                salida.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void eventoNuevoDistribuidor(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/xml/registro_de_distribuidor.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombreDistribuidor"));
        this.clmCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        this.clmTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        this.clmDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tblDistribuidor.setItems(SingletonProvedor.getInstance().getLista());
        Callback<TableColumn<Items, String>, TableCell<Items, String>>
                celda = (TableColumn<Items, String> parametros) -> {
            final TableCell<Items, String> cel = new TableCell<>() {
                @Override
                protected void updateItem(String s, boolean b) {
                    super.updateItem(s, b);
                    if (b) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        FontAwesomeIconView borrarIcono = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        borrarIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744");
                        FontAwesomeIconView productoIcono = new FontAwesomeIconView(FontAwesomeIcon.BARS);
                        productoIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744");

                        FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                        modificarIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744");
                        borrarIcono.setOnMouseClicked((MouseEvent evento) -> {
                            int indice = tblDistribuidor.getSelectionModel().
                                    getSelectedIndex();
                            SingletonProvedor.getInstance().getLista().
                                    remove(indice);
                        });
                        modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
                            modificarDistribuidor(tblDistribuidor.getSelectionModel().
                                    getSelectedIndex());
                        });
                        productoIcono.setOnMouseClicked((MouseEvent evento) -> {

                            crearProducto(tblDistribuidor.getSelectionModel().
                                    getSelectedIndex());

                        });
                        HBox hBox = new HBox(modificarIcono, borrarIcono, productoIcono);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(modificarIcono, new Insets(2, 2, 0, 3));
                        HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));
                        HBox.setMargin(productoIcono, new Insets(2, 2, 0, 3));

                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cel;
        };
        this.clmOperaciones.setCellFactory(celda);
    }
    public void modificarDistribuidor(int indice){
        try {
            FXMLLoader modificar = new FXMLLoader(getClass().
                    getResource("/fes/aragon/xml/registro_de_distribuidor.fxml"));
            Parent parent = (Parent) modificar.load();
            ((registroDistribuidorController) modificar.getController()).indiceDistribuidor(indice);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void crearProducto(int indice){
        try {
            SingletonProvedor.getInstance().setIndice(indice);
            FXMLLoader crear = new FXMLLoader(getClass().
                    getResource("/fes/aragon/xml/tabla_producto.fxml"));
            Parent parent = (Parent) crear.load();

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            //((tablaProductoController) crear.getController()).indiceProducto(indice);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}