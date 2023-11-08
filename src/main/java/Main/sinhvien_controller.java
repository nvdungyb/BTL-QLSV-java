package Main;

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

public class sinhvien_controller implements Initializable {
    @FXML
    private CheckBox choose;
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

    private ArrayList<SinhVien> ls = new ArrayList<>();
//    @FXML
//    private TableColumn<SinhVien, String> gioiTinh;
//    @FXML
//    private TableColumn<SinhVien, String> email;
//    @FXML
//    private TableColumn<SinhVien, String> sdt;

    public void handleSearch(ActionEvent event) {
        if (event.getSource() == search) {
            String WHERE = "";
            String sql = "SELECT * FROM java_project.sinhvien" + WHERE;

            String random = randomSearch.getText().trim();

            HashMap<String, SinhVien> map = new HashMap<>();
            ls.clear();
            try {
                Connection connection = ConnectDatabase.connect();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String maSv = resultSet.getString("id_sv");
                    String tenSv = resultSet.getString("ten_sv");
                    String ngaySinh = resultSet.getString("ngaysinh");
                    String gioiTinh = resultSet.getString("gioitinh");
                    String email = resultSet.getString("email");
                    String sdt = resultSet.getString("sodt");
                    String que = resultSet.getString("que");
                    String lop = resultSet.getString("id_lhc");

                    SinhVien sv = new SinhVien(maSv, tenSv, ngaySinh, gioiTinh, email, sdt, que, lop);
                    if (sv.getMaSv().contains(random) || sv.getTenSv().contains(random) || sv.getNgaySinh().contains(random) || sv.getGioiTinh().contains(random) || sv.getEmail().contains(random) || sv.getSdt().contains(random) || sv.getDiaChi().contains(random) || sv.getMaLop().contains(random)) {
                        map.put(maSv, sv);
                        ls.add(sv);
                    }
                }

                // Tham số của PropertyValueFactory là của đối tượng SinhVien.
                maSv.setCellValueFactory(new PropertyValueFactory<>("maSv"));
                tenSv.setCellValueFactory(new PropertyValueFactory<>("tenSv"));
                ngaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
                que.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
                lop.setCellValueFactory(new PropertyValueFactory<>("maLop"));

                ObservableList<SinhVien> data = FXCollections.observableArrayList(ls);
                tableShow.setItems(data);

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void handerExporter(ActionEvent event) {
        if (event.getSource() == exporter) {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Dữ liệu Sinh Viên");
            for(int i = 0; i < 9; i++) {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (search != null) {
            search.setOnAction(this::handleSearch);
        }

        if (exporter != null) {
            exporter.setOnAction(this::handerExporter);
        }
    }
}
