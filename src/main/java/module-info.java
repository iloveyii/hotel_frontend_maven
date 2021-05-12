module org.hotel {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.hotel to javafx.fxml;
    exports org.hotel;
}