package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class app extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        // Lấy kích thước màn hình chính
//        Screen screen = Screen.getPrimary();
//        Rectangle2D bounds = screen.getBounds();

        Scene scene = new Scene(root);
//        scene.getStylesheets().add(getClass().getResource("Main.css").toExternalForm());
        stage.setScene(scene);
//        stage.setX(50);
//        stage.setY(50);
//        stage.setMaximized(true);
        stage.show();

    }
}
//,bounds.getWidth(),bounds.getHeight()

// Tại sao project QLSV_java thì chỉ cần import mysql-connector vào libraries thôi còn project này thì phải import vào module thì mới chạy được?
// => Vì project QLSV_java là project maven còn project này là project java thông thường.
// => Nếu muốn chạy được project này thì phải import thư viện mysql-connector-java-8.0.25.jar vào module.
// => Nếu muốn chạy được project này thì phải import thư viện mysql-connector-java-8.0.25.jar vào module.

// Sự khác biệt giữa project maven và project java thông thường là gì?
// => Project maven là project sử dụng maven để quản lý thư viện.
// => Project java thông thường là project không sử dụng maven để quản lý thư viện.
// maven là gì?
// => Maven là một công cụ quản lý dự án phần mềm, nó được sử dụng để xây dựng và quản lý các dự án phần mềm Java.
// => Maven sử dụng một file XML để mô tả cấu trúc của dự án, các phụ thuộc vào các thư viện và các plugin được sử dụng trong dự án.