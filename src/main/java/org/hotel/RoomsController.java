package org.hotel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.hotel.models.Controller;
import org.hotel.models.Data;
import org.hotel.models.DataHolder;
import org.hotel.models.Room;

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
    private Label mnuBack;
    @FXML
    private Label mnuShow;
    @FXML
    private AnchorPane sdrLeft;

    @FXML
    private void switchToPrimary() throws IOException {
        // Data data = DataHolder.getInstance().getData();
        System.out.println("Sharing data");
        System.out.println(data);
        App.setRoot("primary");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // showTable(); // @TODO uncomment
        btnClose.setOnMouseClicked(event -> {
            System.exit(0);
        });

        sdrLeft.setTranslateX(-400);
        mnuShow.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(sdrLeft);
            slide.setToX(0);
            slide.play();
            sdrLeft.setTranslateX(-400);

            slide.setOnFinished((ActionEvent e)-> {
                mnuShow.setVisible(false);
                mnuBack.setVisible(true);
            });
        });

        mnuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(sdrLeft);
            slide.setToX(0);
            slide.play();
            sdrLeft.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                mnuShow.setVisible(true);
                mnuBack.setVisible(false);
            });
        });
    }

    private void showTable() {
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
}