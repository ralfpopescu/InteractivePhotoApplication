import java.util.ArrayList;
import java.awt.*;
import java.util.Arrays;

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

    public int identifyDirection(Point p1, Point p2) { //identifies direction between two points
        int direction = 0;
        double p1_x = p1.getX();
        double p1_y = p1.getY();
        double p2_x = p2.getX();
        double p2_y = p2.getY();

        double delta_x = p2_x - p1_x;
        double delta_y = p2_y - p1_y;

        double x_over_y = Math.abs(delta_x/delta_y); //ratio decides if cardinal direction or in between
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



    public String matchTemplate(ArrayList<Integer> n){ // matches a list of numbers with a gesture
        String template = "UNREC";
        int[] nums = new int[n.size()];



        for(int i = 0; i < n.size(); i++){
            nums[i] = n.get(i);
        }

        System.out.println(n);

        int[] rightAngle = {3, 5};
        int[] leftAngle = {5, 3};
        int[] pigtail = {3, 5, 0, 7};
        int[] pigtail2 = {3,7,0,4};

        int[] up = {N};
        int[] v = {3, 7};
        int[] z = {E, SW, E};
        int[] down = {S};

        if(Arrays.equals(nums,rightAngle)){
            template = "RIGHTANGLE";
        }
        if(Arrays.equals(nums,leftAngle)){
            template = "LEFTANGLE";
        }
        if(Arrays.equals(nums,pigtail) || isPigtail(n)){
            template = "PIGTAIL";
        }
        if(Arrays.equals(nums,up)){
            template = "FAMILY";
        }
        if(Arrays.equals(nums,v)){
            template = "WORK";
        }
        if(Arrays.equals(nums,z)){
            template = "VACATION";
        }
        if(Arrays.equals(nums,down)){
            template = "SCHOOL";
        }


        return template;
    }

    public ArrayList<Integer> stripDuplicates(ArrayList<Integer> directions){ //strips away numbers in succession
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

    public ArrayList<Integer> stripSmalls(ArrayList<Integer> noSmalls){ //gets rid of little numbers inbetween strokes

        if(noSmalls.size() > 0) {
            noSmalls.remove(0);
        }

        int i = 0;

        while(i < noSmalls.size() - 2){
            int current = noSmalls.get(i);
            int next = noSmalls.get(i);
            int count = 0;

            while(i + count < noSmalls.size()-1 && current == next){ //counting successive numbers
                count++;
                next = noSmalls.get(i + count);
            }

            if(count < 4) { //decide that 3 or less numbers are insignificant
                for(int j = 0; j < count; j++){
                    noSmalls.remove(i);
                }
            } else {
                i = i + count;
            }
        }

        if(noSmalls.size() > 0) {
            noSmalls.remove(noSmalls.size() - 1);
        }

        return noSmalls;
    }


    public double[] getExtremesNESW(ArrayList<Point> points){ //gets the most left/right/up/down points to create a bounding box
        //N, E, S, W
        double Nex = 999999, Sex = 0, Eex = 0, Wex = 999999;
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
                Sex = y;
            }
        }

        extremesNESW[0] = Nex;
        extremesNESW[1] = Eex;
        extremesNESW[2] = Sex;
        extremesNESW[3] = Wex;

        System.out.println(Nex);
        System.out.println(Eex);
        System.out.println(Sex);
        System.out.println(Wex);


        return extremesNESW;

    }

    public int[] makeBoundingBox(double[] extremes){ //makes array out of extreme points to represent box
        int[] boundingBox = new int[4];
        boundingBox[0] = (int)extremes[3];
        boundingBox[1] = (int)extremes[0];
        boundingBox[2] = (int)extremes[3] - (int)extremes[1];
        boundingBox[3] = (int)extremes[2] - (int)extremes[0];

        return boundingBox;
    }

    public boolean isPigtail(ArrayList<Integer> n){ //decides if deletion gesture
        return ((n.contains(3) && n.contains(7) && n.contains(0) && n.contains(4)) ||
                (n.contains(4) && n.contains(7) && n.contains(0) && n.contains(5)));
    }

    public boolean annotationDelete(ArrayList<Point> n){
        ArrayList<Integer> x = new ArrayList<>();
        x = createGestureString(n);
        x = stripSmalls(x);
        x = stripDuplicates(x);
        String s = matchTemplate(x);

        return(s.equals("PIGTAIL"));

    }
}
