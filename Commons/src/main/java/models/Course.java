package models;

import java.util.List;

/**
 * Created by psu43 on 15/03/17.
 * Interface for a course in a Race
 */
public interface Course {
    List<CourseFeature> getPoints();
    double distanceBetweenGPSPoints(MutablePoint start, MutablePoint end);
}
