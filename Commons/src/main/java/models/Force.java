package models;

import static java.lang.Math.*;

/**
 * Created by khe60 on 26/07/17.
 * Class for a force vector
 */
public class Force {
    private double x;
    private double y;
    private double magnitude;
    private double direction;

    /**
     * constructor for the force vector
     * @param x either horizontal velocity or magnitude depending on inputType
     * @param y either vertical velocity or direction depending on inputType
     *          direction in degrees
     * @param inputType specifies what x and y are
     *                  true - x and y are horizontal and vertical velocity
     *                  false - x and y are magnitude and direction
     */
    public Force(double x, double y, boolean inputType) {
        if(inputType){
            this.x = x;
            this.y = y;
            this.magnitude=sqrt(x*x+y*y);
            this.direction=capDirection(toDegrees(atan2(x,y)));


        }
        else{
            this.magnitude=x;
            this.direction=y;
            this.x=magnitude*sin(toRadians(y));
            this.y=magnitude*cos(toRadians(y));
        }
    }

    /**
     * makes direction range from 0 to 360
     * @param direction the direction to be modified
     * @return the modified direction ranging from 0 to 360
     */
    private double capDirection(double direction){
        double retDirection=direction;
        //make direction always positive
        if(retDirection<0){
            retDirection=360+retDirection;
        }

        return retDirection%360;
    }

    public void setX(double x) {
        this.x = x;
        this.magnitude=sqrt(x*x+y*y);
        this.direction=capDirection(toDegrees(atan2(x,y)));
    }

    public void setY(double y) {
        this.y = y;
        this.magnitude=sqrt(x*x+y*y);
        this.direction=capDirection(toDegrees(atan2(x,y)));
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
        this.x=magnitude*sin(toRadians(y));
        this.y=magnitude*cos(toRadians(y));
    }

    public void setDirection(double direction) {
        this.direction = capDirection(direction);
        this.x=magnitude*sin(toRadians(y));
        this.y=magnitude*cos(toRadians(y));
    }


    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getDirection() {
        return direction;
    }
}