module org.hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    opens org.hotel to javafx.fxml;
    exports org.hotel;
}