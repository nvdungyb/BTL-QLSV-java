package manage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import manage.data.HashMapStudent;
import manage.data.SinhVien;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ChangeInforController implements Initializable {
    @FXML
    private TextField tenSv;
    @FXML
    private Label maSv;
    @FXML
    private TextField diaChi;
    @FXML
    private TextField email;
    @FXML
    private TextField sdt;
    @FXML
    private DatePicker ngaySinh;
    @FXML
    private ChoiceBox gioiTinh;
    @FXML
    private ChoiceBox khoa;
    @FXML
    private TextField lop;
    @FXML
    private Button luu;
    @FXML
    private Button huy;


    public void handleLuu(ActionEvent event){
        if(event.getSource() == luu){
            String name = tenSv.getText().trim();
            String id_sv = maSv.getText().trim();
            String address = diaChi.getText().trim();
            String mail = email.getText().trim();
            String phone = sdt.getText().trim();
            String dob = ngaySinh.getValue().toString();
            String gender = gioiTinh.getValue().toString();
            String class_id = lop.getText().trim();
//            String faculty = khoa.getValue().toString();

            if(name.equals("") || id_sv.equals("") || address.equals("") || mail.equals("") || phone.equals("") || dob.equals("") || gender.equals("") || class_id.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng nhập đầy đủ thông tin!");
                alert.showAndWait();
            }else{
                String sql = "UPDATE java_project.sinhvien SET ten_sv = '" + name + "', ngaysinh = '" + dob + "', que = '" + address + "', email = '" + mail + "', gioitinh = '" + gender + "', sodt = '" + phone + "', id_lhc = '" + class_id + "' WHERE id_sv = '" + id_sv + "'";
                System.out.println(sql);
                try {
                    Connection con = ConnectDatabase.connect();
                    Statement statement = con.createStatement();
                    statement.executeUpdate(sql);

                    // Nếu truy vấn thành công. Cập nhật dữ liệu trong HashMap và reload lại bảng tableView.
                    SinhVien sv = new SinhVien(id_sv, name, dob, gender, mail, phone, address, class_id);
                    HashMapStudent.getHashSinhVien().put(id_sv, sv);
                    SinhVienController.setIsChange(true);
                    con.close();

                    Stage stage = (Stage) luu.getScene().getWindow();
                    stage.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        HashMap<String, SinhVien> hashMapStudent = HashMapStudent.getHashSinhVien();
        SinhVien svChange = null;
        for (String x : hashMapStudent.keySet()) {
            SinhVien sv = hashMapStudent.get(x);
            if (sv.getCheckBox().getValue()) {
                svChange = sv;
                break;
            }
        }

        if (svChange != null) {
            maSv.setText(svChange.getMaSv());
            tenSv.setText(svChange.getTenSv());
            diaChi.setText(svChange.getDiaChi());
            email.setText(svChange.getEmail());
            sdt.setText(svChange.getSdt());
            ngaySinh.setValue(LocalDate.parse(svChange.getNgaySinh().toString()));
            gioiTinh.setValue(svChange.getGioiTinh());
            lop.setText(svChange.getMaLop());
        }

        if(luu != null){
            luu.setOnAction(this::handleLuu);
        }

        if(huy != null){
            huy.setOnAction(event ->{
               Stage stage = (Stage) huy.getScene().getWindow();
               stage.close();
            });
        }
    }
}
