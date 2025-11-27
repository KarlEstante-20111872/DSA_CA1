package dsa_ca1.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Supermarket extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dsa_ca1/views/supermarket.fxml"));
        Scene scene = new Scene(loader.load(), 1200, 750); // adjust size
        scene.getStylesheets().add(getClass().getResource("/dsa_ca1/views/Supermarket.css").toExternalForm());

        primaryStage.setTitle("Supermarket Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
