package manage.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import manage.data.HashMapStudent;
import manage.data.SinhVien;
import manage.database.ConnectDatabase;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox trangChuContent;
    @FXML
    private VBox sinhVienContent;
    @FXML
    private VBox nhanTinContent;
    @FXML
    private VBox thongKeContent;
    @FXML
    ScrollPane container;
    @FXML
    private Label UserName;
    @FXML
    private ImageView userImage;

    private static SimpleBooleanProperty autoScroll = new SimpleBooleanProperty(false);
    private static double targetScrollPane = 0;

    HashMap<String, SinhVien> hashMapStudent = HashMapStudent.getHashSinhVien();

    @FXML
    public void trangchuAction(ActionEvent event) throws IOException {
        container.setContent(trangChuContent);
    }

    @FXML
    public void sinhvienAction(ActionEvent event) throws IOException {
        container.setContent(sinhVienContent);
    }

    @FXML
    public void nhantinAction(ActionEvent event) throws IOException {
        container.setContent(nhanTinContent);
    }

    public static void setAutoScroll(boolean auto) {
        autoScroll.set(auto);
    }

    public static void setTarget(double target) {
        targetScrollPane = target;
    }

    @FXML
    public void thongkeAction(ActionEvent event) {
        container.setContent(thongKeContent);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            trangChuContent = FXMLLoader.load(getClass().getResource("/Gui/HomePage_sub.fxml"));
            sinhVienContent = FXMLLoader.load(getClass().getResource("/Gui/SinhVien_sub.fxml"));
            nhanTinContent = FXMLLoader.load(getClass().getResource("/Gui/NhanTin_sub.fxml"));
            thongKeContent = FXMLLoader.load(getClass().getResource("/Gui/ThongKe.fxml"));
            container.setContent(trangChuContent);
            String userName = LoginController.getUserName();
            UserName.setText(userName);

            // Không hiển thị được ảnh nếu lấy từ cơ sở dữ liệu.
            String urlImage = LoginController.getUrlImage();
            System.out.println(urlImage);
            try {
//                Image image = new Image(getClass().getResource(urlImage).toExternalForm());
//                System.out.println(urlImage);
//                userImage.setImage(image);
                userImage.setImage(new javafx.scene.image.Image("C://Users//acer//OneDrive//Pictures//IMG_20220526_192323.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            ConnectDatabase con = new ConnectDatabase();
            Statement statement = con.connect().createStatement();
            String sql = "SELECT * FROM java_project.sinhvien;";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String maSv = resultSet.getString("id_sv");
                String tenSv = resultSet.getString("ten_sv");
                String ngaySinh = resultSet.getString("dob");
                String gioiTinh = resultSet.getString("gioitinh");
                String email = resultSet.getString("email");
                String sdt = resultSet.getString("sodt");
                String que = resultSet.getString("quequan");
                String lop = resultSet.getString("id_lophc");

                SinhVien sv = new SinhVien(maSv, tenSv, ngaySinh, gioiTinh, email, sdt, que, lop);
                hashMapStudent.put(maSv, sv);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        autoScroll.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                double scrollTime = 500;                                                               // Định thời gian cho thanh cuộn chạy.
                double target = targetScrollPane;                                                       // Định vị trí cuối cùng của thanh cuộn. (0.0 -> 1.0)

                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(scrollTime), new KeyValue(container.vvalueProperty(), target))
                );

                timeline.play();
            } else {
                container.vvalueProperty().unbind();                                                    // giải phóng bất kì ràng buộc nào được thiết lập cho thuộc tính vvalue của thanh cuộn.
            }
            setAutoScroll(false);
        });
    }
}
