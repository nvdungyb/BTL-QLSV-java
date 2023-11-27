package manage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import manage.data.CongViec;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        lsCongViec.clear();
        String sql = "SELECT * FROM demo.congviec";
        try {
            Connection conn = ConnectDatabase.connect();
            Statement state = conn.createStatement();
            ResultSet resultset = state.executeQuery(sql);
            while(resultset.next()){
                String MoTa = resultset.getString("mota");
                String TrangThai = resultset.getString("trangthai");
                String NgayThang = resultset.getString("ngaythang");
                lsCongViec.add(new CongViec(MoTa, TrangThai, NgayThang));
                System.out.println(new CongViec(MoTa, TrangThai, NgayThang));
            }
            return lsCongViec;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



    public void hanlderSubmit(ActionEvent event) {
        if (event.getSource() == submit) {
            String MoTa = this.moTa.getText();
            String TrangThai = this.trangThai.getText();
            String NgayThang = this.ngayThang.getValue().toString();
            System.out.println(new CongViec(MoTa, TrangThai, NgayThang));

            lsCongViec.add(new CongViec(MoTa, TrangThai, NgayThang));
            for(CongViec i : lsCongViec){
                add_DB(i);
            }

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

    public void add_DB(CongViec cv){
        String MoTa = cv.getMoTa();
        String TrangThai = cv.getTrangThai();
        String NgayThang = cv.getNgayThang();

        String sql = "INSERT INTO `demo`.`congviec` (`mota`, `trangthai`, `ngaythang`) VALUES ('"+MoTa+"', '"+TrangThai+"', '"+NgayThang+"');";
        try {
            Connection conn = ConnectDatabase.connect();
            Statement state = conn.createStatement();
            int result = state.executeUpdate(sql);
            if(result != 0){
                System.out.println("Thêm công việc thành công");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}