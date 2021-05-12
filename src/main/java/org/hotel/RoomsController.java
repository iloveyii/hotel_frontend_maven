package org.hotel;

import java.io.IOException;
import javafx.fxml.FXML;
import org.hotel.models.Controller;
import org.hotel.models.Data;
import org.hotel.models.DataHolder;

public class RoomsController extends Controller {
    Data data;

    @FXML
    private void switchToPrimary() throws IOException {
        Data data = DataHolder.getInstance().getData();
        System.out.println("Sharing data");
        System.out.println(data);
        App.setRoot("primary");
    }
}