package controllers;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Code for starter controller


        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("startView.fxml"));
        Parent root = loader.load();
        StarterController starterController = loader.getController();
//        starterController.setCourseFile(courseFile);
        starterController.setStage(primaryStage);
        primaryStage.setMinWidth(530);
        primaryStage.setMinWidth(548);
        primaryStage.setScene(new Scene(root));

        //set on close requests
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();


    }


}