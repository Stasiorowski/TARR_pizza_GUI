package pizza_gui.service;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import pizza_gui.model.Ingredients;
import pizza_gui.model.Pizza;
import pizza_gui.model.PizzaModel;


import java.text.NumberFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PizzaSevice {

    //-----------------------------------------------------------------------------------------------------------------
    //wprowadzić pizze do listy do listy pizzas-------------------
    public ObservableList<PizzaModel> addPizzas(ObservableList<PizzaModel> pizzas) {
        for (Pizza pizza : Pizza.values()) {
            pizzas.add(new PizzaModel(
                    pizza.getName(),
                    pizza.getIngredients().stream().map(Ingredients::getName).collect(Collectors.joining(",")),
                    (pizza.getIngredients().stream().anyMatch(Ingredients::isSpicy) ? "ostra " : "")
                            +
                            (pizza.getIngredients().stream().noneMatch(Ingredients::isMeat) ? "wege " : ""),
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
    ) {
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcIngredients.setCellValueFactory(new PropertyValueFactory<>("ingredients"));
        tcType.setCellValueFactory(new PropertyValueFactory<>("type"));
        tcPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        //ustawiaonie języka
        Locale locale = new Locale("pl", "PL");

        NumberFormat currancyFormant = NumberFormat.getCurrencyInstance(locale);
        tcPrice.setCellFactory(tc -> new TableCell<PizzaModel, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(currancyFormant.format(price));
                }
            }
        });

        tblPizza.setItems(pizzas);


    }

    public void pizzaOfTheDayGenerator(ObservableList<PizzaModel> pizzas, Label randomPizza) {
        int randomIndex = new Random().nextInt(pizzas.size());
        PizzaModel pizzaOfTheDay = pizzas.get(randomIndex);
        randomPizza.setText(pizzaOfTheDay.getName());
        //obniżaie ceny pizzy dnia o 20%
        pizzas.get(randomIndex).setPrice(pizzas.get(randomIndex).getPrice() * 0.8);
        // wypisanie nazwy pizzy w Labelu
        randomPizza.setText(String.format("%s - %.2f zł", pizzaOfTheDay.getName(), pizzaOfTheDay.getPrice()));
    }


    private List<Integer> choices = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));


    //obiekt globalny który przehowuje kwotę do załaty
    private double amount;

    // metoda do wybierania i przenoszenia pizzy do koszyka
    public void addToBasket(TableView<PizzaModel> tblPizza, TextArea taBasket, Label lblSum) {
        // odczyt, który wiersz w tabelce został zaznaczony
        PizzaModel selectedPizza = tblPizza.getSelectionModel().getSelectedItem();
        // utworzenie okna kontekstowego do zamówienia wybranej ilości pizzy
        ChoiceDialog<Integer> addToBasketDialog = new ChoiceDialog<>(1, choices);
        addToBasketDialog.setTitle("Wybierz ilość");
        addToBasketDialog.setHeaderText("Wybrałeś pizze " + selectedPizza.getName());
        addToBasketDialog.setContentText("Wybierz ilość zamawianej pizzy: ");
        // okno zostaje wyświetlone i utrzymane na ekranie i zwróci wartość po wciśnięciu przycisku
        Optional<Integer> result = addToBasketDialog.showAndWait();
        // gdy wybrano OK
        result.ifPresent(quantity -> taBasket.appendText(
                String.format("%-15s %5d szt. %10.2f zł\n",
                        selectedPizza.getName(), quantity, selectedPizza.getPrice() * quantity)));
        //gdy wybrano ok
        result.ifPresent(quantity -> amount = amount + (selectedPizza.getPrice() * quantity));
        lblSum.setText(String.format("KWOTA DO ZAPŁATY: %.2f ZŁ", amount));


        //(^([0-9]{3}[-]{1}){2}[0-9]{3}$)|(^[0-9]{9}$)
        //^[au][l][\.]\s*[A-Za-złóżźńśćłę\.\d\s]+\d+[A-Za-z]*[\\\/]*\d*,\s*\d{2}-\d{3}\s[A-Za-zzłóżźńśćłę\s\-]*$


    }

    //-----------------------------------------------------------------------------------------------------------------
    public void clearOrder(TextArea taBasket, TextField tfPhone, TextField tfAdress, Label lblSum) {
        taBasket.clear();
        tfAdress.clear();
        tfPhone.clear();
        amount = 0;
        lblSum.setText("KWOTA DO ZAPŁATY: 0.00 ZŁ");
    }


    public boolean isPhoneValid(String phoneNo) {
        return Pattern.matches("(^([0-9]{3}[-]{1}){2}[0-9]{3}$)|(^[0-9]{9}$)", phoneNo);
    }

    public boolean isAddressValid(String address) {
        return Pattern.
                matches("^[au][l][\\.]\\s*[A-Za-złóżźńśćłęą\\.\\d\\s]+\\d+[A-Za-z]*[\\\\\\/]*\\d*,\\s*\\d{2}-\\d{3}\\s[A-Za-zzłóżźąńśćłę\\s\\-]*$", address);
    }

    public void getOrder(TextField tfPhone, TextField tfAddress, TextArea taBasket, Label lblSum) {
        if (isPhoneValid(tfPhone.getText()) && isAddressValid(tfAddress.getText()) && !taBasket.getText().equals("")) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Potwierdzenie Zamówienia");
            alert.setHeaderText("Dziękujemy !");
            alert.setContentText("Twoje zamówienie : \n" + taBasket.getText() + "\nŁączna kwota do zapłaty: \n" + amount + " zł");

            alert.showAndWait();
            clearOrder(taBasket, tfPhone, tfAddress, lblSum);
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Potwierdzenie");
            alert.setHeaderText("Błędne Zamówienie");
            String validationResult = "Wprowadziłeś niepoprawne dane w następujących polach : \n";
            if (!isPhoneValid(tfPhone.getText())) {
                validationResult += "nr telefonu ";
            }
            if (!isAddressValid(tfAddress.getText())) {
                validationResult += "adres dostawy ";
            }
            if (isAddressValid(tfAddress.getText())&&isPhoneValid(tfPhone.getText())){
                validationResult = "";
            }
            String emptyBasket = "";
            if (taBasket.getText().equals("")) {
                emptyBasket += "\n Koszyk nie może być pusty";
            }
            alert.setContentText(validationResult + emptyBasket);

            alert.showAndWait();

        }
    }

}
