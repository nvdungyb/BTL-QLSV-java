package Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class NewsController implements Initializable {
    @FXML
    private TextField title;
    @FXML
    private TextField link;
    @FXML
    private Button fileChooser;
    @FXML
    private Button submit;
    @FXML
    private Label warningLable;

    private static NewsData data = new NewsData();

    public void hanlderFileChooser(ActionEvent event){
        if(event.getSource() == fileChooser){
            FileChooser fc = new FileChooser();

            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Tệp PDF (*.pdf)", "*.pdf", "*.txt");
            fc.getExtensionFilters().add(extFilter);

            File selectedFile = fc.showOpenDialog(null);
            if(selectedFile != null){
                String path = selectedFile.getAbsolutePath();
                link.setText(path);
            } else{
                System.out.println("File is not valid!");
            }
        }
    }

    public void hanlderSubmit(ActionEvent event){
        if(event.getSource() == submit){
            if(title.getText().trim().equals("") || link.getText().trim().equals("")){
                warningLable.setText("Vui lòng nhập đầy đủ thông tin!");
            } else{
                data.setTitle(title.getText());
                data.setLink(link.getText());

                Stage stage = (Stage) submit.getScene().getWindow();
                stage.close();
                System.out.println("Đã thêm thành công!");
            }
        }
    }

    public static NewsData getData(){
        return data;
    }

    public static void setData(String title, String link){
        data.setTitle(title);
        data.setLink(link);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(submit != null)
            submit.setOnAction(this::hanlderSubmit);

        if(fileChooser != null)
            fileChooser.setOnAction(this::hanlderFileChooser);
    }
}
