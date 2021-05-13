package org.hotel;

import java.io.IOException;
import javafx.fxml.FXML;
import org.hotel.models.Controller;

public class LoginController extends Controller {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("rooms");
    }

}
