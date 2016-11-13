import java.util.ArrayList;
import java.awt.*;

/**
 * Created by ralfpopescu on 11/10/16.
 */
public class Siger {
    //N, S, E, W, NE, NW, SE, SW
    public static final int N = 0;
    public static final int NE = 1;
    public static final int E = 2;
    public static final int SE = 3;
    public static final int S = 4;
    public static final int SW = 5;
    public static final int W = 6;
    public static final int NW = 7;

    public static final int GN = 8;
    public static final int GE = 9;
    public static final int GS = 10;
    public static final int GW = 11;

    public static final int ratio = 7;

    public Siger(){

    }

    public ArrayList<Integer> createGestureString(ArrayList<Point> points){
        String gestureString = null;
        ArrayList<Integer> gestureDirectionList = new ArrayList<>();

        for(int i = 0; i < points.size() - 2; i++){
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);

            gestureDirectionList.add(identifyDirection(p1,p2));
        }

        return gestureDirectionList;
    }

    public int identifyDirection(Point p1, Point p2) {
        int direction = 0;
        double p1_x = p1.getX();
        double p1_y = p1.getY();
        double p2_x = p2.getX();
        double p2_y = p2.getY();

        double delta_x = p2_x - p1_x;
        double delta_y = p2_y - p1_y;

        double x_over_y = Math.abs(delta_x/delta_y);
        double y_over_x = Math.abs(delta_y/delta_x);

        //if delta y is negative and ratio is about 5
        if(delta_y < 0 && y_over_x > ratio){
            direction = N;
        }

        //if delta y is negative
        if(delta_y < 0 && y_over_x < ratio && delta_x > 0){
            direction = NE;
        }

        if(delta_y < 0 && y_over_x < ratio && delta_x > 0){
            direction = NW;
        }

        //if delta y is positive
        if(delta_y > 0 && y_over_x > ratio){
            direction = S;
        }

        //if delta y is positive
        if(delta_y > 0 && y_over_x < ratio && delta_x > 0){
            direction = SE;
        }

        if(delta_y > 0 && y_over_x < ratio && delta_x < 0) {
            direction = SW;
        }

        if(delta_x < 0 && x_over_y > ratio) {
            direction = W;
        }

        if(delta_x > 0 && x_over_y > ratio) {
            direction = E;
        }

        return direction;

    }

    public String interpretGesture(){
        return "";
    }

    public void createTemplates() {
        int[] rightAngle = {SW, SE};
        int[] leftAngle = {SE, SW};
        int[] pigtail = {SE, S, SW, W, NW, N, NE};

        int[] up = {N};
        int[] v = {SE, NE};
        int[] z = {E, SW, E};
        int[] down = {S};

        int[] circle = {SE, S, SW, W, NW, N, NE};
    }

    public String matchTemplate(){
        String template = null;

        System.out.println(template);
        return template;
    }

    public ArrayList<Integer> stripDuplicates(ArrayList<Integer> directions){
        int currentDirection;
        int count = 0;
        ArrayList<Integer> noDups = new ArrayList<>();

        while(count < directions.size()){


            currentDirection = directions.get(count);


            if(count == directions.size() - 1){
                int previousDirection = directions.get(directions.size() - 2);
                if(currentDirection != previousDirection){
                    noDups.add(currentDirection);
                }
                break;
            }

            count++;
            int nextDirection = directions.get(count);

            noDups.add(currentDirection);

            while(currentDirection == nextDirection && count < directions.size()-1){
                nextDirection = directions.get(count);
                count++;
            }

        }

        return noDups;

    }

    public ArrayList<Integer> stripSmalls(ArrayList<Integer> noSmalls){

        noSmalls.remove(0);

        int i = 0;

        while(i < noSmalls.size() - 2){
            int current = noSmalls.get(i);
            int next = noSmalls.get(i);
            int count = 0;

            while(i + count < noSmalls.size()-1 && current == next){
                count++;
                next = noSmalls.get(i + count);
            }

            if(count < 4) {
                for(int j = 0; j < count; j++){
                    noSmalls.remove(i);
                }
            } else {
                i = i + count;
            }
        }


        noSmalls.remove(noSmalls.size() - 1);

        return noSmalls;
    }


    public double[] getExtremesNESW(ArrayList<Point> points){
        //N, E, S, W
        double Nex = 0, Sex = 0, Eex = 0, Wex = 0;
        double[] extremesNESW = new double[4];

        for(Point p: points){
            double x = p.getX();
            double y = p.getY();

            if(x < Wex){
                Wex = x;
            }
            if(x > Eex){
                Eex = x;
            }
            if(y < Nex){
                Nex = y;
            }
            if(y > Sex){
                Eex = y;
            }
        }

        extremesNESW[0] = Nex;
        extremesNESW[1] = Eex;
        extremesNESW[2] = Sex;
        extremesNESW[3] = Wex;


        return extremesNESW;

    }
}
