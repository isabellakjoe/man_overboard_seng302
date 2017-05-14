package controllers;

import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import model.Race;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import utilities.DataReceiver;

/**
 * Created by psu43 on 22/03/17.
 * The parent controller for the view
 */
public class MainController {


    private Race race;

    @FXML
    private TableController tableController;

    @FXML
    private RaceViewController raceViewController;

    @FXML
    private SplitPane splitPane;

    @FXML
    private SparklinesController sparklinesController;


    /**
     * Sets the race
     */
    public void setRace(DataReceiver dataReceiver, double width, double height, int numBoats) {
        raceViewController.setTableController(tableController);
        raceViewController.begin(width, height, dataReceiver);


    }
}
