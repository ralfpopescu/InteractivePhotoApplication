/**
 * Created by ralfpopescu on 9/28/16.
 */

import java.awt.*;

public class TextRegion {
    int width;
    int height;
    Point startingPoint;
    Point endPoint;
    String text;

    public TextRegion(Point start, Point end, String z){
        startingPoint = start;
        endPoint = end;
        width = (int)(end.getX() - start.getX());
        height = (int)(end.getY() - start.getY());
        text = "";
    }

    public Point getStartingPoint(){
        return startingPoint;
    }

    public void setStartingPoint(double x, double y){
        startingPoint.setLocation(x,y);
    }

    public Point getEndPoint(){
        return endPoint;
    }

    public void setEndPoint(double x, double y){
        endPoint.setLocation(x,y);
    }

    public void updateHeightWidth(){
        width = (int)(endPoint.getX() - startingPoint.getX());
        height = (int)(endPoint.getY() - startingPoint.getY());
    }

    public void extendHeight(int amount){
        height = height + amount;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public String getText(){
        return text;
    }

    public void addCharacter(char a){
        text += a;
    }
}
