package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField controlTextfieldAdd;

    @FXML
    private Button controlTextfieldButton;

    @FXML
    private ComboBox controlSelectBox;

    @FXML
    private TextArea codeOutput;

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
    }

}
