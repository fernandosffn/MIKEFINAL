package fes.aragon.modelo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Items {

    //private Producto producto;
    private Distribuidor distribuidor;

    public ObservableList<Producto> productoArrayList= FXCollections.observableArrayList();


    public Items() {

        this.distribuidor = new Distribuidor();

    }



    public Distribuidor getDistribuidor() {
        return distribuidor;
    }

    public void setDistribuidor(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
    }

    public String getNombreDistribuidor(){
        return this.distribuidor.nombreDistribuidor;
    }
    public String getCorreo(){
        return this.distribuidor.correo;
    }
    public String getTelefono(){
        return this.distribuidor.telefono;
    }
    public String getDireccion(){
        return this.distribuidor.direccion;
    }

    public ObservableList<Producto> getProductoArrayList() {
        return productoArrayList;
    }

    public void setProductoArrayList(ObservableList<Producto> productoArrayList) {
        this.productoArrayList = productoArrayList;
    }
}
