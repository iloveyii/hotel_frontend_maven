package org.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hotel.models.Data;
import org.hotel.models.DataHolder;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static Data data;
    public static double x,y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if(fxml.equals("primary")) {
            primaryStage.setWidth(640);
            primaryStage.setHeight(480);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            System.out.println("Primary");
            // scene.setFill(null);
            scene.setRoot(loadFXML(fxml));
            centralizeStageOnScreen(640, 480);
        } else {
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            System.out.println("Other");
            // scene.setFill(null);
            scene.setRoot(loadFXML(fxml));
            centralizeStageOnScreen(1200, 800);
        }

    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
        return root;
    }

    public static void main(String[] args) throws IOException {
        data = new Data();
        DataHolder.getInstance().setData(data);
        System.out.println(data);
        launch();
    }

    private static void centralizeStageOnScreen(int w, int h) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - w) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - h) / 2);
    }

}