/**
 * Created by ralfpopescu on 9/30/16.
 */
public class ModeController {
    public boolean mode;
    public boolean selectMode;
    public String viewMode;

    public enum ViewMode {
        PHOTOVIEW, GRIDVIEW, SPLITVIEW
    }

    public ModeController(){
        mode = true;
        selectMode = false;
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




}
