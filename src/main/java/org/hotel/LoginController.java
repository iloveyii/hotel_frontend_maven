package org.hotel;

import java.io.IOException;
import javafx.fxml.FXML;
import org.hotel.models.Controller;
import org.hotel.models.Data;

public class LoginController extends Controller {
    Data data;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("rooms");
    }

}
