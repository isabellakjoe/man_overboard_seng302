package parsers.raceStatus;

import parsers.RaceStatusEnum;

import java.util.HashMap;

/**
 * Created by Pang on 3/05/17.
 * Race status data
 */
public class RaceStatusData {

    private long currentTime;
    private RaceStatusEnum raceStatus;
    private long expectedStartTime;
    private Integer numBoatsInRace;
    private Double windDirection;
    private Integer windSpeed;
    private HashMap<Integer, BoatStatus> boatStatuses = new HashMap<>();


    /**
     * Race Status Data
     * @param currentTime long current time
     * @param raceStatus RaceStatusEnum race status enum
     * @param expectedStartTime long expected start time
     * @param windDirection double wind direction in degrees
     * @param windSpeed Integer wind speed in mm/s
     * @param numBoatsInRace int num boats in race
     * @param boatStatuses Map boat statuses
     */
    RaceStatusData(long currentTime, RaceStatusEnum raceStatus, long expectedStartTime, double windDirection,
                   Integer windSpeed, Integer numBoatsInRace, HashMap<Integer, BoatStatus> boatStatuses) {
        this.currentTime = currentTime;
        this.raceStatus = raceStatus;
        this.expectedStartTime = expectedStartTime;
        this.numBoatsInRace = numBoatsInRace;
        this.boatStatuses = boatStatuses;
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }


    public long getCurrentTime() {
        return currentTime;
    }
    public RaceStatusEnum getRaceStatus() {
        return raceStatus;
    }
    public long getExpectedStartTime() {
        return expectedStartTime;
    }
    public Integer getNumBoatsInRace() {
        return numBoatsInRace;
    }
    public HashMap<Integer, BoatStatus> getBoatStatuses() {
        return boatStatuses;
    }

    public Double getWindDirection() {
        return windDirection;
    }

    public Integer getWindSpeed() {
        return windSpeed;
    }


}
