package seng302;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by mgo65 on 4/03/17.
 */
public class RegattaFactory {

    /**
     * Creates an Instance of Regatta and injects all dependencies
     * @return Regatta, a regatta object
     */
    public Regatta createRegatta(int numberOfBoats) {

        //create competitors
        Boat boat1 = new Boat("Oracle Team USA", 9);
        Boat boat2 = new Boat("Emirates Team New Zealand", 8);
        Boat boat3 = new Boat("Ben Ainslie Racing", 7);
        Boat boat4 = new Boat("SoftBank Team Japan", 6);
        Boat boat5 = new Boat("Team France", 5);
        Boat boat6 = new Boat("Artemis Racing", 4);

        List competitors = new ArrayList<Competitor>();
        competitors.add(boat1);
        competitors.add(boat2);
        competitors.add(boat3);
        competitors.add(boat4);
        competitors.add(boat5);
        competitors.add(boat6);

        //randomly select competitors
        Collections.shuffle(competitors);
        competitors = competitors.subList(0, numberOfBoats);


        //create the match races, only one is used for now
        MatchRace race1 = new MatchRace();
        MatchRace race2 = new MatchRace();
        MatchRace race3 = new MatchRace();

        List<Race> races = new ArrayList<>();
        races.add(race1);
        races.add(race2);
        races.add(race3);

        //create the marks
        List<CoursePoint> points = new ArrayList<>();
        points.add(new Mark("PreStart", new Point(-20.0, 0.0)));
        points.add(new Mark("Start Gate", new Point(0.0, 0.0)));
        points.add(new Mark("Mark", new Point(100.0, 10.0)));
        points.add(new Mark("Leeward Gate", new Point(120.0, 180.0)));
        points.add(new Mark("Windward Gate", new Point(70.0, -70.0)));
        points.add(new Mark("Leeward Gate", new Point(120.0, 180.0)));
        points.add(new Mark("Finish", new Point(50.0, 230.0)));

        //inject the dependencies for the regatta
        return new Regatta(competitors, races, points);
    }
}
