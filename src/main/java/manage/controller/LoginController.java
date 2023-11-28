package manage.controller;

import javafx.application.Platform;
import manage.database.ConnectDatabase;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class LoginController implements Initializable {
    @FXML
    private PasswordField Password;
    @FXML
    public TextField UserName;
    @FXML
    private Button loginButton;
    @FXML
    private Label status;

    public static String userNameLogin;
    public static String urlImage;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void statusPause() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(even -> {
            status.setText("");
        });
        delay.play();
    }

    public void checkInfo() {
        String name = UserName.getText().trim();
        String ma = Password.getText().trim();

        if (name.equals("") || ma.equals("")) {
            status.setText("Vui lòng nhập đầy đủ thông tin!");
            statusPause();
        } else {
            String sql = "SELECT * FROM java_project.users WHERE maSv = '" + ma + "' AND tenSv = '" + name + "'";
            connection = ConnectDatabase.connect();
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
                    status.setText("Đăng nhập thành công!");
                    statusPause();

                    userNameLogin = name;
                    urlImage = resultSet.getString(3);
                    loginButton.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/Gui/Main.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.setTitle("Phần mềm quản lý sinh viên");
                    stage.show();

                    stage.setOnCloseRequest(event -> {
                        Platform.exit();                            // Giải phóng tất cả tài nguyên của JavaFx và đóng tất cả các stage đang mở.
                        System.exit(0);                       // Đóng tất cả các thread đang chạy. (Bởi vì phần chat có thể threads vẫn đang chạy).
                    });

                } else {
                    status.setText("Tên đăng nhập hoặc mật khẩu sai!");
                    UserName.setStyle("-fx-border-color: red;");
                    UserName.setOnKeyReleased(
                            event -> UserName.setStyle("-fx-border-color: none;")
                    );
                    Password.setStyle("-fx-border-color: red;");
                    Password.setOnKeyReleased(
                            event -> Password.setStyle("-fx-border-color: none;")
                    );
                    statusPause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getUserName() {
        return userNameLogin;
    }

    public static String getUrlImage() {
        return urlImage;
    }

    public void handleLoginButton(ActionEvent event) {
        if (event.getSource() == loginButton) {
            System.out.println("Login button clicked!");
            checkInfo();

            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (loginButton != null)
            loginButton.setOnAction(this::handleLoginButton);
    }
}