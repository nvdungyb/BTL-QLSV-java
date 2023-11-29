package manage.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import manage.database.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class NhanTinController implements Initializable {
    @FXML
    private Button button_send;

    @FXML
    private Button button_refresh;

    @FXML
    private ScrollPane sp_main;

    @FXML
    private TextField tf_message;

    @FXML
    private VBox ap_main;

    @FXML
    private AnchorPane ap_ATTT;

    @FXML
    private AnchorPane ap_CNTT;

    @FXML
    private AnchorPane ap_CNDPT;

    @FXML
    private AnchorPane ap_KTDTVT;

    @FXML
    private AnchorPane ap_TTDPT;

    @FXML
    private AnchorPane ap_CNKTD;

    @FXML
    private VBox vbox_messages;

    @FXML
    private HBox hbox_message;

    @FXML
    private AnchorPane ap_submain;

    private Server server;
    private Client client;

    private int check_print_CNTT_ATTT = 0;
    private int stt = 0;
    private static String goc_no_change = "";
    //    private static String
    private int check_S_C = 0;  // nếu = 0 thì là Server, còn không thì là Client

    public static boolean isPortAvailable(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            // Nếu không có lỗi, cổng đó chưa được sử dụng
            return true;
        } catch (Exception e) {
            // Có lỗi, cổng đó đã được sử dụng
            return false;
        }
    }

    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        new Thread(() -> {
            try {

                server = new Server(new ServerSocket(3379));
                check_S_C = 0;
                server.receiveMessageFromClient(vbox_messages);
                System.out.println("Created server" + check_S_C);

                vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        sp_main.setVvalue((Double) newValue);
                    }
                });

            } catch (IOException e) {
                check_S_C = 1;
                System.out.println("Error creating server or server already exists" + check_S_C);
                e.printStackTrace();
                try {

                    client = new Client(new Socket("localhost", 3379));
                    client.receiveMessageFromServer(vbox_messages);
                    System.out.println("CREAATED CLIENT" + check_S_C);

                } catch (IOException ex) {
                    check_S_C = 2;
                    System.out.println("Error creating client (after the server is error):))))))))))))))))" + check_S_C);
                    e.printStackTrace();

                }
            }
        }).start();

        ap_ATTT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                goc_no_change = "ATTT";

                gettin();

                sp_main.setVvalue(1.0);
            }
        });

        ap_CNDPT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                goc_no_change = "CNDPT";

                gettin();
            }
        });

        ap_KTDTVT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                goc_no_change = "KTDTVT";

                gettin();
            }
        });

        ap_CNKTD.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                goc_no_change = "CNKTD";

                gettin();
            }
        });

        ap_TTDPT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                goc_no_change = "TTDPT";

                gettin();
            }
        });
        ap_CNTT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                goc_no_change = "CNTT";
                gettin();
            }
        });

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                guitin(stt + 1);
                stt = 0;
            }

        });

        button_refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gettin();
            }
        });
    }

    public static void addLabel(String messageFromClient, VBox vbox) {   // Lấy thông tin từ bên gửi và hiện ra màn hình
        String[] tmp = messageFromClient.split("@@");
        messageFromClient = tmp[0];
//        String from = tmp[1];

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));
        hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));

        Text text = new Text(messageFromClient);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle(
                "-fx-background-color: #b9f0ec;" +
                        " -fx-font-size: 14;" +
                        " -fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
//        }

    }

    public void gettin() {
//        if(check_print_CNTT_ATTT == 0){
//            check_print_CNTT_ATTT++;
        vbox_messages.getChildren().clear();
        String sql = "select * from java_project.tinnhan1 where id_tn = '" + ("CNTT_" + goc_no_change) + "' order by stt asc";
        try {
            Connection conn = ConnectDatabase.connect();
            Statement state = conn.createStatement();
            ResultSet resultSet = state.executeQuery(sql);
            int i = 0;
            while (resultSet.next()) {
                String messages = resultSet.getString("message");
                String from = resultSet.getString("fromWho");
                i++;
                stt = i;
                if (check_S_C == 0) {  // server
                    if (!messages.isEmpty() && from.equals("CNTT")) {
                        HBox hBox = new HBox();
                        hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.setPadding(new Insets(5, 5, 5, 10));
                        Text text = new Text(messages);
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setMaxWidth(0.8 * 360);

                        textFlow.setStyle("-fx-color: #060e1c; " +
                                "-fx-background-color: #f26257;" +
                                " -fx-font-size: 14; " +
                                " -fx-background-radius: 20px;");
                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        text.setFill(Color.rgb(6, 14, 28));
                        hBox.getChildren().add(textFlow);
                        Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                        sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        tf_message.clear();
                    } else if (!messages.isEmpty() && from.equals(goc_no_change)) {
                        HBox hBox = new HBox();
                        hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setPadding(new Insets(5, 5, 5, 10));
                        Text text = new Text(messages);
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setMaxWidth(0.8 * 360);

                        textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                                " -fx-font-size: 14; " +
                                "-fx-background-color: #b9f0ec;" +
                                " -fx-background-radius: 20px;");
                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        text.setFill(Color.BLACK);
                        hBox.getChildren().add(textFlow);
                        //                        vbox_messages.getChildren().add(hBox);
                        Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                        sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        tf_message.clear();
                    }
                } else if (check_S_C == 1) {  // client
                    if (!messages.isEmpty() && from.equals(goc_no_change)) {
                        HBox hBox = new HBox();
                        hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
                        hBox.setAlignment(Pos.CENTER_RIGHT);
                        hBox.setPadding(new Insets(5, 5, 5, 10));
                        Text text = new Text(messages);
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setMaxWidth(0.8 * 360);

                        textFlow.setStyle("-fx-color: #060e1c; " +
                                "-fx-background-color: #f26257;" +
                                " -fx-font-size: 14; " +
                                " -fx-background-radius: 20px;");
                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        text.setFill(Color.rgb(6, 14, 28));
                        hBox.getChildren().add(textFlow);
                        Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                        sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        tf_message.clear();
                    } else if (!messages.isEmpty() && from.equals("CNTT")) {
                        HBox hBox = new HBox();
                        hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.setPadding(new Insets(5, 5, 5, 10));
                        Text text = new Text(messages);
                        TextFlow textFlow = new TextFlow(text);
                        textFlow.setMaxWidth(0.8 * 360);

                        textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                                " -fx-font-size: 14; " +
                                "-fx-background-color: #b9f0ec;" +
                                " -fx-background-radius: 20px;");
                        textFlow.setPadding(new Insets(5, 10, 5, 10));
                        text.setFill(Color.BLACK);
                        hBox.getChildren().add(textFlow);
                        //                        vbox_messages.getChildren().add(hBox);
                        Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                        sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                        tf_message.clear();
                    }
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void guitin(int num) {     // nút gửi
        String messageToSend = tf_message.getText();
        tf_message.clear();
        if (!messageToSend.isEmpty()) {
            try {
                if (check_S_C == 0) { // thêm trường hợp tương ứng với check_S_C = 1 (Client)
                    server.sendMessageToClient(messageToSend + "@@" + "CNTT");
                    HBox hBox = new HBox();
                    hBox.setStyle(String.valueOf(Color.color(1, 0, 0)));
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 8));
                    HBox.setHgrow(hBox, Priority.ALWAYS);
                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    double vboxWidth = vbox_messages.getWidth();
                    textFlow.setMaxWidth(0.8 * 360);
                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                            " -fx-font-size: 14; " +
                            "-fx-background-color: #f26257;" +
                            " -fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.BLACK);
                    hBox.getChildren().add(textFlow);
//            vbox_messages.getChildren().add(hBox);
                    Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                    sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    luu_DB(messageToSend, num + 1);
                } else if (check_S_C == 1) {  // client
                    client.sendMessageToServer(messageToSend + "@@" + goc_no_change);
                    HBox hBox = new HBox();
                    hBox.setStyle(String.valueOf(Color.color(1, 0, 0)));
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 8));
                    HBox.setHgrow(hBox, Priority.ALWAYS);
                    Text text = new Text(messageToSend);
                    TextFlow textFlow = new TextFlow(text);
                    double vboxWidth = vbox_messages.getWidth();
                    textFlow.setMaxWidth(0.8 * 360);
                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                            " -fx-font-size: 14; " +
                            "-fx-background-color: #f26257;" +
                            " -fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.BLACK);
                    hBox.getChildren().add(textFlow);
//            vbox_messages.getChildren().add(hBox);
                    Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                    sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    luu_DB(messageToSend, num + 1);
                }

            } catch (Exception e) {  // bên Client không tồn tại
//                e.printStackTrace();
                System.out.println("Can't send Message to Client or you don't set up Client, chỗ guitin()");

                HBox hBox = new HBox();
                hBox.setStyle(String.valueOf(Color.color(1, 0, 0)));
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 8));
                HBox.setHgrow(hBox, Priority.ALWAYS);
                Text text = new Text("ERROR: CAN'T SEND TO CLIENT (CLIENT NOT FOUND)");
                TextFlow textFlow = new TextFlow(text);
                double vboxWidth = vbox_messages.getWidth();
                textFlow.setMaxWidth(0.8 * 360);

                textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                        " -fx-font-size: 14; " +
                        "-fx-background-color: #000000;" +
                        " -fx-background-radius: 20px;");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.WHITE);
                hBox.getChildren().add(textFlow);
//            vbox_messages.getChildren().add(hBox);
                Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            } finally {
                tf_message.clear();

            }
        }
    }

    public void luu_DB(String messageToSend, int num) {
        System.out.println("Stt tin nhan can gui:::::::::::::::::::::::::::::::::::::: " + num);
//        String sql_check = "SELECT * from `demo`.`tinnhan1` where id_tn = `CNTT_"+goc_no_change+"`, ` and stt = "+num+";";
        String sql = "";
        if (check_S_C == 0) {
            sql = "INSERT INTO `java_project`.`tinnhan1` (`id_tn`, `stt`, `message`, `fromWho`) VALUES ('CNTT_" + goc_no_change + "', '" + (num) + "', '" + messageToSend + "', 'CNTT');";
        } else if (check_S_C == 1) {
            sql = "INSERT INTO `java_project`.`tinnhan1` (`id_tn`, `stt`, `message`, `fromWho`) VALUES ('CNTT_" + goc_no_change + "', '" + (num) + "', '" + messageToSend + "', '" + goc_no_change + "');";
        }

        try {
            Connection conn = ConnectDatabase.connect();
            Statement state = conn.createStatement();
            int resultSet = state.executeUpdate(sql);
            if (resultSet == 1) {
                System.out.println("Add message successfully in Database, not sending:   " + messageToSend);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}


