package Ozlympic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;


public class Ozlympic extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load(location.openStream());
        primaryStage.setTitle("Ozlympic");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        Controller controller = fxmlLoader.getController();
        controller.init();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
