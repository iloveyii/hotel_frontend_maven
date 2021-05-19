package org.hotel;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TextField txtSearch;
    @FXML
    private Label lblError;

    ObservableList<Customer> customers = FXCollections.observableArrayList();

    @FXML
    private void btnSaveClicked() throws IOException, NoSuchFieldException, IllegalAccessException {
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        int id = DataHolder.getInstance().getData().currentCustomer == null ? 0 : DataHolder.getInstance().getData().currentCustomer.getId();
        Customer c = new Customer(id, name, phone, email);
        System.out.print("Saving customer :::");
        System.out.println(c.toJson());
        c.validate();
        if(c.hasErrors()) {
            lblError.setText(c.getStringErrors());
        } else {
            if( Helper.isStatusTrue(Api.postApiData("customers", c.toJson())) ){
                clearCustomerForm();
                DataHolder.getInstance().getData().loadCustomersData();
                showTableCustomers();
            }
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
        setTableCustomersRowClickListener();
        showTableCustomers();

        btnClose.setOnMouseClicked(event -> {
            System.exit(0);
        });
        setSliding();
        setFiltering();
    }

    public void setFiltering() {
        // 1 :- Observable list to filteredList 
        FilteredList<Customer> filteredList = new FilteredList<>(customers, c -> true);
        // 2 :- Set filter predicate whenever the filter changes
        txtSearch.textProperty().addListener((observable, oldValue, newValue)->{
            filteredList.setPredicate(customer -> {
                // Empty display all
                if(newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (customer.getName().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else if(customer.getEmail().toLowerCase().contains(newValue.toLowerCase())) {
                    return true;
                } else if(customer.getPhone().contains(newValue)){
                    return true;
                }
                return false;
            });
            SortedList<Customer> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().setValue(tableCustomers.getComparator());
            tableCustomers.setItems(sortedList);
        });
    }

    private void setSliding() {
        Helper.setSliding(mnuShow, mnuHide, sdrLeft, acrTable, tableCustomers);
    }

    private void showTableCustomers() {
        System.out.println("RoomsController showTable Customers");
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