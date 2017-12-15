package utils;

import domain.train.component.IComponent;
import domain.train.component.sub.CargoComponent;
import domain.train.component.sub.LocomotiveComponent;
import domain.train.component.sub.PassagerComponent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.File;
import java.net.MalformedURLException;

public class DrawUtils {

    public static VBox getComponentBox(IComponent component) {
        try {
            VBox vBox = new VBox();
            StackPane pane = new StackPane();

            ImageView view = new ImageView();
            String url = new File("src/main/resources/" + component.getImage()).toURI().toURL().toExternalForm();
            view.setImage(new Image(url));
            pane.getChildren().add(view);
            vBox.getChildren().add(pane);

            addToVBox(vBox, component.getId(), Font.font("Verdana", FontWeight.BOLD, 20));

            Font attributeFont = Font.font("Verdana", FontWeight.BOLD, 10);
            if (component instanceof CargoComponent) {
                addToVBox(vBox, "cargo :" + ((CargoComponent) component).getMaxCargo(), attributeFont);
            }
            if (component instanceof PassagerComponent) {
                addToVBox(vBox, "seats :" + ((PassagerComponent) component).getSeats(), attributeFont);
            }
            if (component instanceof LocomotiveComponent) {
                addToVBox(vBox, "power :" + ((LocomotiveComponent) component).getPullingPower(), attributeFont);
            }

            return vBox;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void addToVBox(VBox vBox, String text, Font font) {
        StackPane pane = new StackPane();
        Text paneText = new Text(text);
        paneText.setFont(font);
        pane.getChildren().add(paneText);
        vBox.getChildren().add(pane);
    }

}
