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
    // Customer Form
    @FXML
    private TextField txtNumber;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtBooked;


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
        String number = txtNumber.getText();
        Double price = Double.valueOf(txtPrice.getText());
        String booked = txtBooked.getText();
        int id = DataHolder.getInstance().getData().currentRoom == null ? 0 : DataHolder.getInstance().getData().currentRoom.getId();
        Room c = new Room(id, number, price, booked);
        System.out.print("Saving customer :::");
        System.out.println(c);
        System.out.println(c.toJson());
        System.out.println(c);
        if( Helper.isStatusTrue(Api.postApiData("rooms", c.toJson())) ){
            clearRoomForm();
            DataHolder.getInstance().getData().loadRoomsData();
            showTableRooms();
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
        mnuShow.setVisible(false);
        sdrLeft.setTranslateX(0);
        mnuShow.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition(); TranslateTransition slideAnchor = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4)); slideAnchor.setDuration(Duration.seconds(0.4));
            slide.setNode(sdrLeft); slideAnchor.setNode(acrTable);
            slide.setToX(0); slideAnchor.setToX(0);
            slide.play(); slideAnchor.play();
            sdrLeft.setTranslateX(-400); acrTable.setTranslateX(400); acrTable.setMinWidth(691); tableRooms.setPrefWidth(600);

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
            sdrLeft.setTranslateX(0); acrTable.setTranslateX(-400); acrTable.setMinWidth(1195); tableRooms.setPrefWidth(800);

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