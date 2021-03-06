package mockDatafeed;

import models.Competitor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Method;

import static mockDatafeed.Keys.TACK;
import static mockDatafeed.Keys.UP;
import static org.junit.Assert.*;
import static parsers.MessageType.BOAT_ACTION;
import static utilities.Utility.fileToString;

/**
 * Created by khe60 on 30/05/17.
 */
public class BoatMockerTest {
    private BoatMocker boatMocker;
    private Class<?> mockerClass;

    @Before
    public void setUp() throws Exception {
        boatMocker=new BoatMocker();
        mockerClass=boatMocker.getClass();
    }

    @Test
    public void sendRaceXMLTest() throws Exception{
        String raceTemplateString= fileToString("/raceTemplate.xml");
        Method sendRaceXML=mockerClass.getDeclaredMethod("formatRaceXML",String.class);
        sendRaceXML.setAccessible(true);
        String resultString=(String) sendRaceXML.invoke(boatMocker,raceTemplateString);
        System.out.println(resultString);
    }

    @Test
    public void headingChangesWhenUpKeyPressed() throws Exception {
        byte[] header = new byte[15];
        byte[] packet = new byte[15];

        byte messageType = (byte) BOAT_ACTION.getValue();
        byte action = (byte) UP.getValue();
        byte sourceID = 100;

        header[0] = messageType;
        header[7] = sourceID;
        packet[0] = action;

        boatMocker.addConnection(); // generate competitors

        double initialHeading = 0;
        for (Competitor competitor : boatMocker.getCompetitors()) {
            if (competitor.getSourceID() == sourceID) {
                initialHeading = competitor.getCurrentHeading();
            }
        }

        boatMocker.interpretPacket(header, packet);

        for (Competitor competitor : boatMocker.getCompetitors()) {
            if (competitor.getSourceID() == sourceID) {
                assertNotEquals(initialHeading, competitor.getCurrentHeading());
            }
        }
    }


    @Test
    public void headingChangesWhenEnterKeyPressed() throws Exception {
        byte[] header = new byte[15];
        byte[] packet = new byte[15];

        byte messageType = (byte) BOAT_ACTION.getValue();
        byte action = (byte) TACK.getValue();
        byte sourceID = 100;

        header[0] = messageType;
        header[7] = sourceID;
        packet[0] = action;

        boatMocker.addConnection(); // generate competitors

        double expectedHeading = 0;
        for (Competitor competitor : boatMocker.getCompetitors()) {
            if (competitor.getSourceID() == sourceID) {
                double windAngle = boatMocker.getWindDirection();
                expectedHeading = windAngle - (competitor.getCurrentHeading() - windAngle);
            }
        }

        boatMocker.interpretPacket(header, packet);

        for (Competitor competitor : boatMocker.getCompetitors()) {
            if (competitor.getSourceID() == sourceID) {
                assertEquals(expectedHeading, competitor.getCurrentHeading(), 1);
            }
        }
    }


}