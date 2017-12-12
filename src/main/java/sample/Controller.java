package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;

public class Controller {

    @FXML
    private TextField controlTextfieldAdd;

    @FXML
    private Button controlTextfieldButton;

    @FXML
    private ComboBox controlSelectBox;

    @FXML
    private TextArea codeOutput;

    @FXML
    private ScrollPane imagePane;

    public void initialize() {
        controlTextfieldButton.setOnAction((event -> {
            String name = controlTextfieldAdd.getText();
            ObservableList<String> items = controlSelectBox.getItems();
            items.add(name);
            controlSelectBox.setItems(items);

            String text = codeOutput.getText();
            text += name + " train created.\n";
            codeOutput.setText(text);

            System.out.println(name);
        }));

        try {
            VBox vBox = new VBox();
            vBox.setSpacing(20);
            for (int i = 0; i < 2; i++) {
                HBox hBox = new HBox();
                for (String imageStr : Arrays.asList("locomotive.png", "basicwagon.png", "cargowagon.png", "passengerwagon.png")) {
                    ImageView view = new ImageView();
                    String url = new File("src/main/resources/" + imageStr).toURI().toURL().toExternalForm();
                    view.setImage(new Image(url));
                    hBox.getChildren().add(view);
                }
                vBox.getChildren().add(hBox);
            }
            imagePane.setContent(vBox);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

}
