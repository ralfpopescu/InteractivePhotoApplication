import java.util.ArrayList;
import java.awt.*;

/**
 * Created by ralfpopescu on 11/10/16.
 */
public class Siger {
    //N, S, E, W, NE, NW, SE, SW
    //GE, GN, GW, GS
    public Siger(){

    }

    public String interpretGesture(ArrayList<Point> points){
        String gesture = null;


        for(int i = 0; i < points.size() - 2; i++){
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);
        }

        return gesture;
    }

    public String identifyDirection(Point p1, Point p2) {
        String direction = null;
        double p1_x = p1.getX();
        double p1_y = p1.getY();
        double p2_x = p1.getX();
        double p2_y = p1.getY();

        double delta_x = p2_x - p1_x;
        double delta_y = p2_y - p1_y;

        double x_over_y = Math.abs(delta_x/delta_y);
        double y_over_x = Math.abs(delta_y/delta_x);

        //if delta y is negative and ratio is about 5
        if(delta_y < 0 && y_over_x > 5){
            direction = "N";
        }

        //if delta y is negative
        if(delta_y < 0 && y_over_x < 5 && delta_x > 0){
            direction = "N";
        }

        //if delta y is positive
        if(delta_y < 0 && y_over_x > 5){
            direction = "S";
        }

        //if delta y is positive
        if(delta_y < 0 && y_over_x < 5 && delta_x < 0){
            direction = "S";
        }

        //if delta x is negative

        if(delta_x < 0 && x_over_y < 5 && delta_y < 0){
            direction = "S";
        }

        //if delta x is negative

        if(delta_x < 0 && x_over_y < 5 && delta_y < 0){
            direction = "S";
        }

        //if delta x is positive

        if(delta_x < 0 && x_over_y < 5 && delta_y < 0){
            direction = "S";
        }

        //if delta x is positive

        if(delta_x < 0 && x_over_y < 5 && delta_y < 0){
            direction = "S";
        }

        return direction;

    }
}
