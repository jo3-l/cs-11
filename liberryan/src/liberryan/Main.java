package liberryan;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("liberryan.fxml"));
        primaryStage.setTitle("Liberryan");
        primaryStage.setScene(new Scene(root, 806, 545));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
