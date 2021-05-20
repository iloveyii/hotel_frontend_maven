package org.hotel;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hotel.models.Api;
import org.hotel.models.DataHolder;
import org.hotel.models.Helper;
import org.hotel.models.Room;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomsController extends Controller implements Initializable {

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
    @FXML
    private JFXButton btnBook;
    // Room Form
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtBooked;
    @FXML
    private Label lblError;

    @FXML
    private void btnBookClicked() throws IOException {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        Parent root = App.loadFXML("dialog");
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.showAndWait();
        showTableRooms();
        clearRoomForm();
    }

    @FXML
    private void btnSaveClicked() throws IOException, NoSuchFieldException, IllegalAccessException {
        String number = txtNumber.getText();
        if(txtPrice.getText().length() == 0 || txtPrice.getText().matches("^[0-9.]+$") == false) {
            lblError.setText("PRICE: Either empty or non-numeric");
            return;
        }
        Double price = Double.valueOf(txtPrice.getText());
        String booked = txtBooked.getText();
        int id = DataHolder.getInstance().getData().currentRoom == null ? 0 : DataHolder.getInstance().getData().currentRoom.getId();
        Room r = new Room(id, number, price, booked);
        System.out.print("Saving room :::");
        System.out.println(r.toJson());
        r.validate();
        if(r.hasErrors()){
            lblError.setText(r.getStringErrors());
        } else {
            if( Helper.isStatusTrue(Api.postApiData("rooms", r.toJson())) ){
                clearRoomForm();
                DataHolder.getInstance().getData().loadRoomsData();
                showTableRooms();
            }
        }
    }

    @FXML
    private void btnCancelRoomsClicked() throws IOException {
        clearRoomForm();
    }

    @FXML
    private void btnDeleteRoomsClicked() throws IOException {
        Integer id = DataHolder.getInstance().getData().currentRoom == null ? null : DataHolder.getInstance().getData().currentRoom.getId();
        if( id != null && Helper.isStatusTrue(Api.deleteApiData("rooms", id)) ){
            clearRoomForm();
            DataHolder.getInstance().getData().loadRoomsData();
            showTableRooms();
        }
    }

    private void clearRoomForm() {
        DataHolder.getInstance().getData().currentRoom = null;
        txtNumber.setText("");
        txtPrice.setText("");
        txtBooked.setText("");
        lblError.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableRoomsRowClickListener();
        showTableRooms();

        btnClose.setOnMouseClicked(event -> {
            System.exit(0);
        });
        setSliding();
    }

    private void setSliding() {
        Helper.setSliding(mnuShow, mnuHide, sdrLeft, acrTable, tableRooms);
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

    private void setTableRoomsRowClickListener() {
        tableRooms.setRowFactory(tv -> {
            TableRow<Room> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() ) {
                    Room rowData = row.getItem();
                    System.out.println("Double click on: "+rowData);
                    DataHolder.getInstance().getData().currentRoom = rowData;
                    txtNumber.setText(rowData.getNumber());
                    txtPrice.setText(String.valueOf(rowData.getPrice()));
                    txtBooked.setText(rowData.getBooked());
                }
            });
            return row ;
        });
    }
}