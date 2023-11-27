package manage.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import java.awt.Desktop;
import javafx.geometry.HPos;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manage.data.NewsData;
import manage.database.ConnectDatabase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Comparator;
import java.util.ResourceBundle;

public class TaiLieuController implements Initializable {

    @FXML
    public AnchorPane ap_CNTT;
    @FXML
    public AnchorPane ap_CNDPT;
    @FXML
    public AnchorPane ap_ATTT;
    @FXML
    public AnchorPane ap_KTDTVT;
    @FXML
    public AnchorPane ap_TTDPT;
    @FXML
    public AnchorPane ap_CNKTD;
    @FXML
    public AnchorPane ap_submain;
    @FXML
    public Button themTL;
    @FXML
    public Button showTL;
    @FXML
    public ComboBox<String> chonKhoa;
    @FXML
    public VBox vbox_tl;
    private String khoa;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = FXCollections.observableArrayList("CNTT", "ATTT", "CNDPT", "TTDPT", "KTDTVT", "CNKTD");
        chonKhoa.setItems(items);
        setAction_chonkhoa();
        vbox_tl.getChildren().clear();

        if(themTL != null){
            themTL.setOnAction(this::themTLvaoDB);
        }
    }

    private void themTLvaoDB(ActionEvent event) {
        if (event.getSource() == themTL) {
            Stage stage = new Stage();
            Scene scene = new Scene(new Pane());

            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("/Gui/getTailieu.fxml")));
                stage.setScene(scene);
                stage.showAndWait();

//                NewsData data = new NewsData(NewsController.getData().getTitle(), NewsController.getData().getLink());
//                // ĐK để lần sau khi thêm tin mà bị gián đoạn thì sẽ không hiển thị tin trước đó lên nữa.
//                NewsController.setData(null, null);
//
//                if (data.getLink() != null && data.getTitle() != null) {
//
//                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setAction_chonkhoa() {
        chonKhoa.setOnAction(event -> {
            String selectedOption = chonKhoa.getSelectionModel().getSelectedItem();
            khoa = selectedOption;
            vbox_tl.getChildren().clear();
            if (selectedOption != null) {
                switch (selectedOption) {
                    case "CNTT":
                        System.out.println("CNTT");
                        getTLfromDB("CNTT");
                        break;
                    case "ATTT":
                        System.out.println("ATTT");
                        getTLfromDB("ATTT");
                        break;
                    case "CNDPT":
                        System.out.println("CNDPT");
                        getTLfromDB("CNDPT");
                        break;
                    case "KTDTVT":
                        System.out.println("KTDTVT");
                        getTLfromDB("KTDTVT");
                        break;
                    case "TTDPT":
                        System.out.println("TTDPT");
                        getTLfromDB("TTDPT");
                        break;
                    case "CNKTD":
                        System.out.println("CNKTD");
                        getTLfromDB("CNKTD");
                        break;
                }
            }
        });
    }

    public String nguon(){
        return khoa;
    }

    public void getTLfromDB(String from){
        String sql = "Select * from demo.tailieu where khoa = '"+from+"';";
        try{
            Connection conn = ConnectDatabase.connect();
//            assert conn != null;
            Statement state = conn.createStatement();
            ResultSet resultSet = state.executeQuery(sql);
            while(resultSet.next()){
                String url = resultSet.getString("url");
                System.out.println(url);
                String[] tmp =url.split("\\\\+");
                url = "";
                for(int i = 0; i < tmp.length-1; i++){
                    url += tmp[i].trim()+"\\";
                }
                url += tmp[tmp.length-1];
                System.out.println(tmp[tmp.length-1]);
                HBox hBox = new HBox();
                hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
                Text text = new Text(tmp[tmp.length-1]);
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: #f26257;"+
                        " -fx-font-size: 14;"+
                        " -fx-background-radius: 20px;");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                textFlow.setPrefWidth(0.8*450);
                textFlow.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));

                hBox.setUserData(url.trim());

                String finalUrl = url;
                String cleanUrl = finalUrl.replace("\u202A", "");
                hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        downloadFile(cleanUrl);
                    }
                });

                hBox.getChildren().add(textFlow);
                vbox_tl.getChildren().add(hBox);

            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("Can't get Documentary from DataBase");
        }

    }

    public void downloadFile(String filePath) {
        try {
            File file = new File(filePath);

            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                } else {
                    System.out.println("Desktop not supported. Cannot open the file.");
                }
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error opening the file");
        }
    }
}
