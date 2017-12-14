package utils;

import domain.train.component.IComponent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;

public class DrawUtils {

    public static StackPane getComponentPane(IComponent component) {
        try {
            ImageView view = new ImageView();
            String url = new File("src/main/resources/" + component.getImage()).toURI().toURL().toExternalForm();
            view.setImage(new Image(url));

            Text trainText = new Text(component.getId());
            trainText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

            StackPane pane = new StackPane();
            pane.getChildren().add(view);
            pane.getChildren().add(trainText);
            pane.setAlignment(Pos.CENTER);
            return pane;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
