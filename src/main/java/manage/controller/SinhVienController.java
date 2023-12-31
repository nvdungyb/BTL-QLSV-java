package manage.controller;

import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import manage.data.HashMapStudent;
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
    private ComboBox filter;
    @FXML
    private Button exporter;
    @FXML
    private Button sua;
    @FXML
    private Button addStudent;
    @FXML
    private Button delete;
    @FXML
    private Button sapXep;
    @FXML
    private Button reset;
    @FXML
    private Label warning;

    // static: thuộc tính của class, được chia sẻ cho tất cả các đối tượng của class.
    // final : thuộc tính của đối tượng, không thể thay đổi giá trị của thuộc tính này.
    // BooleanProperty: là lớp đại diện cho một giá trị boolean có thể thay đổi được trong javaFx.
    // BooleanProperty sẽ tự động gọi đến các hàm listener đã đk khi giá trị của nó bị thay đổi => update UI một cách tự động.
    private static BooleanProperty isDataChanged = new SimpleBooleanProperty(false);
    private static SinhVien inforSv = null;

    private ArrayList<SinhVien> ls = new ArrayList<>();
    private ArrayList<SinhVien> checked = new ArrayList<>();
    HashMap<String, SinhVien> map = new HashMap<>();

    @FXML
    private Button hienThi;

//    @FXML
//    private TableColumn<SinhVien, String> gioiTinh;
//    @FXML
//    private TableColumn<SinhVien, String> email;
//    @FXML
//    private TableColumn<SinhVien, String> sdt;

    public void warningPause() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(even -> {
            warning.setText("");
        });
        delay.play();
    }

    public void handleSearch(ActionEvent event) {

        String random = randomSearch.getText().trim();
        String gender = "";
        if (filter.getValue() != null) {
            gender = filter.getValue().toString();
            if (gender.equals("Tất cả"))
                gender = "";
        }

        ls.clear();
        hashMapStudent = HashMapStudent.getHashSinhVien();

        for (String key : hashMapStudent.keySet()) {
            SinhVien sv = hashMapStudent.get(key);

            if (sv.getMaSv().contains(random) || sv.getTenSv().contains(random) || sv.getNgaySinh().contains(random) || sv.getGioiTinh().contains(random) || sv.getEmail().contains(random) || sv.getSdt().contains(random) || sv.getDiaChi().contains(random) || sv.getMaLop().contains(random)) {
                if (sv.getGioiTinh().equals(gender) || gender.equals(""))
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

        // Tên getter phải đúng thì mới tìm được thuộc tính của đối tượng nhé.
        maSv.setCellValueFactory(new PropertyValueFactory<>("maSv"));
        tenSv.setCellValueFactory(new PropertyValueFactory<>("tenSv"));
        ngaySinh.setCellValueFactory(new PropertyValueFactory<>("ngaySinh"));
        que.setCellValueFactory(new PropertyValueFactory<>("diaChi"));
        lop.setCellValueFactory(new PropertyValueFactory<>("maLop"));

        ObservableList<SinhVien> data = FXCollections.observableArrayList(ls);
        tableShow.setItems(data);
        tableShow.setEditable(true);
        tableShow.refresh();
    }

    public void handleSort(ActionEvent event) {
        if (event.getSource() == sapXep) {
            ls.sort((SinhVien sv1, SinhVien sv2) -> {
                return sv1.getMaSv().compareTo(sv2.getMaSv());
            });

            ObservableList<SinhVien> data = FXCollections.observableArrayList(ls);
            tableShow.setItems(data);
            tableShow.setEditable(true);
            tableShow.refresh();
        }
    }

    public void handlerExporter(ActionEvent event) {
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

    public void handleDelete(ActionEvent event) {
        if (event.getSource() == delete) {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Gui/ConfirmDelete.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleChangeInfor(ActionEvent event) {
        if (event.getSource() == sua) {
            boolean ok = true;
            int numStudent = 0;
            for (SinhVien sv : ls) {
                if (sv.getCheckBox().getValue() == true) {
                    numStudent++;
                    if (inforSv == null) {
                        inforSv = sv;
                    } else {
                        inforSv = null;
                        break;
                    }
                }
            }

            if (inforSv == null) {
                if (numStudent > 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Chỉ chọn 1 sinh viên để sửa thông tin!");
                    alert.showAndWait();
                } else if (numStudent == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Chưa chọn sinh viên để sửa thông tin!");
                    alert.showAndWait();
                }
            }

            if (numStudent == 1 && inforSv != null) {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/Gui/SuaThongTin.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            inforSv = null;
        }
    }

    public void handleShowInfor(ActionEvent event) {
        if (event.getSource() == hienThi) {
            boolean ok = true;
            int numStudent = 0;
            for (SinhVien sv : ls) {
                if (sv.getCheckBox().getValue() == true) {
                    numStudent++;
                    if (inforSv == null) {
                        inforSv = sv;
                    } else {
                        inforSv = null;
                        break;
                    }
                }
            }

            if (inforSv == null) {
                if (numStudent > 1) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Chỉ chọn 1 sinh viên để xem thông tin!");
                    alert.showAndWait();
                } else if (numStudent == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Chưa chọn sinh viên để xem thông tin!");
                    alert.showAndWait();
                }
            }

            if (numStudent == 1 && inforSv != null) {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/Gui/StudentInfor.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            inforSv = null;
        }
    }

    public static void setIsChange(boolean check) {
        isDataChanged.set(check);
    }

    public static SinhVien getInforSv() {
        return inforSv;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (search != null) {
            search.setOnAction(this::handleSearch);
        }

        if (exporter != null) {
            exporter.setOnAction(this::handlerExporter);
        }

        if (tableShow != null) {
            tableShow.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    SinhVien sv = (SinhVien) tableShow.getSelectionModel().getSelectedItem();
                    SimpleBooleanProperty currStatus = sv.getCheckBox();
                    sv.setCheckBox(!currStatus.get());
                    System.out.println(sv.getMaSv() + " " + sv.getTenSv() + " " + sv.getDiaChi() + " " + sv.getSdt() + " " + sv.getCheckBox());
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

        if (delete != null) {
            delete.setOnAction(this::handleDelete);
        }

        if (sapXep != null) {
            sapXep.setOnAction(this::handleSort);
        }

        // Thực hiện đồng bộ khi thực hiện xác nhận xóa thông tin sinh viên tableShowDel từ ConfirmDelete và tableShow của SinhVienController.
        /*
            observableValue: đại diện cho thuộc tính isDataChanged.
            oldValue: giá trị cũ của thuộc tính isDataChanged.
            newValue: giá trị mới của thuộc tính isDataChanged.
         */
        isDataChanged.addListener((observableValue, oldValue, newValue) -> {
            if (newValue == true) {
                handleSearch(new ActionEvent());
                isDataChanged.set(false);
            }
        });

        if (sua != null) {
            sua.setOnAction(this::handleChangeInfor);
        }

        if (hienThi != null) {
            hienThi.setOnAction(this::handleShowInfor);
        }

        if (reset != null) {
            reset.setOnAction(event -> {
               for(SinhVien sv : ls){
                   sv.setCheckBox(false);
               }
            });
        }
    }
}
