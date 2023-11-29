package manage.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import manage.data.CongViec;
import manage.data.NewsData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import manage.database.ConnectDatabase;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Scanner;

public class HomePageController implements Initializable {
    @FXML
    private Button AddNews;
    @FXML
    private VBox Notification;
    @FXML
    private ScrollPane NotificationPane;
    @FXML
    private AnchorPane imagePane;
    @FXML
    private Label textNotification;
    @FXML
    private ImageView imageView;
    @FXML
    private Button themCv;
    @FXML
    private Button xoaCv;
    @FXML
    private TableView workTable;
    @FXML
    private TableColumn<CongViec, String> cvColumn;
    @FXML
    private TableColumn<CongViec, String> ttColumn;
    @FXML
    private TableColumn<CongViec, String> dateColumn;
    @FXML
    private TableColumn<CongViec, Boolean> checkColumn;

    private static ArrayList<CongViec> ls = new ArrayList<>();

    public void hanlderShowNews(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button btn = (Button) event.getSource();
            String link = (String) btn.getUserData();
            System.out.println(link);

            imagePane.setVisible(false);
            NotificationPane.setVisible(true);
            textNotification.setText("");

            try {
                Scanner sc = new Scanner(new File(link));
                while (sc.hasNextLine()) {
                    Text text = new Text(sc.nextLine() + "\n");
                    textNotification.setText(textNotification.getText() + text.getText());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void hanlderAddNews(ActionEvent event) {
        if (event.getSource() == AddNews) {
            Stage stage = new Stage();
            Scene scene = new Scene(new Pane());

            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("/Gui/News.fxml")));
                stage.setScene(scene);
                stage.showAndWait();

                NewsData data = new NewsData(NewsController.getData().getTitle(), NewsController.getData().getLink());
                // ĐK để lần sau khi thêm tin mà bị gián đoạn thì sẽ không hiển thị tin trước đó lên nữa.
                NewsController.setData(null, null);

                if (data.getLink() != null && data.getTitle() != null) {
                    Button btn = new Button();
                    btn.setWrapText(true);
                    btn.setFont(javafx.scene.text.Font.font("Times New Roman", 20));
                    btn.setMinSize(120, 50);
                    btn.setMaxSize(500, 70);

                    btn.setText(data.getTitle());
                    btn.setUserData(data.getLink());

                    btn.setOnAction(this::hanlderShowNews);
                    btn.setBorder(new Border(new BorderStroke(Color.rgb(215, 211, 212), BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(10, 10, 10, 10))));

                    if (Notification != null) {
                        Notification.getChildren().add(btn);
                    } else {
                        System.out.println("Notification is null!");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showWorkInfo() {
        checkColumn.setCellValueFactory(new PropertyValueFactory<>("check"));
        checkColumn.setCellFactory((TableColumn<CongViec, Boolean> p) -> {                                            // Phương thức setCellFactory để tạo các ô cho cột, cụ thể là cột check. Trong phương thức biểu thức lamda nhận một tableColumn và trả về một TableCell.
            CheckBoxTableCell<CongViec, Boolean> cell = new CheckBoxTableCell<>();                              // Tạo một ô checkBox mới cho mỗi hàng.
            cell.setSelectedStateCallback(param -> {                                                            // Đặt trạng thái của ô checkBox cho mỗi hàng. Biểu thức lamda đặt một hàm gọi lại để xác định trạng thái được chọn của ô checkBox, hàm này gọi lại mỗi khi trạng thái của ô checkBox thay đổi.
                CongViec sv = cell.getTableRow().getItem();                                                     // Lấy đối tượng SinhVien được liên kết với dòng hiện tại.
                return sv != null ? sv.getCheck() : null;                                                    // Trả về thuộc tính checkBox của đối tượng SinhVien.
            });
            return cell;
        });
        checkColumn.setEditable(true);

        cvColumn.setCellValueFactory(new PropertyValueFactory("moTa"));
        ttColumn.setCellValueFactory(new PropertyValueFactory("trangThai"));
        dateColumn.setCellValueFactory(new PropertyValueFactory("ngayThang"));

        ObservableList<CongViec> list = FXCollections.observableArrayList(ls);
        workTable.setItems(list);
        workTable.setEditable(true);
        workTable.refresh();
    }

    public void hanlderAddCV(ActionEvent event) {
        if (event.getSource() == themCv) {
            Stage stage = new Stage();
            Scene scene = new Scene(new Pane());

            try {
                scene.setRoot(FXMLLoader.load(getClass().getResource("/Gui/Work.fxml")));
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }

            CongViec cv = CongViecController.getCongViec();
            ls.add(cv);
            showWorkInfo();
        }
    }

    public void handleDeleteWork(ActionEvent event) {
        Connection con = ConnectDatabase.connect();
        if (event.getSource() == xoaCv) {
            // Lưu ý khi loại bỏ đối tượng trong ArrayList khi đang duyệt.
            Iterator<CongViec> it = ls.iterator();
            while (it.hasNext()) {
                CongViec cv = it.next();
                if (cv.getCheck().getValue()) {
                    try {
                        String sql = "DELETE FROM congviec WHERE moTa = '" + cv.getMoTa() + "' AND trangThai = '" + cv.getTrangThai() + "' AND ngayThang = '" + cv.getNgayThang() + "'";
                        con.createStatement().execute(sql);
                        it.remove();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        showWorkInfo();

        try {
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (AddNews != null) {
            AddNews.setOnAction(this::hanlderAddNews);
        }

        if (themCv != null) {
            themCv.setOnAction(this::hanlderAddCV);
        }

        try {
            Connection con = ConnectDatabase.connect();
            String sql = "SELECT * FROM congviec";
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                String moTa = rs.getString("moTa");
                String trangThai = rs.getString("trangThai");
                String ngayThang = rs.getString("ngayThang");
                ls.add(new CongViec(moTa, trangThai, ngayThang));
            }

            showWorkInfo();

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        workTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                CongViec cv = (CongViec) workTable.getSelectionModel().getSelectedItem();
                cv.setChecked(cv.getCheck().getValue());
            }
        });

        xoaCv.setOnAction(this::handleDeleteWork);
    }
}
