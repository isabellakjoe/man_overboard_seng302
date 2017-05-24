package utilities;

import javafx.scene.Scene;
import models.Competitor;
import models.CourseFeature;
import models.MutablePoint;
import parsers.RaceStatusEnum;
import parsers.markRounding.MarkRoundingData;
import parsers.xml.race.CompoundMarkData;
import parsers.xml.race.MarkData;

import java.util.HashMap;
import java.util.List;

/**
 * Created by mgo65 on 11/05/17.
 * Data source
 */
public interface DataSource {
    boolean receive(String host, int port, Scene scene);
    List<CourseFeature> getCourseFeatures();
    List<MutablePoint> getCourseBoundary();
    String getCourseTimezone();
    List<Integer> getStartMarks();
    List<Integer> getFinishMarks();
    RaceStatusEnum getRaceStatus();
    long getMessageTime();
    long getExpectedStartTime();
    List<Competitor> getCompetitorsPosition();
    double getWindDirection();
    int getNumBoats();
    double getWindSpeed();
    HashMap<Integer, CourseFeature> getStoredFeatures();
    double getCentralLongitude();
    double getCentralLatitude();
    List<Double> getGPSbounds();
    List<MutablePoint> getcourseGPSBoundary();
    int getMapZoomLevel();
    double getShiftDistance();
}
