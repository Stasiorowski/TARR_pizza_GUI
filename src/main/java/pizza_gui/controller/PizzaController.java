package pizza_gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PizzaController {

    @FXML
    private TableView<?> tblPizza;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcIngridients;

    @FXML
    private TableColumn<?, ?> tcType;

    @FXML
    private TableColumn<?, ?> tcPrice;

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

    }

}
