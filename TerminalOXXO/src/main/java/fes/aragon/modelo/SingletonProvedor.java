package fes.aragon.modelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
public class SingletonProvedor {
    private static SingletonProvedor singletonProvedor;
    private Integer indice;

    private ObservableList<Items> listas;

    private SingletonProvedor(){

        listas = FXCollections.observableArrayList();
    }

    public static SingletonProvedor getInstance(){
        if(singletonProvedor == null){
            singletonProvedor = new SingletonProvedor();
        }
        return singletonProvedor;
    }

    public ObservableList<Items> getLista(){
        return listas;
    }

    public ArrayList<Items> getConversion(){
        return new ArrayList<>(listas);
    }

    public Integer getIndice() {
        return indice;
    }

    public void setIndice(Integer indice) {
        this.indice = indice;
    }
}