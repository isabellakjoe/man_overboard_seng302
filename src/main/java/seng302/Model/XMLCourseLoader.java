package seng302.Model;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by khe60 on 14/03/17.
 * An XML file parser for reading courses
 */
public class XMLCourseLoader {
    File inputFile;
    Double screenX;
    Double screenY;
    ArrayList<Gate> winds=new ArrayList<>();

    public XMLCourseLoader(File inputFile, Double x, Double y) {
        this.inputFile = inputFile;
        this.screenX = x;
        this.screenY = y;
    }

    /**
     *
     * @return
     */
    public double getWindDirection(){
        double x1=winds.get(0).getCentre().getXValue();
        double y1=winds.get(0).getCentre().getYValue();
        double x2=winds.get(1).getCentre().getXValue();
        double y2=winds.get(1).getCentre().getYValue();
//        System.out.println(x1);
//        System.out.println(y1);
//        System.out.println(x2);
//        System.out.println(y2);

        return Math.toDegrees(Math.atan( (x1-x2)/-(y1-y2)));
    }

    /**
     * Function to map latitude and longitude to screen coordinates
     * @param lat latitude
     * @param lon longitude
     * @param width width of the screen
     * @param height height of the screen
     * @return
     */
    public ArrayList<Double> mercatorProjection(double lat,double lon,double width, double height){
        ArrayList<Double> ret=new ArrayList<>();
        double x = (lon+180)*(width/360);
        double latRad = lat*Math.PI/180;
        double merc = Math.log(Math.tan(Math.PI/4)+(latRad/2));
        double y = (height/2)-(width*merc/(2*Math.PI));
        ret.add(x);
        ret.add(y);
        return ret;

    }

    /**
     * Creates a list of course features read from an xml file
     * @param width double the width of the screen
     * @param height double the height of the screen
     * @return List the list of course features
     * @throws JDOMException
     * @throws IOException
     */
    public ArrayList<CourseFeature> parseCourse(double width, double height) throws JDOMException, IOException {
        //buffers are defined as the total buffer size, i.e. total for both sides
        int index = 0;
        double bufferX=Math.max(40,width*0.2);
        double bufferY=Math.max(40,height*0.2);
        System.out.println("bufferX: "+bufferX);
        System.out.println("bufferY: "+bufferY);

        SAXBuilder saxbuilder = new SAXBuilder();
        Document document = saxbuilder.build(inputFile);
        Element raceCourse = document.getRootElement();
        List<Element> features = raceCourse.getChildren();
        ArrayList<CourseFeature> points = new ArrayList<>();


        ArrayList<Double> xCoords=new ArrayList<>();
        ArrayList<Double> yCoords=new ArrayList<>();

        for (Element feature : features) {

            String type = feature.getName();

            if (type.equals("gate")) { //its a gate

                List<Element> marks = feature.getChildren();
                Element markOne = marks.get(1);
                Element markTwo = marks.get(2);

                boolean isLine = Boolean.valueOf(feature.getAttributeValue("isLine"));
                boolean isFinish = Boolean.valueOf(feature.getAttributeValue("isFinish"));
                String name = feature.getChildText("name");

                double lat1 = Double.parseDouble(markOne.getChildText("latitude"));
                double lat2 = Double.parseDouble(markTwo.getChildText("latitude"));

                double lon1= Double.parseDouble(markOne.getChildText("longtitude"));
                double lon2= Double.parseDouble(markTwo.getChildText("longtitude"));

                ArrayList<Double> point1=mercatorProjection(lat1,lon1,width,height);
                ArrayList<Double> point2=mercatorProjection(lat2,lon2,width,height);
                double point1X=point1.get(0);
                double point1Y=point1.get(1);
                double point2X=point2.get(0);
                double point2Y=point2.get(1);


                xCoords.add(point1X);
                xCoords.add(point2X);
                yCoords.add(point1Y);
                yCoords.add(point2Y);

                MutablePoint p1=new MutablePoint(point1X,point1Y);
                MutablePoint p2=new MutablePoint(point2X,point2Y);
                Gate gate=new Gate(name, p1, p2, isFinish, isLine);
                gate.setIndex(index);
                index++;
                points.add(gate);


                if (feature.getAttributeValue("type")!=null) {

                        winds.add(gate);

                }
            } else { //Its a mark

                Element mark = feature.getChildren().get(1);
                String name = feature.getChildText("name");

                double lat1 =Double.parseDouble(mark.getChildText("latitude"));
                double lon1= Double.parseDouble(mark.getChildText("longtitude"));
                ArrayList<Double> point1=mercatorProjection(lat1,lon1,width,height);
                double point1X=point1.get(0);
                double point1Y=point1.get(1);
                xCoords.add(point1X);
                yCoords.add(point1Y);

                MutablePoint p1 = new MutablePoint(point1X,point1Y);
                Mark mark1 = new Mark(name, p1, false);
                mark1.setIndex(index);
                index++;
                points.add(mark1);
            }

        }

        double xFactor= (width-bufferX/2)/(Collections.max(xCoords)-Collections.min(xCoords));
        double yFactor=(height-bufferY/2)/(Collections.max(yCoords)-Collections.min(yCoords));

        //make scaling in proportion
        double factor=Math.min(xFactor,yFactor);


        points.stream().forEach(p->p.factor(factor,factor,Collections.min(xCoords),Collections.min(yCoords),bufferX/2,bufferY/2,width,height));

        return points;
    }
}
