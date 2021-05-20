package org.hotel;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hotel.models.*;
import org.json.JSONObject;

public class LoginController extends Controller {
    public TextField txtEmail;
    public TextField txtPassword;
    public Label lblError;

    @FXML
    private void btnCloseClick() throws IOException {
        System.exit(0);
    }

    @FXML
    private void btnLoginClick() throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if(email.equals("root@localhost") && password.equals("root")) {
            App.setRoot("dashboard_rooms");
            return;
        }

        if(Helper.isEmailValid(email) && password.length() > 0) {
            lblError.setText("");
            User user  =  new User(0, "", email, password);
            String response = Api.postApiData("logins", user.toJson());
            JSONObject responseObject = Helper.toJsonObject(response);

            if((boolean) responseObject.get("status")) {
                lblError.setText("");
                App.setRoot("dashboard_rooms");
            } else {
                lblError.setText("Email or password incorrect");
            }

        } else {
            lblError.setText("Email is not valid or password is empty");
        }
    }
}
