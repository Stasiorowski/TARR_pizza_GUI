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
import pizza_gui.service.PizzaSevice;

import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PizzaController {
    // Aby dodać kolekcję do obiektów do kontrolek FXML korzystamy ObservableLst
    private ObservableList<PizzaModel> pizzas=FXCollections.observableArrayList();
    private PizzaSevice pizzaSevice=new PizzaSevice();

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
    private Label lblSum;

    @FXML
    private TextArea taBasket;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfAdress;

    @FXML
    void cleanAction(MouseEvent event) {
        pizzaSevice.clearOrder(taBasket, tfPhone,tfAdress, lblSum);
    }

    @FXML
    void orderAction(MouseEvent event) {
        pizzaSevice.getOrder(tfPhone, tfAdress,taBasket,lblSum);
        System.out.println("Zamówiono");
        System.out.println("Adres zamowienia "+pizzaSevice.isAddressValid(tfAdress.getText()));
        System.out.println("telefon kontaktowy"+pizzaSevice.isPhoneValid(tfPhone.getText()));
    }

    @FXML
    void selectPizzaAction(MouseEvent mouseEvent) {
        pizzaSevice.addToBasket(tblPizza, taBasket, lblSum);
    }

    public void initialize(){
        //wywoływanie metod zaimplementowanych w logice biznesowej aplikacji
        pizzas=pizzaSevice.addPizzas(pizzas);
        pizzaSevice.insertPizzasToTable(tblPizza,tcName,tcIngridients,tcType,tcPrice,pizzas);
        pizzaSevice.pizzaOfTheDayGenerator(pizzas, randomPizza);

    }

}
