package manage.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manage.data.SinhVien;
import manage.database.ConnectDatabase;

import java.io.IOException;
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
    private Label xemTKB;
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
    private static SinhVien sv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sv = SinhVienController.getInforSv();
        lineChart.setLayoutY(4.0);

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
            for (int i = 2; i <= 10; i++) {
                String query = "SELECT diem.id_sv, SUM(diem.diemTK4 * diem.sotc) AS tongDiem, SUM(sotc) AS tongtc, diem.ky" +
                        " FROM(" +
                        "SELECT d.*, m.ten_mh, sotc" +
                        " FROM sv_mh d" +
                        " INNER JOIN monhoc m ON d.id_mh=m.id_mh" +
                        " WHERE (d.id_sv='" + sv.getMaSv() + "') AND (d.ky='" + (i - 1) + "')" +
                        "GROUP BY d.id_sv, m.id_mh, d.ky) as diem" +
                        " GROUP BY diem.id_sv;";

                System.out.println(query);
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    double tongDiem = Double.parseDouble(resultSet.getString("tongDiem"));
                    double tongtc = Double.parseDouble(resultSet.getString("tongtc"));
                    double gpa = tongDiem / tongtc;
                    System.out.println(gpa);
                    series.getData().add(new XYChart.Data<>(i - 1, gpa));
                }
            }


//            series.getData().add(new XYChart.Data<>(1, 3.2));
//            series.getData().add(new XYChart.Data<>(2, 3.5));
//            series.getData().add(new XYChart.Data<>(3, 3.7));
//            series.getData().add(new XYChart.Data<>(4, 3.8));

            // Thêm series vào đồ thị
            lineChart.getData().add(series);
        } catch (Exception e) {
            e.printStackTrace();
        }

        xemTKB.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Gui/LichHoc1.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public static  String getMSV(){
        return sv.getMaSv();
    }

}