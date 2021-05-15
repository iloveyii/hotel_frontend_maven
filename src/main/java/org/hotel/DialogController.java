package org.hotel;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hotel.models.Controller;
import org.hotel.models.DataHolder;
import org.hotel.models.Helper;
import org.hotel.models.Room;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController extends Controller implements Initializable  {
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnCancel;

    // Form
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblError;
    @FXML
    private Label lblRoomNumber;
    @FXML
    private Label lblPrice;

    @FXML
    private void btnSaveClicked() throws IOException {
        System.out.println("Saving booking");
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        if(DataHolder.getInstance().getData().currentRoom == null) {
            System.out.println("Please select a room first");
            lblError.setText("Please select a room  first");
            return;
        }
        String room_number = DataHolder.getInstance().getData().currentRoom.getNumber();
        Double price = DataHolder.getInstance().getData().currentRoom.getPrice();
        if(! Helper.isEmailValid(email) || name.length() == 0 || phone.length() == 0 || email.length() == 0) {
            System.out.println("Either email is invalid or name or phone is empty.");
            lblError.setText("Either email is invalid or name or phone is empty.");
            return;
        }
        lblError.setText("");
        System.out.printf("Creating Booking:: room:%s, price:%.2f, name:%s, phone:%s, email:%s", room_number, price, name, phone, email);
    }

    @FXML
    private void btnCancelClicked() throws IOException {
        System.out.println("Closing dialog");
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Room r = DataHolder.getInstance().getData().currentRoom;

        if(r != null) {
            System.out.println("Please select a room first");
            lblRoomNumber.setText(r.getNumber());
            lblPrice.setText(String.valueOf(r.getPrice()));
        }
    }
}
