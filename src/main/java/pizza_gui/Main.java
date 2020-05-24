package pizza_gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //FXMLLoader ->> zaladowanie pliku .fxml i zwrocenie go jako obiekt Parent
        Parent root = FXMLLoader.load(getClass().getResource("/view/pizzaView.fxml"));

        //Utorzenie obiektu Scene wypełnionego
        Scene scene = new Scene(root);
        primaryStage.setTitle("PizzaTARR");
        //ustawienie sceny w okinie aplikacji (stage
        primaryStage.setScene(scene);
        //wyswietlenie i zatrzymanie okna aplikacji
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
