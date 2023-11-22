package manage.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import manage.data.SinhVien;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class StudentInforController implements Initializable {
    @FXML
    private LineChart<Number, Number> lineChart;
    @FXML
    private Label maSv;
    @FXML
    private Label name;
    @FXML
    private Label address;
    @FXML
    private Label maLop;
    @FXML
    private Label gender;
    @FXML
    private Label email;
    @FXML
    private Label phone;
    @FXML
    private Label birthDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        SinhVien sv = SinhVienController.getInforSv();

        if (sv != null) {
            name.setText(sv.getTenSv());
            maSv.setText(sv.getMaSv());
            address.setText(sv.getDiaChi());
            maLop.setText(sv.getMaLop());
            gender.setText(sv.getGioiTinh());
            email.setText(sv.getEmail());
            phone.setText(sv.getSdt());
            birthDate.setText(sv.getNgaySinh());
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Đồ thị bảng điểm");

        try {
            Connection con = ConnectDatabase.connect();
            Statement statement = con.createStatement();
            String sql = "SELECT * FROM java_project.bangdiem WHERE id_sv = '" + sv.getMaSv() + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                for (int i = 2; i <= 10; i++) {
                    if (resultSet.getString("ki" + (i - 1)) != null) {
                        double gpa = Double.parseDouble(resultSet.getString("ki" + (i - 1)));
                        series.getData().add(new XYChart.Data<>(i - 1, gpa));
                    }
                }
            }

            System.out.println(sql);
            System.out.println(resultSet);

//            series.getData().add(new XYChart.Data<>(1, 3.2));
//            series.getData().add(new XYChart.Data<>(2, 3.5));
//            series.getData().add(new XYChart.Data<>(3, 3.7));
//            series.getData().add(new XYChart.Data<>(4, 3.8));

            // Thêm series vào đồ thị
            lineChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
