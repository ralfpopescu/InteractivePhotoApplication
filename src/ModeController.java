/**
 * Created by ralfpopescu on 9/30/16.
 */
public class ModeController {
    public boolean mode;

    public ModeController(){
        mode = true;
    }

    public void setMode(boolean newMode){
        mode = newMode;
    }

    public boolean getMode(){
        return mode;
    }
}
