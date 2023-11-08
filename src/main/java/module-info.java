module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;

    exports View.controller;
    opens View.controller to javafx.fxml;
    exports View.database;
    opens View.database to javafx.fxml;
    exports View.data;
    opens View.data to javafx.fxml;
}