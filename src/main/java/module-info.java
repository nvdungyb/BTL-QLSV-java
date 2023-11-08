module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;

    opens Main to javafx.fxml;
    exports Main;
}