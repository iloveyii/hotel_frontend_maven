module org.hotel {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    opens org.hotel to javafx.fxml;
    opens org.hotel.models to javafx.base;
    exports org.hotel;
}