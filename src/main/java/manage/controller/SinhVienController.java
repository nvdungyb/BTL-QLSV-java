package manage.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manage.data.HashMapStudent;
import manage.database.ConnectDatabase;
import manage.data.SinhVien;
import javafx.scene.control.cell.CheckBoxTableCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SinhVienController implements Initializable {
    HashMap<String, SinhVien> hashMapStudent;
    @FXML
    private TableColumn<SinhVien, Boolean> check;
    @FXML
    private Button search;
    @FXML
    TableView tableShow;
    @FXML
    private TableColumn<SinhVien, String> maSv;
    @FXML
    private TableColumn<SinhVien, String> tenSv;
    @FXML
    private TableColumn<SinhVien, String> ngaySinh;
    @FXML
    private TableColumn<SinhVien, String> que;
    @FXML
    private TableColumn<SinhVien, String> lop;
    @FXML
    private TextField randomSearch;
    @FXML
    private Button exporter;
    @FXML
    private Button add;
    @FXML
    private Button addStudent;

    private ArrayList<SinhVien> ls = new ArrayList<>();
    private ArrayList<SinhVien> checked = new ArrayList<>();
    HashMap<String, SinhVien> map = new HashMap<>();
//    @FXML
//    private TableColumn<SinhVien, String> gioiTinh;
//    @FXML
//    private TableColumn<SinhVien, String> email;
//    @FXML
//    private TableColumn<SinhVien, String> sdt;

    public void handleSearch(ActionEvent event) {
        if (event.getSource() == search) {
            String random = randomSearch.getText().trim();

            ls.clear();
            hashMapStudent = HashMapStudent.getHashSinhVien();

            for (String key : hashMapStudent.keySet()) {
                SinhVien sv = hashMapStudent.get(key);

                if (sv.getMaSv().contains(random) || sv.getTenSv().contains(random) || sv.getNgaySinh().contains(random) || sv.getGioiTinh().contains(random) || sv.getEmail().contains(random) || sv.getSdt().contains(random) || sv.getDiaChi().contains(random) || sv.getMaLop().contains(random)) {
                    ls.add(sv);
                }
            }

            // Tham số của PropertyValueFactory là của thuộc tính của SinhVien và thuộc tính này có phương thức getter.
            // Thực hiện tạo ô chẹckBox cho mỗi hàng.
            check.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
            check.setCellFactory((TableColumn<SinhVien, Boolean> p) -> {                                            // Phương thức setCellFactory để tạo các ô cho cột, cụ thể là cột check. Trong phương thức biểu thức lamda nhận một tableColumn và trả về một TableCell.
                CheckBoxTableCell<SinhVien, Boolean> cell = new CheckBoxTableCell<>();                              // Tạo một ô checkBox mới cho mỗi hàng.
                cell.setSelectedStateCallback(param -> {                                                            // Đặt trạng thái của ô checkBox cho mỗi hàng. Biểu thức lamda đặt một hàm gọi lại để xác định trạng thái được chọn của ô checkBox, hàm này gọi lại mỗi khi trạng thái của ô checkBox thay đổi.
                    SinhVien sv = cell.getTableRow().getItem();                                                     // Lấy đối tượng SinhVien được liên kết với dòng hiện tại.
                    return sv != null ? sv.getCheckBox() : null;                                                    // Trả về thuộc tính checkBox của đối tượng SinhVien.
                });
                return cell;
            });
            check.setEditable(true);
//                                                                                                                      Tại sao dùng cách này thì không được?
//                check.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
//                check.setCellFactory((CheckBoxTableCell.forTableColumn(check)));                                                                                                                                                                                                                                          Ta
//                check.setEditable(true);

            maSv.setCellValueFactory(new PropertyValueFactory<>("maSv"));
            tenSv.setCellValueFactory(new PropertyValueFactory<>("tenSv"));
            ngaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
            que.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
            lop.setCellValueFactory(new PropertyValueFactory<>("maLop"));

            ObservableList<SinhVien> data = FXCollections.observableArrayList(ls);
            tableShow.setItems(data);
            tableShow.setEditable(true);
        }
    }

    public void handerExporter(ActionEvent event) {
        if (event.getSource() == exporter) {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dữ liệu Sinh Viên");
            for (int i = 0; i < 9; i++) {
                // 16 kí tự, mỗi kí tự chiếm 256 đơn vị.
                sheet.setColumnWidth(i, 16 * 256);
            }

            int rowNum = 0;
            Row row1 = sheet.createRow(rowNum++);
            int numCell = 0;
            Cell cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Mã Sinh Viên");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Tên Sinh Viên");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Ngày Sinh");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Giới Tính");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Email");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Số Điện Thoại");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Địa Chỉ");
            cell1 = row1.createCell(numCell++);
            cell1.setCellValue("Mã Lớp");

            for (SinhVien sv : ls) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                Cell cell = row.createCell(colNum++);
                cell.setCellValue(sv.getMaSv());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getTenSv());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getNgaySinh());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getGioiTinh());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getEmail());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getSdt());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getDiaChi());
                cell = row.createCell(colNum++);
                cell.setCellValue(sv.getMaLop());
            }

            try {
                FileOutputStream outputStream = new FileOutputStream("D:\\sinhvien.xlsx");
                workbook.write(outputStream);
                workbook.close();
                System.out.println("Đã xuất file thành công!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleShow(ActionEvent event) {
        if (event.getSource() == tableShow) {
            SinhVien sv = (SinhVien) tableShow.getSelectionModel().getSelectedItem();
            System.out.println(sv.getMaSv());
            System.out.println(sv.getTenSv());
        }
    }

//    public void handleCheck(ActionEvent event) {
//        ObservableList<SinhVien> items = tableShow.getItems();
//        for (SinhVien sv : items) {
//            boolean checkedSv = sv.getCheckBox();
//            if (checkedSv) {
//                checked.add(sv);
//            }
//        }
//
//        for (SinhVien x : checked) {
//            System.out.println(x);
//        }
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (search != null) {
            search.setOnAction(this::handleSearch);
        }

        if (exporter != null) {
            exporter.setOnAction(this::handerExporter);
        }

        if (tableShow != null) {
            tableShow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    SinhVien sv = (SinhVien) tableShow.getSelectionModel().getSelectedItem();
                    SimpleBooleanProperty currStatus = sv.getCheckBox();
                    sv.setCheckBox(!currStatus.get());
                    System.out.println(sv.getMaSv() + " " + sv.getTenSv() + " " + sv.getCheckBox());
                    tableShow.refresh();
                }
            });
        }

        if (addStudent != null) {
            addStudent.setOnAction(event -> {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Gui/ThemSinhVien.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
