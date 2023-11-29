package manage.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Gui/Login.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(50);
        stage.setY(50);
//        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setTitle("Phần mềm quản lý sinh viên");
        stage.show();

    }
}
