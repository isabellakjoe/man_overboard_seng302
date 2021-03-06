package parsers.boatLocation;

import models.CourseFeature;
import models.Mark;
import models.MutablePoint;
import java.util.Arrays;
import java.util.List;

import static parsers.Converter.hexByteArrayToInt;
import static utility.Projection.mercatorProjection;

/**
 * Created by psu43 on 13/04/17.
 * parsers for boat location data.
 */
public class BoatDataParser {


    private CourseFeature courseFeature;


    /**
     * Process the given data and parse source id, latitude, longitude, heading, speed
     * @param body byte[] a byte array of the boat data message
     * @return BoatData boat data object
     */
    public BoatData processMessage(byte[] body) {
        try {
            Integer sourceID = hexByteArrayToInt(Arrays.copyOfRange(body, 7, 11));
            int deviceType = hexByteArrayToInt(Arrays.copyOfRange(body, 15, 16));
            double latitude = parseCoordinate(Arrays.copyOfRange(body, 16, 20));
            double longitude = parseCoordinate(Arrays.copyOfRange(body, 20, 24));
            double heading = parseHeading(Arrays.copyOfRange(body, 28, 30));

            //speed in mm/sec
            int speed = hexByteArrayToInt(Arrays.copyOfRange(body, 38, 40));
            //speed in m/sec
            double convertedSpeed = speed / 1000.0;
            MutablePoint mercatorPoint = mercatorProjection(latitude, longitude);
            if (deviceType == 3) {
                MutablePoint GPS = new MutablePoint(latitude, longitude);
                this.courseFeature = new Mark(sourceID.toString(), mercatorPoint, GPS, 0);
            }
            return new BoatData(sourceID, deviceType, latitude, longitude, heading, convertedSpeed, mercatorPoint);
        }
        catch (Exception e) {
            return null;
        }

    }

    /**
     * Convert a byte array of little endian hex values into a decimal heading
     *
     * @param hexValues byte[] a byte array of (2) hexadecimal bytes in little endian format
     * @return Double the value of the heading
     */
    private Double parseHeading(byte[] hexValues) {
        return (double) hexByteArrayToInt(hexValues) * 360.0 / 65536.0;
    }

    /**
     * Convert a byte array of little endian hex values into a decimal latitude or longitude
     *
     * @param hexValues byte[] a byte array of (4) hexadecimal bytes in little endian format
     * @return Double the value of the coordinate value
     */
    private Double parseCoordinate(byte[] hexValues) {
        return (double) hexByteArrayToInt(hexValues) * 180.0 / 2147483648.0;
    }


    public CourseFeature getCourseFeature() {
        return courseFeature;
    }

}
