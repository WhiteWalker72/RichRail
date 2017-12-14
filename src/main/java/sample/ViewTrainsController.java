package sample;

import domain.train.ITrain;
import domain.train.TrainManager;
import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.DrawUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class ViewTrainsController {

    @FXML
    private ScrollPane trainPane;

    @FXML
    private Button backButton;

    public void initialize() {
        drawTrains();

        backButton.setOnAction(event -> {
            try {
                ((Stage) backButton.getScene().getWindow()).close();
                URL url = new File("src/main/resources/train.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                Stage stage = new Stage();
                stage.setTitle("Trains");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void drawTrains() {
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        for (ITrain train : TrainManager.getInstance().getTrains()) {
            HBox hBox = new HBox();
            for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
                hBox.getChildren().add(DrawUtils.getComponentPane(iterator.getNext()));
            }
            vBox.getChildren().add(hBox);
        }
        trainPane.setContent(vBox);
    }

}
