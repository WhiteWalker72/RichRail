package sample;

import domain.command.CommandManager;
import domain.train.ITrain;
import domain.train.TrainManager;
import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.DrawUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    @FXML
    private Button viewTrainsButton;

    public void initialize() {
        updateTrainNames();

        if (!TrainManager.getInstance().getTrains().isEmpty()) {
            ITrain firstTrain = TrainManager.getInstance().getTrains().get(0);
            drawTrain(firstTrain.getName());
            controlSelectBox.setValue(firstTrain.getName());
        }

        controlSelectBox.setOnAction((event -> {
            String trainName = (String) controlSelectBox.getValue();
            drawTrain(trainName);
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
                }
            }
        }));

        codeSubmit.setOnAction(event -> {
            String command = codeInput.getText();
            if (command.length() > 0) {
                writeToConsole(CommandManager.getInstance().execute(command));
                updateTrainNames();
            }
        });

        // Open the view trains screen
        viewTrainsButton.setOnAction(event -> {
            try {
                ((Stage) viewTrainsButton.getScene().getWindow()).close();
                URL url = new File("src/main/resources/viewtrains.fxml").toURI().toURL();
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

    private void writeToConsole(String text) {
        String consoleText = codeOutput.getText();
        consoleText += text + "\n";
        codeOutput.setText(consoleText);
    }

    private void drawTrain(String trainName) {
        ITrain train = TrainManager.getInstance().getTrain(trainName);
        if (train != null) {
            HBox hBox = new HBox();
            for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
                hBox.getChildren().add(DrawUtils.getComponentPane(iterator.getNext()));
            }
            imagePane.setContent(hBox);
        }
    }

    private void updateTrainNames() {
        List<String> trainNames = new ArrayList<>();
        TrainManager.getInstance().getTrains().stream().forEach(train -> trainNames.add(train.getName()));
        controlSelectBox.setItems(FXCollections.observableList(trainNames));
    }

}
