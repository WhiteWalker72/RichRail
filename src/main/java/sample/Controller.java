package sample;

import com.sun.javafx.collections.ElementObservableListDecorator;
import domain.command.CommandManager;
import domain.train.ITrain;
import domain.train.TrainManager;
import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @FXML
    private ListView controlRemoveList;

    @FXML
    private ListView controlAddList;

    @FXML
    private TextField codeInput;

    @FXML
    private Button codeSubmit;

    public void initialize() {
        drawTrains();
        updateTrainNames();

        controlSelectBox.setOnAction((event -> {
            String trainName = (String) controlSelectBox.getValue();
            ITrain train = TrainManager.getInstance().getTrain(trainName);

            if (train != null) {
                List<String> lines = new ArrayList<>();
                for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
                    lines.add(iterator.getNext().getId());
                }
                controlRemoveList.setItems(FXCollections.observableList(lines));
            }
        }));

        controlTextfieldButton.setOnAction((event -> {
            String name = controlTextfieldAdd.getText();

            if (name.length() > 0) {
                String commandResult = CommandManager.getInstance().execute("new train " + name);
                writeToConsole(commandResult);

                ITrain train = TrainManager.getInstance().getTrain(name);
                if (train != null) {
                    ObservableList<String> items = controlSelectBox.getItems();
                    items.add(name);
                    controlSelectBox.setItems(items);
                    drawTrains();
                }
            }
        }));

        codeSubmit.setOnAction(event -> {
            String command = codeInput.getText();
            if (command.length() > 0) {
                writeToConsole(CommandManager.getInstance().execute(command));
                updateTrainNames();
                drawTrains();
            }
        });
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
                for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
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

    private void updateTrainNames() {
        List<String> trainNames = new ArrayList<>();
        TrainManager.getInstance().getTrains().stream().forEach(train -> trainNames.add(train.getName()));
        controlSelectBox.setItems(FXCollections.observableList(trainNames));
    }

}
