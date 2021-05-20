package org.hotel;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.hotel.models.*;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class UsersController extends Controller implements Initializable {

    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, Integer> colId;
    @FXML
    private TableColumn<User, String> colEmail;
    @FXML
    private TableColumn<User, String> colName;
    @FXML
    private TableColumn<User, String> colPassword;

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
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtName;
    @FXML
    private Label lblError;
    

    @FXML
    private void btnSaveClicked() throws IOException, NoSuchFieldException, IllegalAccessException {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        int id = DataHolder.getInstance().getData().currentUser == null ? 0 : DataHolder.getInstance().getData().currentUser.getId();
        User u = new User(id, name, email, password);
        u.validate();
        if(u.hasErrors()) {
            lblError.setText(u.getStringErrors());
        } else {
            System.out.println("User has no errors");
            if( Helper.isStatusTrue(Api.postApiData("users", u.toJson())) ){
                clearUserForm();
                DataHolder.getInstance().getData().loadUsersData();
                showTableUsers();
            }
        }
    }

    @FXML
    private void btnCancelUsersClicked() throws IOException {
        clearUserForm();
    }

    @FXML
    private void btnDeleteUsersClicked() throws IOException {
        Integer id = DataHolder.getInstance().getData().currentUser == null ? null : DataHolder.getInstance().getData().currentUser.getId();
        if( id != null && Helper.isStatusTrue(Api.deleteApiData("users", id)) ){
            clearUserForm();
            DataHolder.getInstance().getData().loadUsersData();
            showTableUsers();
        }
    }

    private void clearUserForm() {
        DataHolder.getInstance().getData().currentUser = null;
        txtName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableUsersRowClickListener();
        showTableUsers();

        btnClose.setOnMouseClicked(event -> {
            System.exit(0);
        });
        setSliding();
    }

    private void setSliding() {
        Helper.setSliding(mnuShow, mnuHide, sdrLeft, acrTable, tableUsers);
    }

    private void showTableUsers() {
        System.out.println("UsersController showTable");
        ObservableList<User> users = FXCollections.observableArrayList();
        colId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<User, String>("password"));

        for(int i=0; i < data.users.size(); i++) {
            User u = data.users.get(i);
            System.out.println("Users:::" + u);
            u.setPassword("******");
            users.add(u);
        }
        tableUsers.setItems(users);
    }

    private void setTableUsersRowClickListener() {
        tableUsers.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() ) {
                    User rowData = row.getItem();
                    System.out.println("Double click on: "+rowData);
                    DataHolder.getInstance().getData().currentUser = rowData;
                    txtName.setText(rowData.getName());
                    txtEmail.setText(rowData.getEmail());
                    txtPassword.setText(rowData.getPassword());
                }
            });
            return row ;
        });
    }
}