package models;

import java.util.List;

/**
 * Created by mgo65 on 4/03/17.
 * An interface for a feature in a Course
 */
public interface CourseFeature {
    String getName();

    List<MutablePoint> getPixelLocations();

    double getExitHeading();

    void setExitHeading(Double exitHeading);

    boolean isFinish();

    int getIndex();

    MutablePoint getGPSCentre();

    MutablePoint getGPSPoint();

    void factor(double xFactor, double yFactor, double minX, double minY, double xBuffer, double yBuffer);

    int getRounding();

    void setRounding(int rounding);

    int getZoneSize();

    void setZoneSize(String zoneSize);

    boolean isLine();
}