package models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests for the Clock Model
 */
public class ClockTest {

    private WorldClock worldClock;
    private ClockHandler handler;

    @Before
    public void setUp() {
    }

    @Test
    public void doesntUseDefaultWhenUTCIsValid() throws Exception {
        String offsetUTC = "+9";

        handler = (newTime, clock) -> {
            int beginIndex = newTime.length() - (3 + offsetUTC.length());
            int endIndex = newTime.length();
            assertTrue(newTime.substring(beginIndex, endIndex).equals("UTC+9"));
        };

        worldClock = new WorldClock(handler, offsetUTC);
        worldClock.handle(System.currentTimeMillis());
    }

    @Test
    public void doesntUseDefaultWhenUTCIsPrefixedByZero() throws Exception {
        String offsetUTC = "+09";

        handler = (newTime, clock) -> {
            int beginIndex = newTime.length() - (3 + offsetUTC.length());
            int endIndex = newTime.length();
            assertTrue(newTime.substring(beginIndex, endIndex).equals("UTC+09"));
        };

        worldClock = new WorldClock(handler, offsetUTC);
        worldClock.handle(System.currentTimeMillis());
    }

    @Test
    public void usesDefaultTimeWhenUTCIsNull() throws Exception {
        handler = (newTime, clock) -> {
            int beginIndex = newTime.length() - 5;
            int endIndex = newTime.length();
            assertTrue(newTime.substring(beginIndex, endIndex).equals("UTC+0"));
        };

        worldClock = new WorldClock(handler, null);
        worldClock.handle(System.currentTimeMillis());

    }

    @Test
    public void usesDefaultTimeWhenUTCIsInvalid() throws Exception {
        String offsetUTC = "abc";

        handler = (newTime, clock) -> {
            int beginIndex = newTime.length() - 5;
            int endIndex = newTime.length();
            assertTrue(newTime.substring(beginIndex, endIndex).equals("UTC+0"));
        };

        worldClock = new WorldClock(handler, offsetUTC);
        worldClock.handle(System.currentTimeMillis());
    }

    @Test
    public void usesDefaultTimeWhenUTCOutOfRange() throws Exception {
        String offsetUTC = "+400";

        handler = (newTime, clock) -> {
            int beginIndex = newTime.length() - 5;
            int endIndex = newTime.length();
            assertTrue(newTime.substring(beginIndex, endIndex).equals("UTC+0"));
        };

        worldClock = new WorldClock(handler, offsetUTC);
        worldClock.handle(System.currentTimeMillis());
    }

    @Test
    public void usesDefaultTimeWhenUTCHasIncorrectFormat() throws Exception {
        String offsetUTC = "12";

        handler = (newTime, clock) -> {
            int beginIndex = newTime.length() - 5;
            int endIndex = newTime.length();
            assertTrue(newTime.substring(beginIndex, endIndex).equals("UTC+0"));
        };

        worldClock = new WorldClock(handler, offsetUTC);
        worldClock.handle(System.currentTimeMillis());
    }

}