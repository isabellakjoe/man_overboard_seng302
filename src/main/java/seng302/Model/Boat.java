package seng302.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;

/**
 * Created by mgo65 on 3/03/17.
 * Boat object
 */
public class Boat implements Competitor {
    private String teamName;
    private int velocity;
    private MutablePoint position;
    private Color color;
    private String abbreName;
    private DoubleProperty currentHeading = new SimpleDoubleProperty();



    /**
     * Creates a boat
     * @param teamName String the team name of the boat
     * @param velocity int the velocity of the boat in m/s
     * @param startPosition MutablePoint the boat's start position coordinate
     * @param color Color the boat colour
     * @param abbreName String the abbreviated name of the boat
     */
    public Boat(String teamName, int velocity, MutablePoint startPosition, Color color, String abbreName) {
        this.velocity = velocity;
        this.teamName = teamName;
        this.position = startPosition;
        this.color = color;
        this.abbreName=abbreName;
    }

    /**
     * Getter for the boat's team name
     * @return String The name of the boat team
     */
    public String getTeamName() {
        return this.teamName;
    }

    /**
     * Setter for the boat's velocity
     * @param velocity boat's velocity m/s
     */
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }


    /**
     * Getter for the boats velocity
     * @return int the velocity in m/s
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * Getter for the boat's position
     * @return MutablePoint the coordinate of the boat
     */
    public MutablePoint getPosition() {
        return this.position;
    }


    /**
     * Getter for the abbreviated team name
     * @return String the abbreviated team name
     */
    @Override
    public String getAbbreName() {
        return abbreName;
    }

    /**
     * Getter for the team color
     * @return Color the team color
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Setter for the current heading
     * @param currentHeading double the angle of the heading
     */
    public void setCurrentHeading(double currentHeading) {
        this.currentHeading.setValue(currentHeading);
    }

    /**
     * Getter for the current heading
     * @return double the current heading
     */
    public double getCurrentHeading() {
        return currentHeading.getValue();
    }

    /**
     * Getter for the double property of the heading
     * @return DoubleProperty the heading property
     */
    public DoubleProperty getHeadingProperty() {
        return this.currentHeading;
    }
}