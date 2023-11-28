package manage.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LichHocMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Gui/LichHoc1.fxml"));
        Parent root = loader.load();

        // Set the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Thời khoá biểu");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
