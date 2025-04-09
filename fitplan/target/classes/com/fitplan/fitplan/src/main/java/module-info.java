module com.fitplan {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.fitplan to javafx.fxml;
    exports com.fitplan;
}
