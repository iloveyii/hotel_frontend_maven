package org.hotel;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hotel.models.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DialogController extends Controller implements Initializable  {
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
    private DatePicker calBookingDate;

    @FXML
    private void btnSaveClicked() throws IOException {
        System.out.println("Saving booking");
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        LocalDate datetime = calBookingDate.getValue();

        if(DataHolder.getInstance().getData().currentRoom == null) {
            System.out.println("Please select a room first");
            lblError.setText("Please select a room  first");
            return;
        }

        if(DataHolder.getInstance().getData().currentRoom.isBooked()) {
            System.out.println("This room is already booked, un-book it first.");
            lblError.setText("This room is already booked, un-book it first.");
            return;
        }

        String room_number = DataHolder.getInstance().getData().currentRoom.getNumber();
        Double price = DataHolder.getInstance().getData().currentRoom.getPrice();
        if(! Helper.isEmailValid(email) || name.length() == 0 || phone.length() == 0 || email.length() == 0 || datetime == null) {
            System.out.println("Either email is invalid or name or phone is empty.");
            lblError.setText("Either email is invalid or name/phone/booking date is empty.");
            return;
        }
        lblError.setText("");
        System.out.printf("Creating Booking:: room:%s, price:%.2f, name:%s, phone:%s, email:%s, datetime:%s", room_number, price, name, phone, email, datetime.toString());
        Booking b = new Booking(0, room_number, price, name, phone, email, datetime.toString());
        String response = Api.postApiData("bookings", b.toJson());
        if(Helper.isStatusTrue(response)) {
            lblError.setText("Saved successfully.");
            lblError.setStyle("-fx-background-color: green");
            clearForm();
            // Change room status booked: yes
            DataHolder.getInstance().getData().currentRoom.setBooked("yes");
            String roomBookedStatus = Api.postApiData("rooms", DataHolder.getInstance().getData().currentRoom.toJson());
            if(Helper.isStatusTrue(roomBookedStatus)) {
                System.out.println("Room status changed to booked:yes");
                DataHolder.getInstance().getData().loadRoomsData();
            }
            DataHolder.getInstance().getData().loadBookingsData();
        }
    }

    @FXML
    private void btnCancelClicked() throws IOException {
        System.out.println("Closing dialog");
        clearForm();
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Room r = DataHolder.getInstance().getData().currentRoom;

        if(r == null) {
            lblError.setText("Please select a room first");
        } else {
            if(r.isBooked()) {
                lblError.setText("This room is already booked, un-book it first.");
            }
            lblRoomNumber.setText(r.getNumber());
            lblPrice.setText(String.valueOf(r.getPrice()));
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        calBookingDate.setValue(null);
    }
}
