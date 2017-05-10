package controllers;

import models.Clock;
import models.ClockHandler;
import models.Competitor;
import models.WorldClock;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import utilities.DataReceiver;
import utilities.EnvironmentConfig;
import utilities.Interpreter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

/**
 * Created by rjc249 on 5/04/17.
 * Controller for the start scene.
 */
public class StarterController implements Initializable, ClockHandler {


    private final int STARTTIME = 0;
    @FXML
    private ListView<Competitor> starterList;
    @FXML
    private Label worldClockValue;
    @FXML
    private Button countdownButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label raceStatus;
    @FXML
    private ComboBox<String> streamCombo;
    private Clock worldClock;
    private Stage primaryStage;
    private ObservableList<Competitor> compList;
    private Rectangle2D primaryScreenBounds;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);
    private DataReceiver dataReceiver;
    private Interpreter interpreter;

    /**
     * Sets the stage
     *
     * @param primaryStage Stage the stage for this window
     */
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Implementation of ClockHandler interface method
     *
     * @param newTime The currentTime of the clock
     */
    public void clockTicked(String newTime, Clock clock) {
        if (clock == worldClock) {
            worldClockValue.setText(newTime);
        }
    }

    /**
     * Initialiser for StarterController
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.countdownButton.setDisable(true);
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        compList = FXCollections.observableArrayList();

        starterList.setCellFactory(new Callback<ListView<Competitor>, ListCell<Competitor>>() {
            @Override
            public ListCell<Competitor> call(ListView<Competitor> param) {
                ListCell<Competitor> cell = new ListCell<Competitor>() {

                    @Override
                    protected void updateItem(Competitor boat, boolean empty) {
                        super.updateItem(boat, empty);
                        if (boat != null) {
                            setText(boat.getTeamName() + ": " + boat.getVelocity() + " m/s");
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        starterList.setItems(compList);

        streamCombo.getItems().addAll(EnvironmentConfig.liveStream, EnvironmentConfig.csseStream, EnvironmentConfig.mockStream);


    }

    /**
     * Switches from start view to course view. Called when user clicks start button.
     * Starts countdown if the list of starters is not empty.
     */
    @FXML
    void switchToCourseView() {
        // check that starter table is not empty
        if (!starterList.getItems().isEmpty()) {
            startCountdown();
            countdownButton.setDisable(true);

        } else {
            // inform user to press confirm
            Stage thisStage = (Stage) countdownButton.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.initOwner(thisStage);
            alert.setHeaderText(null);
            alert.setContentText("Please make sure there are competitors.");
            alert.showAndWait();
        }

    }

    /**
     * Countdown until the race start, updates the countdown time text.
     */
    private void startCountdown() {

        //count down for 5 seconds
        timeSeconds.set(STARTTIME);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(STARTTIME + 1),
                        new KeyValue(timeSeconds, 0)));
        timeline.play();

        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            //after 5 seconds, load race course view
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int numBoats = interpreter.getNumBoats();
                MainController mainController = loader.getController();
                mainController.setRace(interpreter, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight(), numBoats);
                primaryStage.setTitle("RaceVision");
                primaryStage.setWidth(primaryScreenBounds.getWidth());
                primaryStage.setHeight(primaryScreenBounds.getHeight());
                primaryStage.setMinHeight(primaryScreenBounds.getHeight());
                primaryStage.setMinWidth(primaryScreenBounds.getWidth());
                primaryStage.setX((primaryScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
                primaryStage.setY((primaryScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
                primaryStage.setScene(new Scene(root, primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight()));


            }
        });
    }

    /**
     * Set fields using data from the stream
     */
    private void setFields() {


        while (interpreter.getCourseTimezone() == null) {
            System.out.print("");
        }

        this.worldClock = new WorldClock(this, interpreter.getCourseTimezone());
        worldClock.start();

        compList.setAll(interpreter.getCompetitors());
        raceStatus.setText(interpreter.getRaceStatus());

        System.out.println(interpreter.getRaceStatus());

        if (interpreter.getCompetitors().size() == 0) {
            Stage thisStage = (Stage) countdownButton.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.initOwner(thisStage);
            alert.setHeaderText(null);
            alert.setContentText("Sorry, this data stream hasn't started.");
            alert.showAndWait();
        }
        this.countdownButton.setDisable(false);
    }


    /**
     * Called when the user clicks confirm.
     * Begins streaming data from the selected server
     * Calls setFields when data is received
     */
    @FXML
    public void confirmStream() {

        if (this.dataReceiver == null) {

            //get the selected stream
            String host = this.streamCombo.getSelectionModel().getSelectedItem();
            if (host == null || host.equals("")) {
                System.out.println("No stream selected");
                return;
            }

            //create a data reciever
            try {
                dataReceiver = new DataReceiver(host, EnvironmentConfig.port);
                interpreter = dataReceiver.getInterpreter();
                dataReceiver.setCanvasDimensions(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
                this.streamCombo.setDisable(true);
                this.confirmButton.setDisable(true);

            } catch (IOException e) {
                System.out.println("Could not connect to: " + host + ":" + EnvironmentConfig.port);
                dataReceiver = null;
                return;
            }

            //start receiving data
            Timer receiverTimer = new Timer();
            receiverTimer.schedule(dataReceiver, 0, 1);

            try {
                //wait for data to come in before setting fields
                while (interpreter.getNumBoats() < 1 || interpreter.getCompetitors().size() < interpreter.getNumBoats()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        System.out.println("Thread sleep error");
                    }
                }

            }
            catch(NullPointerException e) {
                System.out.println("Live stream is down");
            }


            this.setFields();

        }
    }

}