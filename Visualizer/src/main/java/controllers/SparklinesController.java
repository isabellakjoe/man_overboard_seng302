package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Competitor;
import model.RaceEvent;
import utilities.DataReceiver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by msl47 on 21/03/17.
 * The controller for the table
 */

public class SparklinesController implements Initializable {

    @FXML
    private LineChart sparklinesChart;

    private DataReceiver dataReceiver;



    /**
     * Initialiser for the SparklinesController
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        // initialise race table
        //sparklinesChart.

    }




}