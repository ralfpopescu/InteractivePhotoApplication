/**
 * Created by ralfpopescu on 9/30/16.
 */
public class ModeController {
    public boolean mode;
    public boolean selectMode;
    public String viewMode;
    public boolean dragging;
    public boolean magnetMode;

    public enum ViewMode {
        PHOTOVIEW, GRIDVIEW, SPLITVIEW
    }

    public ModeController(){
        mode = true;
        selectMode = false;
        dragging = false;
        magnetMode = false;
        viewMode = "PHOTOVIEW";
    }

    public void setMode(boolean newMode){
        mode = newMode;
    }

    public boolean getMode(){
        return mode;
    }

    public void setViewMode(String mode){
        viewMode = mode;
    }

    public String getViewMode(){
        return viewMode;
    }

    public boolean getSelectMode(){
        return selectMode;
    }

    public void setSelectMode(boolean x){
        selectMode = x;
    }

    public boolean dragging(){
        return dragging;
    }

    public void setDragging(boolean x){
        dragging = x;
    }

    public void setMagnetMode(boolean x){
        magnetMode = x;
        System.out.println(x);

    }

    public boolean getMagnetMode(){
        return magnetMode;

    }




}
