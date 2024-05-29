package fes.aragon.controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fes.aragon.modelo.Items;

import fes.aragon.modelo.Producto;
import fes.aragon.modelo.SingletonProvedor;
import javafx.collections.ObservableList;
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
import javafx.collections.FXCollections;

public class tablaProductoController implements Initializable{
    private Integer indiceProd;

    @FXML
    private TableColumn<Producto, String> clmCantidad;

    @FXML
    private TableColumn<Producto, String> clmFechaCaducidad;

    @FXML
    private TableColumn<Producto, String> clmNombre;

    @FXML
    private TableColumn<Items, String> clmOperaciones;

    @FXML
    private TableColumn<Producto, String> clmPrecioUnitario;

    @FXML
    private TableColumn<Producto, String> clmPrecioVenta;

    @FXML
    private FontAwesomeIconView iconAbrirProducto;

    @FXML
    private FontAwesomeIconView iconGuardarProducto;

    @FXML
    private FontAwesomeIconView iconNuevoProducto;
    //movi el provedor

    @FXML
    private TableView<Producto> tblProducto;

    @FXML
    void eventoAbrirProducto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //       fileChooser.getExtensionFilters().addAll(
        //               new FileChooser.ExtensionFilter("FES", "*.fes"));
        File selectedFile = fileChooser.showOpenDialog(this.iconAbrirProducto.getScene().
                getWindow());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                ObjectInputStream entrada = new ObjectInputStream(fo);
                ArrayList<Items> datos = (ArrayList<Items>) entrada.readObject();
                SingletonProvedor.getInstance().getLista().clear();
                for (Items us:datos) {
                    //System.out.println(us.getImagen());
                    SingletonProvedor.getInstance().getLista().add(us);
                }
            }catch (IOException | ClassNotFoundException e) { //+FileNotFound
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void eventoGuardarProdcuto(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        //       fileChooser.getExtensionFilters().addAll(
        //               new FileChooser.ExtensionFilter("FES", "*.fes"));
        File selectedFile = fileChooser.showSaveDialog(this.iconAbrirProducto.getScene().
                getWindow());
        if (selectedFile != null) {
            try {
                FileOutputStream fo = new FileOutputStream(selectedFile);
                ObjectOutputStream salida = new ObjectOutputStream(fo);
                ArrayList<Items> datos = SingletonProvedor.getInstance().getConversion();
                for (Items us : datos) {
                    //System.out.println(us.getImagen());
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
    void eventoNuevoProducto(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/fes/aragon/xml/registro_de_producto.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            stage.getOwner();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.clmNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        this.clmFechaCaducidad.setCellValueFactory(new PropertyValueFactory<>("fechaCaducidad"));
        this.clmCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        this.clmPrecioUnitario.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        this.clmPrecioVenta.setCellValueFactory(new PropertyValueFactory<>("precioVenta"));
        //movi el provedor
//        ObservableList temp=FXCollections.observableArrayList();
//        for(int i=0;i<SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList.size();i++){
//            temp.add(SingletonProvedor.getInstance().getLista().get(indiceProd).productoArrayList.get(i));
//        }

        tblProducto.setItems(SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList);
        //movi el provedor
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

                        FontAwesomeIconView distribuidorIcono = new FontAwesomeIconView(FontAwesomeIcon.BARS);
                        distribuidorIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744");


                        FontAwesomeIconView modificarIcono = new FontAwesomeIconView(FontAwesomeIcon.PENCIL);
                        modificarIcono.setGlyphStyle("-fx-cursor:hand;" + "-glyph-size:28px;" + "-fx-fill:#ff1744");
                        borrarIcono.setOnMouseClicked((MouseEvent evento) -> {
                            int indice = tblProducto.getSelectionModel().
                                    getSelectedIndex();
                            SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList.remove(indice);
                        });
                        modificarIcono.setOnMouseClicked((MouseEvent evento) -> {
                            modificarProducto(tblProducto.getSelectionModel().
                                    getSelectedIndex());

                        });


                        HBox hBox = new HBox(modificarIcono, borrarIcono);
                        hBox.setStyle("-fx-alignment:center");
                        HBox.setMargin(modificarIcono, new Insets(2, 2, 0, 3));
                        HBox.setMargin(borrarIcono, new Insets(2, 2, 0, 3));


                        setGraphic(hBox);
                        setText(null);
                    }
                }
            };
            return cel;
        };
        this.clmOperaciones.setCellFactory(celda);
    }
    public void modificarProducto(int indice){
        try {
            FXMLLoader modificar = new FXMLLoader(getClass().
                    getResource("/fes/aragon/xml/registro_de_producto.fxml"));
            Parent parent = (Parent) modificar.load();
            ((registroProductoController) modificar.getController()).indiceProducto(indice);
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
    public void indiceProducto(Integer indice){

        this.indiceProd=indice;

    }

    }


