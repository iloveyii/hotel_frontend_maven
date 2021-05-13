package org.hotel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private void btnLoginClick() throws IOException {
        App.setRoot("dashboard"); // @TODO remove
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if(isEmailValid(email) && password.length() > 0) {
            lblError.setText("");
            User user  =  new User(0, email, password);
            String response = Api.postApiData("logins", user.toJson());
            JSONObject responseObject = Helper.toJsonObject(response);

            if((boolean) responseObject.get("status")) {
                lblError.setText("");
                App.setRoot("rooms");
            } else {
                lblError.setText("Email or password incorrect");
            }

        } else {
            lblError.setText("Email is not valid or password is empty");
        }
    }

    private boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean valid = matcher.matches() ? true :false;
        return valid;
    }

}
