package manage.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddStudentController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(AddStudentController.class.getName());
    @FXML
    private TextField maSv;
    @FXML
    private TextField tenSv;
    @FXML
    private TextField soDt;
    @FXML
    private ChoiceBox gioiTinh;
    @FXML
    private TextField diaChi;
    @FXML
    private TextField lop;
    @FXML
    private ChoiceBox khoa;
    @FXML
    private DatePicker ngaySinh;
    @FXML
    private TextField email;
    @FXML
    private Button them;
    @FXML
    private Button huy;
    @FXML
    private Label status;

    public void statusPause() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(even -> {
            status.setText("");
        });
        delay.play();
    }

    public void handleThem(ActionEvent event) {
        if (event.getSource() == them) {
            try {
                String name = tenSv.getText().trim();
                String id = maSv.getText().trim();
                String gender = gioiTinh.getValue().toString();
                String phone = soDt.getText().trim();
                String address = diaChi.getText().trim();
                String classId = lop.getText().trim();
                String faculty = khoa.getValue().toString();
                String dob = ngaySinh.getValue().toString();
                String mail = email.getText().trim();

                if (name.equals("") || id.equals("") || gender.equals("") || phone.equals("") || address.equals("") || classId.equals("") || faculty.equals("") || dob.equals("") || mail.equals("")) {
                    status.setText("Vui lòng nhập đầy đủ thông tin!");
                    statusPause();
                } else {
                    ConnectDatabase con = new ConnectDatabase();
                    Statement statement = con.connect().createStatement();

                    String sql = "INSERT INTO java_project.sinhvien VALUES('" + id + "', '" + name + "', '" + dob + "', '" + address + "', '" + mail + "', '" + gender + "', '" + phone + "', '" + classId + "');";
                    System.out.println(sql);
                    statement.execute(sql);
                    status.setText("Thêm thành công!");
                }
            } catch (Exception e) {
                status.setText("Vui lòng nhập đầy đủ thông tin!");
                statusPause();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (them != null) {
            them.setOnAction(this::handleThem);
        }

        if (huy != null) {
            huy.setOnAction(event -> {
                Stage stage = (Stage) huy.getScene().getWindow();
                stage.close();
            });

        }
    }
}
