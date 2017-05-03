package seng302.Parsers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static seng302.Parsers.Converter.hexByteArrayToInt;

/**
 * Created by Pang on 3/05/17.
 * Parse for race status message
 */
public class RaceStatusParser {
    RaceStatusData raceStatus;

    public RaceStatusParser(byte[] message) {
        this.raceStatus = processMessage(message);
    }

    /**
     * Parse the race status message
     * @param body byte[] a byte array of the message that needs parsing
     * @return RaceStatusData the data from the race status message
     */
    private RaceStatusData processMessage(byte[] body) {
        Integer currentTime = hexByteArrayToInt(Arrays.copyOfRange(body, 1,7));
        Integer raceID = hexByteArrayToInt(Arrays.copyOfRange(body, 7,11));
        Integer raceStatus = hexByteArrayToInt(Arrays.copyOfRange(body, 11,12));
        Integer expectedStartTime = hexByteArrayToInt(Arrays.copyOfRange(body, 12,18));
        Integer windDirection = hexByteArrayToInt(Arrays.copyOfRange(body, 18,20));
        Integer windSpeed = hexByteArrayToInt(Arrays.copyOfRange(body, 20,22));
        Integer numBoatsInRace = hexByteArrayToInt(Arrays.copyOfRange(body, 22,23));
        Integer raceType = hexByteArrayToInt(Arrays.copyOfRange(body, 23,24));
        Double doubleWindDirection = windDirection * 360.0 / 65536.0;


// System.out.println("current time " + currentTime);
//        System.out.println("race id " + raceID);
//        System.out.println("race status " + raceStatus);
//        System.out.println("expected start time " + expectedStartTime);

        // format date
//        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//        Date resultdate = new Date(expectedStartTime);
//        System.out.println("FORMAT " + sdf.format(resultdate));
//
//        System.out.println("wind direction " + windDirection);
//        System.out.println(doubleWindDirection);
//        System.out.println("wind speed " + windSpeed);
//        System.out.println("# boats in race " + numBoatsInRace);
//        System.out.println("Race type " + raceType);

        HashMap<Integer, BoatStatus> boatStatuses = new HashMap<>();

        int currentByte = 24;
        for(int i = 0; i < numBoatsInRace; i++) {

            Integer sourceID = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte,currentByte + 4));
            Integer boatStatus = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte + 4,currentByte + 5));
            Integer legNumber = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte + 5,currentByte + 6));
            Integer numPenaltiesAwarded = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte + 6,currentByte + 7));
            Integer numPenaltiesServed = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte + 7,currentByte + 8));
            Integer estTimeAtNextMark = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte + 8,currentByte + 14));
            Integer estTimeAtFinish = hexByteArrayToInt(Arrays.copyOfRange(body, currentByte + 14,currentByte + 20));

//            System.out.println("boat source id " + sourceID);
//            System.out.println("boat status " + boatStatus);
//            System.out.println("leg num " + legNumber);
//            System.out.println("num penalties awarded " + numPenaltiesAwarded);
//            System.out.println("num penalties served " + numPenaltiesServed);
//            System.out.println("est time at next mark " + estTimeAtNextMark);
//            System.out.println("est time at finish " + estTimeAtFinish);

            boatStatuses.put(sourceID, new BoatStatus(sourceID,boatStatus, legNumber,
                    numPenaltiesAwarded, numPenaltiesServed, estTimeAtNextMark, estTimeAtFinish));
            currentByte+=20;


        }

        return new RaceStatusData(currentTime, raceID, raceStatusToString(raceStatus), expectedStartTime, doubleWindDirection,
                windSpeed, numBoatsInRace, raceType,boatStatuses);
    }

    /**
     * Converts the received race status integer to a string with meaning.
     * @param status Integer the race status integer
     * @return String the description of the race status
     */
    private String raceStatusToString(Integer status) {
        String statusString;
        switch(status) {
            case 0:
                statusString = "Not Active";
                break;
            case 1:
                statusString = "Warning";
                break;
            case 2:
                statusString = "Preparatory";
                break;
            case 3:
                statusString = "Started";
                break;
            case 4:
                statusString = "Finished";
                break;
            case 5:
                statusString = "Retired";
                break;
            case 6:
                statusString = "Abandoned";
                break;
            case 7:
                statusString = "Postponed";
                break;
            case 8:
                statusString = "Terminated";
                break;
            case 9:
                statusString = "Race start time not set";
                break;
            case 10:
                statusString = "Prestart";
                break;
            default:
                statusString = "No status found";
                break;

        }
        return statusString;

    }
    public RaceStatusData getRaceStatus() {
        return raceStatus;
    }


}