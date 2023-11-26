package manage.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import manage.data.HashMapStudent;
import manage.data.SinhVien;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ConfirmDelete implements Initializable {
    @FXML
    private TableColumn<SinhVien, Boolean> checkDel;
    @FXML
    private TableView tableShowDel;
    @FXML
    private TableColumn<SinhVien, String> maSvDel;
    @FXML
    private TableColumn<SinhVien, String> tenSvDel;
    @FXML
    private TableColumn<SinhVien, String> ngaySinhDel;
    @FXML
    private TableColumn<SinhVien, String> queDel;
    @FXML
    private TableColumn<SinhVien, String> lopDel;
    @FXML
    private Button show;
    @FXML
    private Label statusConfirm;
    @FXML
    private Button confirm;

    ArrayList<SinhVien> ls = new ArrayList<>();

    public void handleShow(ActionEvent event) {
        if (event.getSource() == show) {

            ls.clear();
            HashMap<String, SinhVien> hashMapStudent = HashMapStudent.getHashSinhVien();
            int count = 0;
            for (String x : hashMapStudent.keySet()) {
                if (hashMapStudent.get(x).getCheckBox().getValue()) {
                    count++;
                    ls.add(hashMapStudent.get(x));
                }
            }

            statusConfirm.setText("Số lượng: " + count + " sv");

            checkDel.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
            checkDel.setCellFactory((TableColumn<SinhVien, Boolean> p) -> {                                            // Phương thức setCellFactory để tạo các ô cho cột, cụ thể là cột check. Trong phương thức biểu thức lamda nhận một tableColumn và trả về một TableCell.
                CheckBoxTableCell<SinhVien, Boolean> cell = new CheckBoxTableCell<>();                              // Tạo một ô checkBox mới cho mỗi hàng.
                cell.setSelectedStateCallback(param -> {                                                            // Đặt trạng thái của ô checkBox cho mỗi hàng. Biểu thức lamda đặt một hàm gọi lại để xác định trạng thái được chọn của ô checkBox, hàm này gọi lại mỗi khi trạng thái của ô checkBox thay đổi.
                    SinhVien sv = cell.getTableRow().getItem();                                                     // Lấy đối tượng SinhVien được liên kết với dòng hiện tại.
                    return sv != null ? sv.getCheckBox() : null;                                                    // Trả về thuộc tính checkBox của đối tượng SinhVien.
                });
                return cell;
            });
            checkDel.setEditable(true);

            maSvDel.setCellValueFactory(new PropertyValueFactory<>("maSv"));
            tenSvDel.setCellValueFactory(new PropertyValueFactory<>("tenSv"));
            ngaySinhDel.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
            queDel.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
            lopDel.setCellValueFactory(new PropertyValueFactory<>("maLop"));

            ObservableList<SinhVien> data = FXCollections.observableArrayList(ls);
            tableShowDel.setItems(data);
            tableShowDel.setEditable(true);
        }
    }

    public void handleConfirm(ActionEvent event) {
        HashMap<String, SinhVien> hashMapStudent = HashMapStudent.getHashSinhVien();
        if (event.getSource() == confirm) {
            int count = 0;
            int size = ls.size();
            Iterator iterator = ls.iterator();
            while (iterator.hasNext()) {
                SinhVien sv = (SinhVien) iterator.next();
                if (sv.getCheckBox().getValue()) {
                    try {
                        String sql = "DELETE FROM demo.sinhvien WHERE id_sv = '" + sv.getMaSv() + "';";
                        Connection con = ConnectDatabase.connect();
                        Statement statement = con.createStatement();
                        statement.execute(sql);
                        count++;

                        hashMapStudent.remove(sv.getMaSv());
                        iterator.remove();
                    } catch (Exception e) {
                        System.out.println("Không xóa được " + sv);
                    }
                }
            }
            System.out.println("Xóa thành công " + count + " trên " + size + " sinh viên");
            if (count > 0)
                SinhVienController.setIsChange(true);
        }

        ObservableList<SinhVien> updatedDate = FXCollections.observableArrayList(ls);
        tableShowDel.setItems(updatedDate);
        statusConfirm.setText("Số lượng: " + ls.size() + " sv");
        tableShowDel.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (show != null) {
            show.setOnAction(this::handleShow);
        }

        if (confirm != null) {
            confirm.setOnAction(this::handleConfirm);
        }

        if (tableShowDel != null) {
            tableShowDel.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    SinhVien sv = (SinhVien) tableShowDel.getSelectionModel().getSelectedItem();
                    SimpleBooleanProperty currStatus = sv.getCheckBox();
                    sv.setCheckBox(!currStatus.get());
                    System.out.println(sv.getMaSv() + " " + sv.getTenSv() + " " + sv.getCheckBox());
                    tableShowDel.refresh();
                }
            });
        }
    }
}
