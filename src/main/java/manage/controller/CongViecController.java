package manage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import manage.data.CongViec;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CongViecController implements Initializable {
    @FXML
    private TextField moTa;
    @FXML
    private TextField trangThai;
    @FXML
    private DatePicker ngayThang;
    @FXML
    private Button submit;

    public static ArrayList<CongViec> lsCongViec = new ArrayList<>();

    public static ArrayList<CongViec> getLsCongViec() {
        return lsCongViec;
    }

    public void hanlderSubmit(ActionEvent event) {
        if (event.getSource() == submit) {
            String MoTa = this.moTa.getText();
            String TrangThai = this.trangThai.getText();
            String NgayThang = this.ngayThang.getValue().toString();
            System.out.println(new CongViec(MoTa, TrangThai, NgayThang));

            lsCongViec.add(new CongViec(MoTa, TrangThai, NgayThang));

            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (submit != null) {
            submit.setOnAction(this::hanlderSubmit);
        }
    }
}