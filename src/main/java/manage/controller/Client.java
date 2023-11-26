//package application;
package manage.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


import javafx.application.Platform;
import javafx.scene.layout.VBox;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Client(Socket socket) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch(IOException e) {
            System.out.println("Error creating client");
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessageToServer(String messageToServer) {
        try {
            bufferedWriter.write(messageToServer);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the client");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    //    public void receiveMessageFromServer(VBox vBox) {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while(socket.isConnected()) {
//                    try {
//                        String messageFromServer = bufferedReader.readLine();
//                        NhanTinController.addLabel(messageFromServer, vBox);
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                        System.out.println("Error receiving message from the client (Server)");
//                        closeEverything(socket, bufferedReader, bufferedWriter);
//                        break;
//                    }
//                }
//            }
//
//        }).start();
//    }
    public void receiveMessageFromServer(VBox vBox) {
        try {
            while (socket.isConnected()) {
                String messageFromServer = bufferedReader.readLine();
                if (messageFromServer == null || messageFromServer.isEmpty()) {
                    // Kết nối đã đóng hoặc tin nhắn rỗng, thoát khỏi vòng lặp
                    break;
                }

                Platform.runLater(() -> NhanTinController.addLabel(messageFromServer, vBox));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error receiving message from the server");
        } finally {
            // Khi kết thúc vòng lặp, đóng tất cả tài nguyên
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }


    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
            if(socket != null) {
                socket.close();
            }

        } catch(IOException e) {
            e.printStackTrace();
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
}
