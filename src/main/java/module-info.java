module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires poi;
    requires poi.ooxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;

    exports manage.controller;
    opens manage.controller to javafx.fxml;
    exports manage.database;
    opens manage.database to javafx.fxml;
    exports manage.data;
    opens manage.data to javafx.fxml;
}