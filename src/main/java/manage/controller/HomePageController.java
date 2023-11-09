package manage.controller;

import manage.data.NewsData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomePageController implements Initializable {
    @FXML
    private Button AddNews;
    @FXML
    private VBox Notification;
    @FXML
    private ScrollPane NotificationPane;
    @FXML
    private AnchorPane imagePane;
    @FXML
    private Label textNotification;
    @FXML
    private ImageView imageView;

    public void hanlderShowNews(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();
            String link = (String) btn.getUserData();
            System.out.println(link);

            imagePane.setVisible(false);
            NotificationPane.setVisible(true);
            textNotification.setText("");

            try {
                Scanner sc = new Scanner(new File(link));
                while (sc.hasNextLine()) {
                    Text text = new Text(sc.nextLine() + "\n");
                    textNotification.setText(textNotification.getText() + text.getText());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void hanlderAddNews(ActionEvent event) {
        if (event.getSource() == AddNews) {
            Stage stage = new Stage();
            Scene scene = new Scene(new Pane());

            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("/Gui/News.fxml")));
                stage.setScene(scene);
                stage.showAndWait();

                NewsData data = new NewsData(NewsController.getData().getTitle(), NewsController.getData().getLink());
                // ĐK để lần sau khi thêm tin mà bị gián đoạn thì sẽ không hiển thị tin trước đó lên nữa.
                NewsController.setData(null, null);

                if (data.getLink() != null && data.getTitle() != null) {
                    Button btn = new Button();
                    btn.setWrapText(true);
                    btn.setFont(javafx.scene.text.Font.font("Times New Roman", 20));
                    btn.setMinSize(50, 50);
                    btn.setMaxSize(430, 70);

                    btn.setText(data.getTitle());
                    btn.setUserData(data.getLink());

                    btn.setOnAction(this::hanlderShowNews);
                    btn.setBorder(new Border(new BorderStroke(Color.rgb(215, 211, 212), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(10, 10, 10, 10))));

                    if (Notification != null) {
                        Notification.getChildren().add(btn);
                    } else {
                        System.out.println("Notification is null!");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (AddNews != null) {
            AddNews.setOnAction(this::hanlderAddNews);
        }
    }
}
