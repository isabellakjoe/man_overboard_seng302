package seng302.Model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;

/**
 * Created by mgo65 on 3/03/17.
 * An interface for a competitor in a Race
 */
public interface Competitor {
    String getTeamName();
    int getVelocity();
    MutablePoint getPosition();
    Color getColor();
    String getAbbreName();
    double getCurrentHeading();
    DoubleProperty getHeadingProperty();
    void setCurrentHeading(double currentHeading);
    void setVelocity(int velocity);
}
