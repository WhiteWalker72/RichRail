package sample;

import domain.command.CommandManager;
import domain.train.ITrain;
import domain.train.TrainFacade;
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
import java.util.Observer;

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
    private Button controlRemoveButton;

    @FXML
    private TextField codeInput;

    @FXML
    private Button codeSubmit;

    @FXML
    private Button viewTrainsButton;

    public void initialize() {
        updateTrainNames();

        if (!TrainFacade.getInstance().getTrains().isEmpty()) {
            ITrain firstTrain = TrainFacade.getInstance().getTrains().get(0);
            drawTrain(firstTrain.getName());
            controlSelectBox.setValue(firstTrain.getName());
            updateComponentRemoveList(firstTrain);
        }
        controlRemoveButton.setOnAction(event -> {
            String commandResult = CommandManager.getInstance().execute("delete wagon " + controlRemoveList.getSelectionModel().getSelectedItem());
            writeToConsole(commandResult);
            updateTrainNames();
            drawTrain(controlRemoveList.getSelectionModel().getSelectedItems().toString());
        });

        controlSelectBox.setOnAction((event -> {
            String trainName = (String) controlSelectBox.getValue();
            drawTrain(trainName);
            ITrain train = TrainFacade.getInstance().getTrain(trainName);

            updateComponentRemoveList(train);
        }));

        controlTextfieldButton.setOnAction((event -> {
            String name = controlTextfieldAdd.getText();

            if (name.length() > 0) {
                String commandResult = CommandManager.getInstance().execute("new train " + name);
                writeToConsole(commandResult);

                ITrain train = TrainFacade.getInstance().getTrain(name);
                if (train != null) {
                    ObservableList<String> items = controlSelectBox.getItems();
                    controlSelectBox.setValue(name);
                    if (!items.contains(train.getName()))
                        items.add(name);
                    controlSelectBox.setItems(items);
                }
            }

        }));
        //Button voor commands doorvoeren
        codeSubmit.setOnAction(event -> {
            String command = codeInput.getText();
            if (command.length() > 0) {
                writeToConsole(CommandManager.getInstance().execute(command));
                updateTrainNames();
                drawSelectedTrain();
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

    private void drawSelectedTrain() {
        String selected = (String) controlSelectBox.getSelectionModel().getSelectedItem();
        if (selected != null) {
            drawTrain(selected);
        } else {
            imagePane.setContent(null);
            controlRemoveList.setItems(FXCollections.observableList(new ArrayList<>()));
        }
    }

    private void drawTrain(String trainName) {
        ITrain train = TrainFacade.getInstance().getTrain(trainName);
        if (train != null) {
            HBox hBox = new HBox();
            for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
                hBox.getChildren().add(DrawUtils.getComponentBox(iterator.getNext()));
            }
            imagePane.setContent(hBox);
        }
    }

    private void updateTrainNames() {
        List<String> trainNames = new ArrayList<>();
        TrainFacade.getInstance().getTrains().stream().forEach(train -> trainNames.add(train.getName()));
        controlSelectBox.setItems(FXCollections.observableList(trainNames));
    }

    private void updateComponentRemoveList(ITrain train) {
        if (train == null) {
            controlRemoveList.setItems(FXCollections.observableList(new ArrayList<>()));
            return;
        }
        List<String> lines = new ArrayList<>();
        for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
            lines.add(iterator.getNext().getId());
        }
        controlRemoveList.setItems(FXCollections.observableList(lines));
    }

    private void populateComponentList () {
        controlAddList.setItems(FXCollections.observableList(TrainFacade.getInstance().getComponentTypes()));
    }
    
}
