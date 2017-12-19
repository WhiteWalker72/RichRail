package controller;

import domain.command.CommandManager;
import domain.train.ITrain;
import domain.train.TrainFacade;
import domain.train.component.IComponent;
import domain.train.iterator.Iterator;
import domain.train.observer.IObserver;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import utils.DrawUtils;
import utils.Pair;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainController implements IObserver {

    private static Stage stage;

    @FXML
    private Button controlAddButton;

    @FXML
    private ComboBox controlAddSelectBox;

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

    @FXML
    private TextField controlAddNaam;

    @FXML
    private TextField controlAddAmount;

    @FXML
    private Button deleteTrainButton;

    public MainController() {
        TrainFacade.getInstance().registerTrainObserver(this);
        TrainFacade.getInstance().registerComponentObserver(this);
    }

    public void initialize() {
        updateScreen();
        controlAddNaam.setPromptText("Name");
        controlAddAmount.setPromptText("Amount");
        controlAddSelectBox.setItems(FXCollections.observableList(TrainFacade.getInstance().getComponentTypes()));

        // Draw the first train
        if (!TrainFacade.getInstance().getTrains().isEmpty()) {
            ITrain firstTrain = TrainFacade.getInstance().getTrains().get(0);
            drawTrain(firstTrain.getName());
            controlSelectBox.setValue(firstTrain.getName());
        }

        controlRemoveButton.setOnAction(event -> {
            String commandResult = CommandManager.getInstance().execute("delete wagon " + controlRemoveList.getSelectionModel().getSelectedItem());
            writeToConsole(commandResult);
        });

        controlSelectBox.setOnAction((event -> {
            String trainName = (String) controlSelectBox.getValue();
            drawTrain(trainName);
            updateControlRemoveList();
        }));

        controlTextfieldButton.setOnAction((event -> {
            String name = controlTextfieldAdd.getText();

            if (name.length() > 0) {
                String commandResult = CommandManager.getInstance().execute("new train " + name);
                writeToConsole(commandResult);
            }
        }));

        codeInput.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                codeSubmit.fire();
            }
        });

        deleteTrainButton.setOnAction(event -> {
            String trainName = (String) controlAddList.getSelectionModel().getSelectedItem();
            if (trainName != null) {
                ITrain train = TrainFacade.getInstance().getTrain(trainName);
                if (train != null) {
                    for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); ) {
                        TrainFacade.getInstance().removeComponent(iterator.getNext());
                    }
                }
                writeToConsole("train " + train.getName() + " deleted.");
                TrainFacade.getInstance().deleteTrain(train);
            }
        });

        // Command execute button
        codeSubmit.setOnAction(event -> {
            String command = codeInput.getText();
            if (command.length() > 0) {
                writeToConsole(CommandManager.getInstance().execute(command));
            }
        });

        // Open the view trains screen
        viewTrainsButton.setOnAction(event -> {
            try {
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

        controlAddButton.setOnAction(event -> {
            String name = controlAddNaam.getText();
            if (name.length() > 0) {
                if (!TrainFacade.getInstance().validTrainName(name.toLowerCase())) {
                    writeToConsole("Invalid train name.");
                } else {
                    String newWagonResult = CommandManager.getInstance().execute("new wagon " + name + " " + controlAddSelectBox.getValue() + " " + controlAddAmount.getText());

                    if (newWagonResult.contains("created")) {
                        String addToResult = CommandManager.getInstance().execute("add " + name + " to " + controlAddList.getSelectionModel().getSelectedItem());
                        if (!addToResult.contains("max pulling power")) {
                            writeToConsole(newWagonResult);
                        }
                        else {
                            Pair<String, IComponent> componentPair = TrainFacade.getInstance().getComponentPair(name);
                            if (componentPair.getRightValue() != null) {
                                TrainFacade.getInstance().removeComponent(componentPair.getRightValue());
                                writeToConsole(addToResult);
                            }
                        }
                    } else {
                        writeToConsole(newWagonResult);
                    }
                }
                drawTrain((String) controlAddList.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void writeToConsole(String text) {
        String consoleText = codeOutput.getText() + text + "\n";
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

    private void updateControlRemoveList() {
        String selectedTrain = (String) controlSelectBox.getSelectionModel().getSelectedItem();
        List<String> lines = new ArrayList<>();
        if (selectedTrain != null) {
            ITrain train = TrainFacade.getInstance().getTrain(selectedTrain);
            if (train != null) {
                for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext(); )
                    lines.add(iterator.getNext().getId());
            }
        }
        controlRemoveList.setItems(FXCollections.observableList(lines));
    }

    private void updateScreen() {
        List<String> trainNames = TrainFacade.getInstance().getTrains().stream().map(train -> train.getName()).collect(Collectors.toList());
        controlAddList.setItems(FXCollections.observableList(trainNames));
        controlSelectBox.setItems(FXCollections.observableList(trainNames));

        updateControlRemoveList();
        if (trainNames.size() == 1) {
            String trainName = trainNames.get(0);
            drawTrain(trainName);
            controlSelectBox.setValue(trainName);
        } else {
            drawSelectedTrain();
        }
    }

    @Override
    public void update() {
        updateScreen();
    }

    static Stage getStage() {
        if (stage == null) {
            stage = new Stage();
        }
        return stage;
    }

}
