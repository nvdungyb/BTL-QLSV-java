package View.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox trangChuContent;
    @FXML
    private VBox sinhVienContent;
    @FXML
    private VBox nhanTinContent;
    @FXML
    ScrollPane container;
    @FXML
    private Label UserName;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            trangChuContent = FXMLLoader.load(getClass().getResource("/Gui/HomePage_sub.fxml"));
            sinhVienContent = FXMLLoader.load(getClass().getResource("/Gui/sinhVien_sub.fxml"));
            nhanTinContent = FXMLLoader.load(getClass().getResource("/Gui/nhanTin_sub.fxml"));
            container.setContent(trangChuContent);
            String userName = LoginController.getUserName();
            UserName.setText(userName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


/*
Để đảm bảo rằng phần dữ liệu trên giao diện của một thành phần không bị mất đi khi load một phần giao diện khác,
nên sử dụng cơ chế để lưu trạng thái của giao diện trước khi chuyển đổi sang giao diện mới.

Cách phổ biến để thực hiện điều này là sử dụng một đối tượng Model (trong mô hình MVC) để lưu trữ dữ liệu và trạng thái của giao diện.

1) Tạo một lớp Model: để lưu trạng thái dữ liệu của giao diện, Model chứa dữ liệu và logic liên quan đến dữ liệu đó.

2) Khi load giao diện mới
   +) Trước khi load giao diện mới, lưu trạng thái hiện tại của giao diện vào Model.

3) Khi quay lại giao diện cũ, lấy dữ liệu từ Model và cập nhật giao diện hiển thị dựa trên dữ liệu này.


** Với ý tưởng trên, mỗi lần chuyển đổi giao diện chỉ cần thay đổi nội dung của Container thành nội dung được lưu trữ
trong Model tương ứng với giao diện đó. Dữ liệu trên giao diện không bị mất do các tp này đã lưu.
 */
