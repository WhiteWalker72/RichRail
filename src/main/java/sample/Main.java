package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/resources/sample.fxml").toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Hello World test");
        primaryStage.setScene(new Scene(root, 886, 671));
        primaryStage.show();
    }	

    public static void main(String[] args) {
        launch(args);
    }

}
