package org.hotel.models;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static JSONObject toJsonObject(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return jsonObject;
        }catch (JSONException err){
            System.out.println("Error" + err.toString());
        }
        return null;
    }

    public static boolean isStatusTrue(String response) {
        JSONObject respJson = new JSONObject(response);
        return  (boolean)respJson.get("status");
    }

    public static <T> void setSliding(Label mnuShow, Label mnuHide, AnchorPane sdrLeft, AnchorPane acrTable, TableView<T> tableBookings) {
        mnuShow.setVisible(false);
        sdrLeft.setTranslateX(0);
        mnuShow.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition(); TranslateTransition slideAnchor = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4)); slideAnchor.setDuration(Duration.seconds(0.4));
            slide.setNode(sdrLeft); slideAnchor.setNode(acrTable);
            slide.setToX(0); slideAnchor.setToX(0);
            slide.play(); slideAnchor.play();
            sdrLeft.setTranslateX(-400); acrTable.setTranslateX(400); acrTable.setMinWidth(691); tableBookings.setPrefWidth(600);

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
            sdrLeft.setTranslateX(0); acrTable.setTranslateX(-400); acrTable.setMinWidth(1195); tableBookings.setPrefWidth(800);

            slide.setOnFinished((ActionEvent e)-> {
                mnuShow.setVisible(true);
                mnuHide.setVisible(false);
            });
        });
    }

    public static boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        boolean valid = matcher.matches() ? true :false;
        return valid;
    }
}
