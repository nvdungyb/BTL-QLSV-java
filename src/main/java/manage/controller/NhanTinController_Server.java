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

public class NhanTinController_Server implements Initializable {
    @FXML
    private Button button_send;

    @FXML
    private Button button_refresh;

    @FXML
    private ScrollPane sp_main;

    @FXML
    private TextField tf_message;

    @FXML
    private AnchorPane ap_main;

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
    private static String nguon = "";


    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        new Thread(() -> {
            try {

                server = new Server(new ServerSocket(3379));
                server.receiveMessageFromClient(vbox_messages);
//
//                // sửa lúc 22:20
//                client = new Client(new Socket("localhost", 3378));
//                client.receiveMessageFromServer(vbox_messages);


                System.out.println("Connected to server");

                vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        sp_main.setVvalue((Double) newValue);
                    }
                });

            } catch (IOException e) {
                System.out.println("Error creating server.");
                e.printStackTrace();

            }
        }).start();

//        new Thread(()->{
//            try{
//                client = new Client(new Socket("localhost", 3378));
//                System.out.println("Connected to client");
//                client.receiveMessageFromServer(vbox_messages);
//
        //                vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                        sp_main.setVvalue((Double) newValue);
//                    }
//                });
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("Error creating client.");
//            }
//        }).start();

        ap_ATTT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                nguon = "ATTT";
                gettin(nguon);
                sp_main.setVvalue(1.0);
//                stt = 0;
            }
        });

        ap_CNDPT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                nguon = "CNDPT";
                gettin(nguon);
                sp_main.setVvalue(1.0);
            }
        });

        ap_KTDTVT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                nguon = "KTDTVT";
                gettin(nguon);
            }
        });

        ap_CNKTD.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                nguon = "CNKTD";
                gettin(nguon);
            }
        });

        ap_TTDPT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                nguon = "TTDPT";
                gettin(nguon);
            }
        });
        ap_CNTT.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                check_print_CNTT_ATTT = 0;
                nguon = "CNTT";
                gettin(nguon);
            }
        });

        button_send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                guitin(stt+1);
                stt = 0;
            }

        });

        button_refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gettin(nguon);
            }
        });
    }
    public static void addLabel(String messageFromClient, VBox vbox) {   // Lấy thông tin từ bên gửi và hiện ra màn hình
        String[] tmp = messageFromClient.split("@@");
        messageFromClient = tmp[0];
        String from = tmp[1];
        if(!from.equals(nguon)){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));

            Text text = new Text(messageFromClient);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: #f26257;"+
                    " -fx-font-size: 14;"+
                    " -fx-background-radius: 20px;");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            textFlow.setMaxWidth(0.8*360);
            hBox.getChildren().add(textFlow);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vbox.getChildren().add(hBox);
                }
            });
        }
        else{
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));

            Text text = new Text(messageFromClient);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle(
                    "-fx-background-color: #b9f0ec;" +
                            " -fx-font-size: 14;"+
                            " -fx-background-radius: 20px;");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
            hBox.getChildren().add(textFlow);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    vbox.getChildren().add(hBox);
                }
            });
        }

    }

    public void gettin(String nguon){
//        if(check_print_CNTT_ATTT == 0){
//            check_print_CNTT_ATTT++;
        vbox_messages.getChildren().clear();
        String sql = "select * from demo.tinnhan1 where id_tn = '"+("CNTT_"+nguon)+"' order by stt asc";
        try {
            Connection conn = ConnectDatabase.connect();
            Statement state = conn.createStatement();
            ResultSet resultSet = state.executeQuery(sql);
            int i = 0;
            while (resultSet.next()) {
                String messages = resultSet.getString("message");
                String from = resultSet.getString("from");
                i++;
                stt = i;

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
                            " -fx-font-size: 14; "+
                            " -fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.rgb(6, 14, 28));
                    hBox.getChildren().add(textFlow);
                    Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                    sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    tf_message.clear();
                }
                else if (!messages.isEmpty() && from.equals(nguon)) {
                    HBox hBox = new HBox();
                    hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));
                    Text text = new Text(messages);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setMaxWidth(0.8 * 360);

                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                            " -fx-font-size: 14; "+
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void guitin(int num){     // nút gửi
        String messageToSend = tf_message.getText();
        tf_message.clear();
        if(!messageToSend.isEmpty()) {


            try {
                server.sendMessageToClient(messageToSend+"@@"+"CNTT");
                HBox hBox = new HBox();
                hBox.setStyle(String.valueOf(Color.color(1,0,0)));
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 8));
                HBox.setHgrow(hBox, Priority.ALWAYS);
                Text text = new Text(messageToSend);
                TextFlow textFlow = new TextFlow(text);
                double vboxWidth = vbox_messages.getWidth();
                textFlow.setMaxWidth(0.8*360);
                textFlow.setStyle("-fx-color: rgb(239,242,255); "+
                        " -fx-font-size: 14; "+
                        "-fx-background-color: #f26257;"+
                        " -fx-background-radius: 20px;");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.BLACK);
                hBox.getChildren().add(textFlow);
//            vbox_messages.getChildren().add(hBox);
                Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                luu_DB(messageToSend, num+1);
            } catch (Exception e) {
//                e.printStackTrace();
                System.out.println("Can't send Message to Client or you don't set up Client");

                HBox hBox = new HBox();
                hBox.setStyle(String.valueOf(Color.color(1,0,0)));
                hBox.setAlignment(Pos.CENTER_RIGHT);
                hBox.setPadding(new Insets(5, 5, 5, 8));
                HBox.setHgrow(hBox, Priority.ALWAYS);
                Text text = new Text("ERROR: CAN'T SEND TO CLIENT (CLIENT NOT FOUND)");
                TextFlow textFlow = new TextFlow(text);
                double vboxWidth = vbox_messages.getWidth();
                textFlow.setMaxWidth(0.8*360);

                textFlow.setStyle("-fx-color: rgb(239,242,255); "+
                        " -fx-font-size: 14; "+
                        "-fx-background-color: #000000;"+
                        " -fx-background-radius: 20px;");
                textFlow.setPadding(new Insets(5, 10, 5, 10));
                text.setFill(Color.WHITE);
                hBox.getChildren().add(textFlow);
//            vbox_messages.getChildren().add(hBox);
                Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
                sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
            finally {
                tf_message.clear();

            }
        }
    }

    public void luu_DB(String messageToSend, int num){
        System.out.println("Stt tin nhan can gui:::::::::::::::::::::::::::::::::::::: "+num);
//        String sql_check = "SELECT * from `demo`.`tinnhan1` where id_tn = `CNTT_"+nguon+"`, ` and stt = "+num+";";
        String sql = "INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_"+nguon+"', '"+(num)+"', '"+messageToSend+"', 'CNTT');";
        try {
            Connection conn = ConnectDatabase.connect();
            Statement state = conn.createStatement();
            int resultSet = state.executeUpdate(sql);
            if(resultSet == 1){
                System.out.println("Add message successfully");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}





//package manage.controller;
//
//
//import javafx.application.Platform;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Priority;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.scene.text.TextFlow;
//import manage.database.*;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ResourceBundle;
//
//public class NhanTinController implements Initializable {
//    @FXML
//    private Button button_send;
//
//    @FXML
//    private Button button_refresh;
//
//    @FXML
//    private static ScrollPane sp_main;
//
//    @FXML
//    private TextField tf_message;
//
//    @FXML
//    private AnchorPane ap_main;
//
//    @FXML
//    private AnchorPane ap_ATTT;
//
//    @FXML
//    private AnchorPane ap_CNTT;
//
//    @FXML
//    private AnchorPane ap_CNDPT;
//
//    @FXML
//    private AnchorPane ap_KTDTVT;
//
//    @FXML
//    private AnchorPane ap_TTDPT;
//
//    @FXML
//    private AnchorPane ap_CNKTD;
//
//    @FXML
//    private VBox vbox_messages;
//
//    @FXML
//    private HBox hbox_message;
//
//    @FXML
//    private AnchorPane ap_submain;
//
//    private Server server;
//    private Client client;
//
//    private int check_print_CNTT_ATTT = 0;
//    private int stt = 0;
//    private static String nguon = "";
//
//
//    public void initialize(URL arg0, ResourceBundle arg1) {
//        // TODO Auto-generated method stub
//        new Thread(() -> {
//            try {
//                set_phuongthuc();
//                gettin(nguon);
//                server = new Server(new ServerSocket(3379));
//                server.receiveMessageFromClient(vbox_messages);
//                System.out.println("Connected to server");
//
//
//                vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
//                    @Override
//                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                        sp_main.setVvalue((Double) newValue);
//                    }
//                });
//
//            } catch (IOException e) {
//                System.out.println("Error creating server.");
//                e.printStackTrace();
//
//            }
//        }).start();
//
////        new Thread(()->{
////            try{
////                set_phuongthuc();
////                client = new Client(new Socket("localhost", 3378));
////                System.out.println("Created to client");
////                client.receiveMessageFromServer(vbox_messages);
////
////
//////                vbox_messages.heightProperty().addListener(new ChangeListener<Number>() {
//////                    @Override
//////                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//////                        sp_main.setVvalue((Double) newValue);
//////                    }
//////                });
////
////            } catch (IOException e) {
////                e.printStackTrace();
////                System.out.println("Error creating client.");
////            }
////        }).start();
//    }
//    public static void addLabel(String messageFromClient, VBox vbox) {
//        String[] tmp = messageFromClient.split("@@");
//        messageFromClient = tmp[0];
//        String from = tmp[1];
//        if(!from.equals(nguon)){
//            HBox hBox = new HBox();
//            hBox.setAlignment(Pos.CENTER_RIGHT);
//            hBox.setPadding(new Insets(5, 5, 5, 10));
//            hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
//
//            Text text = new Text(messageFromClient);
//            TextFlow textFlow = new TextFlow(text);
//            textFlow.setStyle("-fx-background-color: #f26257;"+
//                    " -fx-font-size: 14;"+
//                    " -fx-background-radius: 20px;");
//            textFlow.setPadding(new Insets(5, 10, 5, 10));
//            textFlow.setMaxWidth(0.8*360);
//            hBox.getChildren().add(textFlow);
//
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    vbox.getChildren().add(hBox);
//                }
//            });
//        }
//        else{
//            HBox hBox = new HBox();
//            hBox.setAlignment(Pos.CENTER_LEFT);
//            hBox.setPadding(new Insets(5, 5, 5, 10));
//            hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
//
//            Text text = new Text(messageFromClient);
//            TextFlow textFlow = new TextFlow(text);
//            textFlow.setStyle(
//                    "-fx-background-color: #b9f0ec;" +
//                            " -fx-font-size: 14;"+
//                            " -fx-background-radius: 20px;");
//            textFlow.setPadding(new Insets(5, 10, 5, 10));
//            hBox.getChildren().add(textFlow);
//
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//                    vbox.getChildren().add(hBox);
//                }
//            });
//        }
//
//    }
//
//    public void gettin(String nguon){
////        if(check_print_CNTT_ATTT == 0){
////            check_print_CNTT_ATTT++;
//        vbox_messages.getChildren().clear();
//        String sql = "select * from demo.tinnhan1 where id_tn = '"+("CNTT_"+nguon)+"' order by stt asc";
//        try {
//            Connection conn = ConnectDatabase.connect();
//            Statement state = conn.createStatement();
//            ResultSet resultSet = state.executeQuery(sql);
//            int i = 0;
//            while (resultSet.next()) {
//                String messages = resultSet.getString("message");
//                String from = resultSet.getString("from");
//                i++;
//                stt = i;
//
//                if (!messages.isEmpty() && from.equals("CNTT")) {
//                    HBox hBox = new HBox();
//                    hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
//                    hBox.setAlignment(Pos.CENTER_RIGHT);
//                    hBox.setPadding(new Insets(5, 5, 5, 10));
//                    Text text = new Text(messages);
//                    TextFlow textFlow = new TextFlow(text);
//                    textFlow.setMaxWidth(0.8 * 360);
//
//                    textFlow.setStyle("-fx-color: #060e1c; " +
//                            "-fx-background-color: #f26257;" +
//                            " -fx-font-size: 14; "+
//                            " -fx-background-radius: 20px;");
//                    textFlow.setPadding(new Insets(5, 10, 5, 10));
//                    text.setFill(Color.rgb(6, 14, 28));
//                    hBox.getChildren().add(textFlow);
//                    Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
//                    sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                    tf_message.clear();
//                }
//                else if (!messages.isEmpty() && from.equals(nguon)) {
//                    HBox hBox = new HBox();
//                    hBox.setStyle(String.valueOf(Color.color(0.2, 0, 0)));
//                    hBox.setAlignment(Pos.CENTER_LEFT);
//                    hBox.setPadding(new Insets(5, 5, 5, 10));
//                    Text text = new Text(messages);
//                    TextFlow textFlow = new TextFlow(text);
//                    textFlow.setMaxWidth(0.8 * 360);
//
//                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
//                            " -fx-font-size: 14; "+
//                            "-fx-background-color: #b9f0ec;" +
//                            " -fx-background-radius: 20px;");
//                    textFlow.setPadding(new Insets(5, 10, 5, 10));
//                    text.setFill(Color.BLACK);
//                    hBox.getChildren().add(textFlow);
////                        vbox_messages.getChildren().add(hBox);
//                    Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
//                    sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                    tf_message.clear();
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void guitin(int num){     // nút gửi
//        String messageToSend = tf_message.getText();
//        tf_message.clear();
//        if(!messageToSend.isEmpty()) {
//
//
//            try {
//                server.sendMessageToClient(messageToSend+"@@"+"CNTT");  // gủi tin nhắn
//
//                HBox hBox = new HBox();
//                hBox.setStyle(String.valueOf(Color.color(1,0,0)));
//                hBox.setAlignment(Pos.CENTER_RIGHT);
//                hBox.setPadding(new Insets(5, 5, 5, 8));
//                HBox.setHgrow(hBox, Priority.ALWAYS);
//                Text text = new Text(messageToSend);
//                TextFlow textFlow = new TextFlow(text);
//                double vboxWidth = vbox_messages.getWidth();
//                textFlow.setMaxWidth(0.8*360);
//
//                textFlow.setStyle("-fx-color: rgb(239,242,255); "+
//                        " -fx-font-size: 14; "+
//                        "-fx-background-color: #f26257;"+
//                        " -fx-background-radius: 20px;");
//                textFlow.setPadding(new Insets(5, 10, 5, 10));
//                text.setFill(Color.BLACK);
//                hBox.getChildren().add(textFlow);
////            vbox_messages.getChildren().add(hBox);
//                Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
//                sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//                luu_DB(messageToSend, num+1);
//            } catch (Exception e) {
////                e.printStackTrace();
//                System.out.println("Can't send Message to Client or you don't set up Client");
//
//                HBox hBox = new HBox();
//                hBox.setStyle(String.valueOf(Color.color(1,0,0)));
//                hBox.setAlignment(Pos.CENTER_RIGHT);
//                hBox.setPadding(new Insets(5, 5, 5, 8));
//                HBox.setHgrow(hBox, Priority.ALWAYS);
//                Text text = new Text("ERROR: CAN'T SEND TO CLIENT (CLIENT NOT FOUND)");
//                TextFlow textFlow = new TextFlow(text);
//                double vboxWidth = vbox_messages.getWidth();
//                textFlow.setMaxWidth(0.8*360);
//
//                textFlow.setStyle("-fx-color: rgb(239,242,255); "+
//                        " -fx-font-size: 14; "+
//                        "-fx-background-color: #000000;"+
//                        " -fx-background-radius: 20px;");
//                textFlow.setPadding(new Insets(5, 10, 5, 10));
//                text.setFill(Color.WHITE);
//                hBox.getChildren().add(textFlow);
////            vbox_messages.getChildren().add(hBox);
//                Platform.runLater(() -> vbox_messages.getChildren().add(hBox));
//                sp_main.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//            }
//            finally {
//                tf_message.clear();
//
//            }
//        }
//    }
//
//    public static void luu_DB(String messageToSend, int num){
//        System.out.println("Stt tin nhan can gui:::::::::::::::::::::::::::::::::::::: "+num);
////        String sql_check = "SELECT * from `demo`.`tinnhan1` where id_tn = `CNTT_"+nguon+"`, ` and stt = "+num+";";
//        String sql = "INSERT INTO `demo`.`tinnhan1` (`id_tn`, `stt`, `message`, `from`) VALUES ('CNTT_"+nguon+"', '"+(num)+"', '"+messageToSend+"', 'CNTT');";
//        try {
//            Connection conn = ConnectDatabase.connect();
//            Statement state = conn.createStatement();
//            int resultSet = state.executeUpdate(sql);
//            if(resultSet == 1){
//                System.out.println("Add message successfully");
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void set_phuongthuc(){  // CHO HẾT VÀO TRONG MỘT HÀM
//        ap_ATTT.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                nguon = "ATTT";
////                khoidong_Client();
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
////                stt = 0;
//            }
//        });
//
//        ap_CNDPT.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                check_print_CNTT_ATTT = 0;
//                nguon = "CNDPT";
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
//            }
//        });
//
//        ap_KTDTVT.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                check_print_CNTT_ATTT = 0;
//                nguon = "KTDTVT";
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
//            }
//        });
//
//        ap_CNKTD.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                check_print_CNTT_ATTT = 0;
//                nguon = "CNKTD";
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
//            }
//        });
//
//        ap_TTDPT.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                check_print_CNTT_ATTT = 0;
//                nguon = "TTDPT";
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
//            }
//        });
//        ap_CNTT.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                check_print_CNTT_ATTT = 0;
////                khoidong_Client();
//                nguon = "CNTT";
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
//            }
//        });
//
//        button_send.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                guitin(stt+1);
//                stt = 0;
//            }
//
//        });
//
//        button_refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                gettin(nguon);
//                sp_main.setVvalue(1.0);
//            }
//        });
//    }
//
//}
//
