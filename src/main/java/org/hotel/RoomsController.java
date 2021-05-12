package org.hotel;

import java.io.IOException;
import javafx.fxml.FXML;

public class RoomsController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}