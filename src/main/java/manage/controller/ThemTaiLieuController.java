package manage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manage.database.ConnectDatabase;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ThemTaiLieuController implements Initializable {
    @FXML
    public TextField link;
    @FXML
    public Button fileChooser;
    @FXML
    public Button submit;
    @FXML
    public Label warningLable;

    @FXML
    public TextField khoa;

    public void hanlderFileChooser(ActionEvent event){
        if(event.getSource() == fileChooser){
            FileChooser fc = new FileChooser();

//            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Tệp PDF (*.pdf)", "*.pdf", "*.txt");
//            fc.getExtensionFilters().add(extFilter);

            FileChooser.ExtensionFilter extFilterPDF = new FileChooser.ExtensionFilter("Tệp PDF (*.pdf)", "*.pdf");
            FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("Tệp Text (*.txt)", "*.txt");
            FileChooser.ExtensionFilter extFilterIN = new FileChooser.ExtensionFilter("Tệp Input (*.in)", "*.in");
            FileChooser.ExtensionFilter extFilterOUT = new FileChooser.ExtensionFilter("Tệp Output (*.out)", "*.out");
            FileChooser.ExtensionFilter extFilterDOCX = new FileChooser.ExtensionFilter("Tệp Word (*.docx)", "*.docx");
            FileChooser.ExtensionFilter extFilterDOC = new FileChooser.ExtensionFilter("Tệp Word 97-2003 (*.doc)", "*.doc");
            FileChooser.ExtensionFilter extFilterXLSX = new FileChooser.ExtensionFilter("Tệp Excel (*.xlsx)", "*.xlsx");
            FileChooser.ExtensionFilter extFilterCSV = new FileChooser.ExtensionFilter("Tệp CSV (*.csv)", "*.csv");
            FileChooser.ExtensionFilter extFilterMP3 = new FileChooser.ExtensionFilter("Tệp MP3 (*.mp3)", "*.mp3");
            FileChooser.ExtensionFilter extFilterMP4 = new FileChooser.ExtensionFilter("Tệp MP4 (*.mp4)", "*.mp4");
            FileChooser.ExtensionFilter extFilterPPTX = new FileChooser.ExtensionFilter("Tệp PowerPoint (*.pptx)", "*.pptx");

//            FileChooser fileChooser = new FileChooser();
            fc.getExtensionFilters().addAll(
                    extFilterPDF, extFilterTXT, extFilterIN, extFilterOUT,
                    extFilterDOCX, extFilterDOC, extFilterXLSX, extFilterCSV,
                    extFilterMP3, extFilterMP4, extFilterPPTX
            );

            File selectedFile = fc.showOpenDialog(null);
            if(selectedFile != null){
                String path = selectedFile.getAbsolutePath();
                link.setText(path);
            } else{
                System.out.println("File is not valid!");
            }
        }
    }

    public void hanlderSubmit(ActionEvent event) {
        if (event.getSource() == submit) {

            String url = link.getText();
            System.out.println("URL:::::::::::::::" + url);

//            TaiLieuController tlc = new TaiLieuController();
//            String from = tlc.nguon();
//            if(from == null)
//                from = "CNTT";
            String from = khoa.getText();
            if(url.trim().equals("") || from.trim().equals("")){
                warningLable.setText("Vui lòng nhập đầy đủ thông tin!");
            }
            else{
                String sql = "INSERT INTO `demo`.`tailieu` (`url`, `khoa`) VALUES (?, ?);";
                try (Connection conn = ConnectDatabase.connect();
                     PreparedStatement statement = conn.prepareStatement(sql)) {

                    // Sử dụng truy vấn SQL tham số hóa để tránh vấn đề với ký tự đặc biệt
                    statement.setString(1, url);
                    statement.setString(2, from);

                    int result = statement.executeUpdate();
                    if (result != 0) {
                        System.out.println("Add File successfully!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    } else {
                        System.out.println("File already exists");
                    }

                    Stage stage = (Stage) submit.getScene().getWindow();
                    stage.close();
                    System.out.println("Đã thêm thành công!");
                } catch (SQLException e) {
                    System.out.println("Error at add file");
                    System.out.println(e.getMessage());
                }
            }

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(submit != null)
            submit.setOnAction(this::hanlderSubmit);

        if(fileChooser != null)
            fileChooser.setOnAction(this::hanlderFileChooser);
    }
}
