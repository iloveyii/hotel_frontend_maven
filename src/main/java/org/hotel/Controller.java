package org.hotel;

import javafx.fxml.FXML;
import org.hotel.App;
import org.hotel.models.Data;
import org.hotel.models.DataHolder;

import java.io.IOException;

public class Controller {
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    private void switchToDashboardBookings() throws IOException {
        App.setRoot("dashboard_bookings");
    }
    @FXML
    private void switchToDashboardCustomers() throws IOException {
        App.setRoot("dashboard_customers");
    }
    @FXML
    private void switchToDashboardRooms() throws IOException {
        App.setRoot("dashboard_rooms");
    }
    @FXML
    private void switchToDashboardUsers() throws IOException {
        App.setRoot("dashboard_users");
    }

    public Data data = DataHolder.getInstance().getData();
}
