package manage.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import manage.database.ConnectDatabase;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LichHocController implements Initializable {
    @FXML
    private GridPane bang_TKB;

    @FXML
    private VBox cell1_2;
    @FXML
    private VBox cell1_3;
    @FXML
    private VBox cell1_4;
    @FXML
    private VBox cell1_5;
    @FXML
    private VBox cell1_6;
    @FXML
    private VBox cell1_7;

    @FXML
    private VBox cell2_2;
    @FXML
    private VBox cell2_3;
    @FXML
    private VBox cell2_4;
    @FXML
    private VBox cell2_5;
    @FXML
    private VBox cell2_6;
    @FXML
    private VBox cell2_7;
    @FXML
    private VBox cell3_2;
    @FXML
    private VBox cell3_3;
    @FXML
    private VBox cell3_4;
    @FXML
    private VBox cell3_5;
    @FXML
    private VBox cell3_6;
    @FXML
    private VBox cell3_7;
    @FXML
    private VBox cell4_2;
    @FXML
    private VBox cell4_3;
    @FXML
    private VBox cell4_4;
    @FXML
    private VBox cell4_5;
    @FXML
    private VBox cell4_6;
    @FXML
    private VBox cell4_7;
    @FXML
    private VBox cell5_2;
    @FXML
    private VBox cell5_3;
    @FXML
    private VBox cell5_4;
    @FXML
    private VBox cell5_5;
    @FXML
    private VBox cell5_6;
    @FXML
    private VBox cell5_7;
    @FXML
    private VBox cell6_2;
    @FXML
    private VBox cell6_3;
    @FXML
    private VBox cell6_4;
    @FXML
    private VBox cell6_5;
    @FXML
    private VBox cell6_6;
    @FXML
    private VBox cell6_7;
    private static String maSv;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String maSv = StudentInforController.getMSV();

        if (maSv != null) {
//            maSv = maSv.getMaSv();
            System.out.println(maSv);
        } else {
            System.out.println("nullllll");
        }
//        String maSv = "B21DCCN001";
        String sql = "SELECT l.id_sv, c.*, m.ten_mh FROM monhoc m, mh_lich l, course c " +
                "WHERE (l.id_sv = '" + maSv + "') AND (m.id_mh = c.id_mh) AND (c.id_course = l.id_course);";

        Connection con = ConnectDatabase.connect();
        try {
            Statement state = con.createStatement();
            ResultSet resultSet = state.executeQuery(sql);

            while (resultSet.next()) {
                String gvien = resultSet.getString("ten_gv");
                String mhoc = resultSet.getString("ten_mh");
                Integer sotiet = Integer.parseInt(resultSet.getString("sotiet"));
                Integer kip = Integer.parseInt(resultSet.getString("kip"));
                Integer thu = Integer.parseInt(resultSet.getString("thu"));
                System.out.println("kip: " + kip + " thu: " + thu + " sotiet : " + sotiet);

                Label gvienLabel = CellofTKB(gvien);
                Label mhocLabel = CellofTKB(mhoc);
                Label gvienLabel1 = CellofTKB(gvien);
                Label mhocLabel1 = CellofTKB(mhoc);
                if (sotiet == 2) {
                    them(kip, thu, gvienLabel, mhocLabel);
                } else if (sotiet == 4) {
                    them(kip, thu, gvienLabel, mhocLabel);
                    them(kip + 1, thu, gvienLabel1, mhocLabel1);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error when get the Schedule of a student");
            System.out.println(e.getMessage());
        }
    }

    public Label CellofTKB(String s) {
        Label label = new Label(s);
        label.setPrefHeight(70);
        label.setPrefWidth(100);
        label.setStyle("-fx-background-color: #73c78a;" +
                " -fx-font-size: 10;");

        return label;
    }

    public void them(Integer kip, Integer thu, Label gvienLabel, Label mhocLabel) {
        if (kip == 1 && thu == 2) {
            cell1_2.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 1 && thu == 3) {
            cell1_3.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 1 && thu == 4) {
            cell1_4.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 1 && thu == 5) {
            cell1_5.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 1 && thu == 6) {
            cell1_6.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 1 && thu == 7) {
            cell1_7.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 2 && thu == 2) {
            cell2_2.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 2 && thu == 3) {
            cell2_3.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 2 && thu == 4) {
            cell2_4.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 2 && thu == 5) {
            cell2_5.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 2 && thu == 6) {
            cell2_6.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 2 && thu == 7) {
            cell2_7.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 3 && thu == 2) {
            cell3_2.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 3 && thu == 3) {
            cell3_3.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 3 && thu == 4) {
            cell3_4.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 3 && thu == 5) {
            cell3_5.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 3 && thu == 6) {
            cell3_6.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 3 && thu == 7) {
            cell3_7.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 4 && thu == 2) {
            cell4_2.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 4 && thu == 3) {
            cell4_3.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 4 && thu == 4) {
            cell4_4.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 4 && thu == 5) {
            cell4_5.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 4 && thu == 6) {
            cell4_6.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 4 && thu == 7) {
            cell4_7.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 5 && thu == 2) {
            cell5_2.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 5 && thu == 3) {
            cell5_3.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 5 && thu == 4) {
            cell5_4.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 5 && thu == 5) {
            cell5_5.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 5 && thu == 6) {
            cell5_6.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 5 && thu == 7) {
            cell5_7.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 6 && thu == 2) {
            cell6_2.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 6 && thu == 3) {
            cell6_3.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 6 && thu == 4) {
            cell6_4.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 6 && thu == 5) {
            cell6_5.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 6 && thu == 6) {
            cell6_6.getChildren().addAll(gvienLabel, mhocLabel);
        } else if (kip == 6 && thu == 7) {
            cell6_7.getChildren().addAll(gvienLabel, mhocLabel);
        }
    }
}