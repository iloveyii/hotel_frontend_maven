package org.hotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TableRow;
import javafx.util.Duration;
import org.hotel.models.*;

public class CustomersController extends Controller implements Initializable {

    @FXML
    private TableView<Room> tableRooms;
    @FXML
    private TableColumn<Room, Integer> colId;
    @FXML
    private TableColumn<Room, String> colNumber;
    @FXML
    private TableColumn<Room, Double> colPrice;
    @FXML
    private TableColumn<Room, String> colBooked;

    // Table Customers
    @FXML
    private TableView<Customer> tableCustomers;
    @FXML
    private TableColumn<Customer, Integer> colIdCustomers;
    @FXML
    private TableColumn<Customer, String> colNameCustomers;
    @FXML
    private TableColumn<Customer, String> colPhoneCustomers;
    @FXML
    private TableColumn<Customer, String> colEmailCustomers;

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
    // Customer Form
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;


    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    @FXML
    private void switchToDashboardUsers() throws IOException {
        App.setRoot("dashboard_users");
    }
    @FXML
    private void switchToDashboardCustomers() throws IOException {
        App.setRoot("dashboard_customers");
    }
    @FXML
    private void switchToDashboardRooms() throws IOException {
        App.setRoot("dashboard_rooms");
    }
    @FXML
    private void switchToDashboardBookings() throws IOException {
        App.setRoot("dashboard_bookings");
    }

    @FXML
    private void btnSaveClicked() throws IOException {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        int id = DataHolder.getInstance().getData().currentCustomer == null ? 0 : DataHolder.getInstance().getData().currentCustomer.getId();
        Customer c = new Customer(id, phone, email);
        System.out.print("Saving customer :::");
        System.out.println(c);
        System.out.println(c.toJson());
        System.out.println(c);
        if( Helper.isStatusTrue(Api.postApiData("customers", c.toJson())) ){
            clearCustomerForm();
            DataHolder.getInstance().getData().loadCustomersData();
            showTableCustomers();
        }
    }

    @FXML
    private void btnCancelCustomersClicked() throws IOException {
        clearCustomerForm();
    }

    @FXML
    private void btnDeleteCustomersClicked() throws IOException {
        Integer id = DataHolder.getInstance().getData().currentCustomer == null ? null : DataHolder.getInstance().getData().currentCustomer.getId();
        if( id != null && Helper.isStatusTrue(Api.deleteApiData("customers", id)) ){
            clearCustomerForm();
            DataHolder.getInstance().getData().loadCustomersData();
            showTableCustomers();
        }
    }

    private void clearCustomerForm() {
        DataHolder.getInstance().getData().currentCustomer = null;
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // showTable(); // @TODO uncomment
        setTableCustomersRowClickListener();
        showTableCustomers();

        btnClose.setOnMouseClicked(event -> {
            System.exit(0);
        });
        setSliding();
    }

    private void setSliding() {
        mnuShow.setVisible(false);
        sdrLeft.setTranslateX(0);
        mnuShow.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition(); TranslateTransition slideAnchor = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4)); slideAnchor.setDuration(Duration.seconds(0.4));
            slide.setNode(sdrLeft); slideAnchor.setNode(acrTable);
            slide.setToX(0); slideAnchor.setToX(0);
            slide.play(); slideAnchor.play();
            sdrLeft.setTranslateX(-400); acrTable.setTranslateX(400); acrTable.setMinWidth(691); tableCustomers.setPrefWidth(600);

            slide.setOnFinished((ActionEvent e)-> {
                mnuShow.setVisible(false);
                mnuHide.setVisible(true);
            });
        });

        mnuHide.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();  TranslateTransition slideAnchor = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4)); slideAnchor.setDuration(Duration.seconds(0.4));
            slide.setNode(sdrLeft); slideAnchor.setNode(acrTable);
            slide.setToX(-400);  slideAnchor.setToX(-400);
            slide.play(); slideAnchor.play();
            sdrLeft.setTranslateX(0); acrTable.setTranslateX(-400); acrTable.setMinWidth(1195); tableCustomers.setPrefWidth(800);

            slide.setOnFinished((ActionEvent e)-> {
                mnuShow.setVisible(true);
                mnuHide.setVisible(false);
            });
        });
    }

    private void showTableRooms() {
        System.out.println("RoomsController showTable");
        ObservableList<Room> rooms = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<Room, Integer>("id"));
        colNumber.setCellValueFactory(new PropertyValueFactory<Room, String>("number"));
        colPrice.setCellValueFactory(new PropertyValueFactory<Room, Double>("price"));
        colBooked.setCellValueFactory(new PropertyValueFactory<Room, String>("booked"));

        for(int i=0; i < data.rooms.size(); i++) {
            System.out.println("Rooms:::" + data.rooms.get(i));
            rooms.add(data.rooms.get(i));
        }
        tableRooms.setItems(rooms);
    }

    private void showTableCustomers() {
        System.out.println("RoomsController showTable Customers");
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        colIdCustomers.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        colNameCustomers.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        colPhoneCustomers.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        colEmailCustomers.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));

        for(int i=0; i < data.customers.size(); i++) {
            System.out.println("Customers:::" + data.customers.get(i));
            customers.add(data.customers.get(i));
        }
        tableCustomers.setItems(customers);
    }

    private void setTableCustomersRowClickListener() {
        tableCustomers.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() ) {
                    Customer rowData = row.getItem();
                    System.out.println("Double click on: "+rowData);
                    DataHolder.getInstance().getData().currentCustomer = rowData;
                    txtName.setText(rowData.getName());
                    txtPhone.setText(rowData.getPhone());
                    txtEmail.setText(rowData.getEmail());
                }
            });
            return row ;
        });
    }
}