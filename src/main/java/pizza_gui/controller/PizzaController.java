package pizza_gui.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pizza_gui.model.Ingredients;
import pizza_gui.model.Pizza;
import pizza_gui.model.PizzaModel;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PizzaController {
    // Aby dodać kolekcję do obiektów do kontrolek FXML korzystamy ObservableLst
    private ObservableList<PizzaModel> pizzas=FXCollections.observableArrayList();

    @FXML
    private TableView<PizzaModel> tblPizza;   // Klasa modelu

    @FXML
    private TableColumn<PizzaModel, String> tcName;   //Klasa modelu, typ danych

    @FXML
    private TableColumn<PizzaModel, String> tcIngridients;

    @FXML
    private TableColumn<PizzaModel, String> tcType;

    @FXML
    private TableColumn<PizzaModel, Double> tcPrice;

    @FXML
    private Label randomPizza;

    @FXML
    private TextArea taBasket;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAdress;

    @FXML
    void cleanAction(MouseEvent event) {

    }

    @FXML
    void orderAction(MouseEvent event) {

        System.out.println("Zamówiono");

    }

}
