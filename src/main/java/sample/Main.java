package sample;

import domain.command.CommandManager;
import domain.train.ITrain;
import domain.train.TrainManager;
import domain.train.component.IComponent;
import domain.train.component.sub.PassagerComponent;
import domain.train.iterator.Iterator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = new File("src/main/resources/train.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("RichRail");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
//        System.out.println(CommandManager.getInstance().execute("new train tr1"));
        ITrain train = TrainManager.getInstance().getTrain("tr1");

        for (Iterator<IComponent> iterator = train.getIterator(); iterator.hasNext();) {
            IComponent component = iterator.getNext();
            if (component instanceof PassagerComponent) {
                System.out.println(((PassagerComponent) component).getSeats());
            }
        }

/*        IComponent component = TrainManager.getInstance()
                .getComponentManager().getComponentPair("comp2").getRightValue();
        System.out.println(component.getId());
        System.out.println(component instanceof PassagerComponent);
        System.out.println(((PassagerComponent) component).getSeats());

        System.out.println(CommandManager.getInstance().execute("getnumseats train tr2"));*/
/*        train.addItem(new ComponentBuilder("comp2").withPassengers(8).build());
        train.addItem(new ComponentBuilder("comp3").withPassengers(18).build());
        TrainManager.getInstance().updateTrain(train);*/
//        launch(args);
    }

}
