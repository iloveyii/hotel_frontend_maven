package org.hotel;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hotel.models.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DialogController extends Controller implements Initializable  {
    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private void btnSaveClicked() throws IOException {
        System.out.println("Saving booking");
    }

    @FXML
    private void btnCancelClicked() throws IOException {
        System.out.println("Closing dialog");
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
