package manage.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import manage.data.MonHoc;
import manage.data.SinhVien;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ThongKeController implements Initializable {
    @FXML
    private AnchorPane containt;
    @FXML
    private Label totalStudent;
    @FXML
    private Label totalCourse;
    @FXML
    private Label totalLecturer;
    @FXML
    private Label totalClass;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label infoLabel;
    @FXML
    private TableView courseTable;
    @FXML
    private TableColumn<MonHoc, Boolean> check;
    @FXML
    private TableColumn<MonHoc, String> courseId;
    @FXML
    private TableColumn<MonHoc, String> courseName;
    @FXML
    private TableColumn<MonHoc, Integer> soTinChi;
    @FXML
    private PieChart coursePieChart;
    @FXML
    private Label courseLabel;
    @FXML
    private Label totalStudentCourse;

    @FXML
    private Button courseButton;
    @FXML
    private Button target1;

    private void drawGenderPieChart(Connection con, int totalStudents) {
        String sql = "SELECT COUNT(*) FROM demo1.sinhvien WHERE gioitinh = 'Nữ'";
        int nu = 0, nam = 0;
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                nu = Integer.parseInt(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PieChart.Data slice1 = new PieChart.Data("Nữ: " + nu + " sv", nu);
        PieChart.Data slice2 = new PieChart.Data("Nam: " + (totalStudents - nu) + " sv", totalStudents - nam);
        pieChart.getData().add(slice1);
        pieChart.getData().add(slice2);
        pieChart.setStartAngle(90);
//        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(10);
        pieChart.setPrefSize(350, 350);
        infoLabel.setText("Tỉ lệ:");
        pieChart.setAnimated(true);
    }

    private void showPercentage(PieChart.Data data, PieChart chart, Label label) {
        double percentage = (data.getPieValue() / getTotal(chart)) * 100;
        label.setText("Tỉ lệ: " + String.format("%.2f%%", percentage));
    }

    private void hidePercentage(Label label) {
        label.setText("Tỉ lệ: ");
    }

    private double getTotal(PieChart chart) {
        double total = 0;
        for (PieChart.Data data : chart.getData()) {
            total += data.getPieValue();
        }
        return total;
    }

    public void handleStudentsButton(ActionEvent event) {
        MainController.setAutoScroll(true);
        MainController.setTarget(0.8);
    }

    public void drawCoursePieChart(Connection con, String maMon) {
        coursePieChart.getData().clear();
        try {
            Statement statement = con.createStatement();
            String diem;
            for (int i = 1; i <= 9; i++) {
                if (i == 1)
                    diem = "A+";
                else if (i == 2)
                    diem = "A";
                else if (i == 3)
                    diem = "B+";
                else if (i == 4)
                    diem = "B";
                else if (i == 5)
                    diem = "C+";
                else if (i == 6)
                    diem = "C";
                else if (i == 7)
                    diem = "D+";
                else if (i == 8)
                    diem = "D";
                else
                    diem = "F";

                String sql = "SELECT COUNT(*) FROM demo1.sv_mh WHERE id_mh = '" + maMon + "' AND kieu_chu = '" + diem + "';";
                System.out.println(sql);
                ResultSet rs = statement.executeQuery(sql);
                if (rs.next()) {
                    int count = Integer.parseInt(rs.getString(1));
                    PieChart.Data data = new PieChart.Data(diem + ": " + count + " sv", count);
                    coursePieChart.getData().add(data);
                }
            }

            coursePieChart.setStartAngle(90);
            coursePieChart.setAnimated(true);
            coursePieChart.getData().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    showPercentage(data, coursePieChart, courseLabel);
                });

                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    hidePercentage(courseLabel);
                });
            });

            totalStudentCourse.setText("Tổng sinh viên: " + (int)getTotal(coursePieChart));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int totalStudents = 0;
        int totalCourses = 0;
        int totalLecturers = 0;
        int totalClasses = 0;

        try {
            Connection con = ConnectDatabase.connect();
            Statement statement = con.createStatement();

            String sql = "SELECT COUNT(*) FROM demo1.sinhvien";
            ResultSet rs = statement.executeQuery(sql);
            if (rs.next()) {
                totalStudents = Integer.parseInt(rs.getString(1));
            }

            sql = "SELECT COUNT(*) FROM demo1.course";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                totalCourses = Integer.parseInt(rs.getString(1));
            }

            sql = "SELECT COUNT(DISTINCT(ten_gv)) FROM demo1.course";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                totalLecturers = Integer.parseInt(rs.getString(1));
            }

            sql = "SELECT COUNT(*) FROM demo1.lophc";
            rs = statement.executeQuery(sql);
            if (rs.next()) {
                totalClasses = Integer.parseInt(rs.getString(1));
            }

            totalStudent.setText(String.valueOf(totalStudents) + "  sinh viên");
            totalCourse.setText(String.valueOf(totalCourses) + "  môn học");
            totalLecturer.setText(String.valueOf(totalLecturers) + "  giảng viên");
            totalClass.setText(String.valueOf(totalClasses) + "  lớp học");

            drawGenderPieChart(con, totalStudents);

            pieChart.getData().forEach(data -> {
                data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
                    showPercentage(data, pieChart, infoLabel);
                });

                data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
                    hidePercentage(infoLabel);
                });
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (courseButton != null) {
            courseButton.setOnAction(this::handleStudentsButton);
        }

        if (target1 != null) {
            target1.setOnAction(event -> {
                MainController.setAutoScroll(true);
                MainController.setTarget(1);
            });
        }

        if (courseTable != null) {
            ArrayList<MonHoc> ls = new ArrayList<>();
            try {
                Connection con = ConnectDatabase.connect();
                Statement statement = con.createStatement();
                String sql = "SELECT * FROM demo1.monhoc;";
                ResultSet resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    String maMon = resultSet.getString("id_mh");
                    String tenMon = resultSet.getString("ten_mh");
                    int soTinChi = Integer.parseInt(resultSet.getString("sotc"));
                    MonHoc mh = new MonHoc(tenMon, maMon, soTinChi);
                    System.out.println(mh);
                    ls.add(mh);
                }

                check.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
                check.setCellFactory((TableColumn<MonHoc, Boolean> p) -> {                                            // Phương thức setCellFactory để tạo các ô cho cột, cụ thể là cột check. Trong phương thức biểu thức lamda nhận một tableColumn và trả về một TableCell.
                    CheckBoxTableCell<MonHoc, Boolean> cell = new CheckBoxTableCell<>();                              // Tạo một ô checkBox mới cho mỗi hàng.
                    cell.setSelectedStateCallback(param -> {                                                            // Đặt trạng thái của ô checkBox cho mỗi hàng. Biểu thức lamda đặt một hàm gọi lại để xác định trạng thái được chọn của ô checkBox, hàm này gọi lại mỗi khi trạng thái của ô checkBox thay đổi.
                        MonHoc mh = cell.getTableRow().getItem();                                                     // Lấy đối tượng SinhVien được liên kết với dòng hiện tại.
                        return mh != null ? mh.getCheckBox() : null;                                                    // Trả về thuộc tính checkBox của đối tượng SinhVien.
                    });
                    return cell;
                });
                check.setEditable(true);

                // Tên của phương thức getter sai thì cũng không tìm thấy thuộc tính của đối tượng đâu nhé.
                courseId.setCellValueFactory(new PropertyValueFactory<>("maMon"));
                courseName.setCellValueFactory(new PropertyValueFactory<>("tenMon"));
                soTinChi.setCellValueFactory(new PropertyValueFactory<>("tinChi"));

                ObservableList<MonHoc> data = FXCollections.observableArrayList(ls);
                courseTable.setItems(data);
                courseTable.setEditable(true);
                courseTable.refresh();

                courseTable.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 1) {
                        for (MonHoc mon : ls) {
                            mon.setCheckBox(false);
                        }
                        MonHoc mh = (MonHoc) courseTable.getSelectionModel().getSelectedItem();
                        SimpleBooleanProperty currStatus = mh.getCheckBox();
                        mh.setCheckBox(!currStatus.get());
                        courseTable.refresh();

                        drawCoursePieChart(con, mh.getMaMon());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}