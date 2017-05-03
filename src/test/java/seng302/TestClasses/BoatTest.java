package seng302.TestClasses;

import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;
import seng302.Model.Boat;
import seng302.Model.MutablePoint;

import static org.junit.Assert.*;

/**
 * Created by khe60 on 24/03/17.
 */
public class BoatTest {
    Boat boat;
    @Before
    public void setUp(){
        boat= new Boat("A", 10, new MutablePoint(10.0, 29.0), Color.BLACK,"ABC");
    }

    @Test
    public void getAbbreName() throws Exception {
        assertEquals(boat.getAbbreName(),"ABC");
        assertNotEquals(boat.getAbbreName(),"123");
    }

    @Test
    public void getColor() throws Exception {
        assertEquals(boat.getColor(),Color.BLACK);
    }

    @Test
    public void getTeamName() throws Exception {
        assertEquals(boat.getTeamName(),"A");
    }

    @Test
    public void getVelocity() throws Exception {
        assertEquals(boat.getVelocity(),10,0.1);
    }

    @Test
    public void getPosition() throws Exception {
        MutablePoint p=new MutablePoint(10.0,29.0);
        assertEquals(boat.getPosition(),p);
    }
    @Test
    public void updatePositionTest() throws Exception {
        Boat boat2=new Boat("Test",1000,new MutablePoint(32.29700,-64.861),Color.BEIGE,"T");
        System.out.println(boat2.getPosition());
        boat2.setCurrentHeading(35);
        boat2.updatePosition(1);
        assertEquals(32.304366,boat2.getPosition().getXValue(),0.000001);
        assertEquals(-64.854897,boat2.getPosition().getYValue(),0.000001);
    }

}