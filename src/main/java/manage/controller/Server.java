package manage.controller;

import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {
        try {
//			System.out.println("Connect sucessful");
            this.serverSocket = serverSocket;
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.out.println("Error creating server.!!!");
            // TODO Auto-generated catch block
            e.printStackTrace();
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
//			closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessageToClient(String messageToClient) {
        try {
            bufferedWriter.write(messageToClient);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to the Client");
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

//    public void receiveMessageFromClient(VBox vBox) {
//        new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while(socket.isConnected()) {
//                    try {
//                        String messageFromClient = bufferedReader.readLine();
//                        NhanTinController.addLabel(messageFromClient, vBox);
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                        System.out.println("Error receiving message from the client");
//                        closeEverything(socket, bufferedReader, bufferedWriter);
//                        break;
//                    }
//                }
//            }
//
//        }).start();
//    }

    public void receiveMessageFromClient(VBox vBox) {
        try {
            while (socket.isConnected()) {
                String messageFromClient = bufferedReader.readLine();
                if (messageFromClient == null || messageFromClient.isEmpty()) {
                    // Kết nối đã đóng hoặc tin nhắn rỗng, thoát khỏi vòng lặp
                    break;
                }

                Platform.runLater(() -> NhanTinController.addLabel(messageFromClient, vBox));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error receiving message from the client");
        } finally {
            // Khi kết thúc vòng lặp, đóng tất cả tài nguyên
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }
}
