package Main;

import Main.ConnectDatabase;
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
    private PasswordField password;
    @FXML
    public TextField userName;
    @FXML
    private Button loginButton;
    @FXML
    private Label status;

    public static String userNameLogin;

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
        String name = userName.getText().trim();
        String pass = password.getText().trim();

        if (name.equals("") || pass.equals("")) {
            status.setText("Vui lòng nhập đầy đủ thông tin!");
            statusPause();
        } else {
            String sql = "SELECT maSv, tenSv FROM java_project.users WHERE maSv = '" + name + "' AND tenSv = '" + pass + "'";
            connection = ConnectDatabase.connect();
            try {
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
                    status.setText("Đăng nhập thành công!");
                    statusPause();

                    userNameLogin = name;
                    loginButton.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                } else {
                    status.setText("Tên đăng nhập hoặc mật khẩu sai!");
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

    public void handleLoginButton(ActionEvent event) {
        if (event.getSource() == loginButton) {
            System.out.println("Login button clicked!");
            checkInfo();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (loginButton != null)
            loginButton.setOnAction(this::handleLoginButton);
    }
}