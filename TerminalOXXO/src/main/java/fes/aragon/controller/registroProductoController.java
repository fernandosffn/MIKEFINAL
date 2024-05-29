package fes.aragon.controller;

import fes.aragon.modelo.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class registroProductoController {
    private Integer indice;
    @FXML
    private Button btnAbrirImagen;

    @FXML
    private Button btnGuardar;

    @FXML
    private ImageView imgImagen;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtFechaCaducidad;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecioUnitario;

    @FXML
    private TextField txtPrecioVenta;
    private File selectedFile;
    @FXML
    void abrirImagen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("imagen", "*.png"),
                new FileChooser.ExtensionFilter("imagen JPG", "*.jpg")
        );
        this.selectedFile = fileChooser.showOpenDialog(
                this.btnGuardar.getScene().getWindow());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                Image imagen = new Image(fo);
                this.imgImagen.setImage(imagen);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void guardaProducto(ActionEvent event) {

        Producto producto=new Producto();
        producto.nombre=this.txtNombre.getText();
        producto.cantidad=this.txtCantidad.getText();
        producto.fechaCaducidad=this.txtFechaCaducidad.getText();
        producto.precioUnitario=this.txtPrecioUnitario.getText();
        producto.precioVenta=this.txtPrecioVenta.getText();
//        provedor.getProducto().setNombre(txtNombre.getText());
//        provedor.getProducto().setFechaCaducidad(txtFechaCaducidad.getText());
//        provedor.getProducto().setCantidad(txtCantidad.getText());
//        provedor.getProducto().setPrecioUnitario(txtPrecioUnitario.getText());
//        provedor.getProducto().setPrecioVenta(txtPrecioVenta.getText());
        if (selectedFile != null) {
            try {
                FileInputStream fo = new FileInputStream(selectedFile);
                Image imagen = new Image(fo);
                SerializableImage img = new SerializableImage();
                img.setImage(imagen);
               // provedor.getProducto().setImagen(img);
              //  SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList.set(Image, img);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }

        if (indice == null) {
            SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList.add(producto);
        }else{
            SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList.set(indice, producto);
//            SingletonProducto.getInstance().getLista().
//                    set(indice, provedor.getProducto());
            Stage stage = (Stage) this.btnGuardar.getScene().getWindow();
            stage.close();
        }

        txtNombre.clear();
        txtFechaCaducidad.clear();
        txtCantidad.clear();
        txtPrecioUnitario.clear();
        txtPrecioVenta.clear();
        imgImagen.setImage(null);
    }
    public void indiceProducto(int indice){
        this.indice = indice;
        Producto producto = SingletonProvedor.getInstance().getLista().get(SingletonProvedor.getInstance().getIndice()).productoArrayList.get(indice);
        txtNombre.setText(producto.getNombre());
        txtFechaCaducidad.setText(producto.getFechaCaducidad());
        txtCantidad.setText(producto.getCantidad());
        txtPrecioUnitario.setText(producto.getPrecioUnitario());
        txtPrecioVenta.setText(producto.getPrecioVenta());
       //System.out.println(producto.getImagen());
        //imgImagen.setImage(producto.getImagen().getImage()); //Usuarios -> Imagen / SerializableImage ->Image
    }
}
