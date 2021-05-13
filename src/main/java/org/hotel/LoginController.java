package org.hotel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.hotel.models.*;
import org.json.JSONObject;

public class LoginController extends Controller {
    public TextField txtEmail;
    public TextField txtPassword;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("rooms");
    }

    @FXML
    private void btnLoginClick() throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if(isEmailValid(email) && password.length() > 0) {
            System.out.println("Email is valid and password > 0");

            User user  =  new User(0, email, password);
            String response = Api.postApiData("logins", user.toJson());
            JSONObject responseObject = Helper.toJsonObject(response);
            System.out.println("btnLoginClick:::" + response);
            System.out.println((boolean)responseObject.get("status") == true);
            if((boolean)responseObject.get("status") == true) {
                App.setRoot("rooms");
            } else {
                System.out.println((boolean)responseObject.get("status") == true);
            }
        } else {
            System.out.println("Email is NOT valid OR password <> 0");
        }
    }

    private boolean isEmailValid(String email) {
        // Validate
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean valid = matcher.matches() ? true :false;

        System.out.println("Email valid : " + valid);

        return valid;
    }

}
