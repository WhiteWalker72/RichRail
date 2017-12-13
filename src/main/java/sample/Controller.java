package sample;

import domain.command.CommandManager;
import domain.train.ITrain;
import domain.train.TrainManager;
import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;

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
        drawTrains();

        controlTextfieldButton.setOnAction((event -> {
            String name = controlTextfieldAdd.getText();


            String commandResult = CommandManager.getInstance().execute("new train " + name);
            writeToConsole(commandResult);

            ITrain train = TrainManager.getInstance().getTrain(name);
            if (train != null) {
                ObservableList<String> items = controlSelectBox.getItems();
                items.add(name);
                controlSelectBox.setItems(items);
                drawTrains();
            }
        }));
    }

    private void writeToConsole(String text) {
        String consoleText = codeOutput.getText();
        consoleText += text + "\n";
        codeOutput.setText(consoleText);
    }

    private void drawTrains() {
        try {
            VBox vBox = new VBox();
            vBox.setSpacing(20);
            for (ITrain train : TrainManager.getInstance().getTrains()) {
                HBox hBox = new HBox();
                for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext();) {
                    ImageView view = new ImageView();
                    String url = new File("src/main/resources/" + iterator.getNext().getImage()).toURI().toURL().toExternalForm();
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
