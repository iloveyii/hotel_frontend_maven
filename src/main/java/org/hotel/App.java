package org.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        primaryStage = stage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        if(fxml.equals("primary")) {
            primaryStage.setWidth(640);
            primaryStage.setHeight(480);
            primaryStage.setMaximized(false);
            primaryStage.setResizable(false);
            System.out.println("Primary");
        } else {
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.setMaximized(true);
            primaryStage.setResizable(true);
            System.out.println("Other");
        }
        // scene.setFill(null);
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        data = new Data();
        DataHolder.getInstance().setData(data);
        System.out.println(data);
        launch();
    }

}