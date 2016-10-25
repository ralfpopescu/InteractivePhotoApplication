/**
 * Created by ralfpopescu on 9/30/16.
 */
public class ModeController {
    public boolean mode;
    public ViewMode viewMode;

    public enum ViewMode {
        PHOTOVIEW, GRIDVIEW, SPLITVIEW
    }

    public ModeController(){
        mode = true;
    }

    public void setMode(boolean newMode){
        mode = newMode;
    }

    public boolean getMode(){
        return mode;
    }

    public void setViewMode(ViewMode mode){
        viewMode = mode;
    }

    public ViewMode getViewMode(){
        return viewMode;
    }


}
