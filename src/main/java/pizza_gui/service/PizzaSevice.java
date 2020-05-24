package pizza_gui.service;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import pizza_gui.model.Ingredients;
import pizza_gui.model.Pizza;
import pizza_gui.model.PizzaModel;


import java.util.stream.Collectors;

public class PizzaSevice {

    //-----------------------------------------------------------------------------------------------------------------
    //wprowadzić pizze do listy do listy pizzas-------------------
    public ObservableList<PizzaModel> addPizzas(ObservableList<PizzaModel> pizzas){
        for (Pizza pizza : Pizza.values()){
            pizzas.add(new PizzaModel(
                    pizza.getName(),
                    pizza.getIngredients().stream().map(Ingredients::getName).collect(Collectors.joining(",")),
                    (pizza.getIngredients().stream().anyMatch(Ingredients::isSpicy) ? "ostra " : " ")
                            +
                            (pizza.getIngredients().stream().noneMatch(Ingredients::isMeat) ? "wege " : " "),
                    pizza.getIngredients().stream().mapToDouble(Ingredients::getPrice).sum()
            ));
        }
        return pizzas;

    }

    //-----------------------------------------------------------------------------------------------------------------
        //metoda konfigurująca kolumny TableView i wprowadzająca dane z ObservableList -------------------

    public void insertPizzasToTable(
                    TableView<PizzaModel> tblPizza,
            TableColumn<PizzaModel, String> tcName,
            TableColumn<PizzaModel, String> tcIngredients,
            TableColumn<PizzaModel, String> tcType,
            TableColumn<PizzaModel, Double> tcPrice,
            ObservableList<PizzaModel> pizzas
    ){
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tblPizza.setItems(pizzas);

    }

    //-----------------------------------------------------------------------------------------------------------------


}
