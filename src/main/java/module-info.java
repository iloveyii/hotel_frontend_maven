module org.hotel {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.jfoenix;
    requires itextpdf;
    requires java.desktop;
    opens org.hotel to javafx.fxml;
    opens org.hotel.models to javafx.base;
    exports org.hotel;
}