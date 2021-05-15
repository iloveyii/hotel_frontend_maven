package org.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.hotel.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BookingsController extends Controller implements Initializable {

    @FXML
    private TableView<Booking> tableBookings;
    @FXML
    private TableColumn<Booking, Integer> colId;
    @FXML
    private TableColumn<Booking, String> colRoomNumber;
    @FXML
    private TableColumn<Booking, Double> colPrice;
    @FXML
    private TableColumn<Booking, String> colCustomerName;
    @FXML
    private TableColumn<Booking, String> colCustomerPhone;
    @FXML
    private TableColumn<Booking, String> colCustomerEmail;
    @FXML
    private TableColumn<Booking, String> colDatetime;

    // Dashboard
    @FXML
    private ImageView btnClose;
    @FXML
    private Label mnuHide;
    @FXML
    private Label mnuShow;
    @FXML
    private AnchorPane sdrLeft;
    @FXML
    private AnchorPane acrTable;
    // Booking Form
    @FXML
    private TextField txtRoomNumber;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private TextField txtCustomerPhone;
    @FXML
    private TextField txtCustomerEmail;
    @FXML
    private TextField txtDatetime;

    @FXML
    private void btnSaveClicked() throws IOException {
        String room_number = txtRoomNumber.getText();
        Double price = Double.valueOf(txtPrice.getText());
        String customer_name = txtCustomerName.getText();
        String customer_email = txtCustomerEmail.getText();
        String customer_phone = txtCustomerPhone.getText();
        String datetime = txtDatetime.getText();
        int id = DataHolder.getInstance().getData().currentBooking == null ? 0 : DataHolder.getInstance().getData().currentBooking.getId();
        Booking b = new Booking(id, room_number, price, customer_name, customer_phone, customer_email, datetime);

        System.out.print("Saving booking :::");
        System.out.println(b.toJson());

        if( Helper.isStatusTrue(Api.postApiData("bookings", b.toJson())) ){
            clearBookingForm();
            DataHolder.getInstance().getData().loadBookingsData();
            showTableBookings();
        }
    }

    @FXML
    private void btnCancelBookingsClicked() throws IOException {
        clearBookingForm();
    }

    @FXML
    private void btnDeleteBookingsClicked() throws IOException {
        Integer id = DataHolder.getInstance().getData().currentBooking == null ? null : DataHolder.getInstance().getData().currentBooking.getId();
        if( id != null && Helper.isStatusTrue(Api.deleteApiData("bookings", id)) ){
            clearBookingForm();
            DataHolder.getInstance().getData().loadBookingsData();
            showTableBookings();
        }
    }

    private void clearBookingForm() {
        DataHolder.getInstance().getData().currentBooking = null;
        txtRoomNumber.setText("");
        txtPrice.setText("");
        txtCustomerName.setText("");
        txtCustomerPhone.setText("");
        txtCustomerEmail.setText("");
        txtDatetime.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableBookingsRowClickListener();
        showTableBookings();

        btnClose.setOnMouseClicked(event -> {
            System.exit(0);
        });
        setSliding();
    }

    private void setSliding() {
        Helper.setSliding(mnuShow, mnuHide, sdrLeft, acrTable, tableBookings);
    }

    private void showTableBookings() {
        System.out.println("BookingsController showTable");
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<Booking, Integer>("id"));
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<Booking, String>("room_number"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Booking, Double>("price"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<Booking, String>("name"));
        colCustomerEmail.setCellValueFactory(new PropertyValueFactory<Booking, String>("email"));
        colCustomerPhone.setCellValueFactory(new PropertyValueFactory<Booking, String>("phone"));
        colDatetime.setCellValueFactory(new PropertyValueFactory<Booking, String>("datetime"));

        for(int i=0; i < data.bookings.size(); i++) {
            System.out.println("Bookings:::" + data.bookings.get(i));
            bookings.add(data.bookings.get(i));
        }
        tableBookings.setItems(bookings);
    }

    private void setTableBookingsRowClickListener() {
        tableBookings.setRowFactory(tv -> {
            TableRow<Booking> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() ) {
                    Booking rowData = row.getItem();
                    System.out.println("Double click on: "+rowData);
                    DataHolder.getInstance().getData().currentBooking = rowData;
                    txtRoomNumber.setText(rowData.getRoom_number());
                    txtPrice.setText(String.valueOf(rowData.getPrice()));
                    txtCustomerName.setText(rowData.getName());
                    txtCustomerPhone.setText(rowData.getPhone());
                    txtCustomerEmail.setText(rowData.getEmail());
                    txtDatetime.setText(rowData.getDatetime());
                }
            });
            return row ;
        });
    }
}