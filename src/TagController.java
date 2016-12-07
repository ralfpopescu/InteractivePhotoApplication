import javax.swing.*;

/**
 * Created by ralfpopescu on 11/13/16.
 */
public class TagController {

    JToggleButton vacationToggle, familyToggle, schoolToggle, workToggle;
    JLabel status;

    public TagController(JToggleButton vacation, JToggleButton family,
                         JToggleButton school, JToggleButton work, JLabel statusLabel) {

        vacationToggle = vacation;
        schoolToggle = school;
        familyToggle = family;
        workToggle = work;

        status = statusLabel;

    }
    public void updateTags(boolean[] tags, LightTable lt){ //updates tags accordingly

            vacationToggle.setSelected(lt.getVacationTag());
            workToggle.setSelected(lt.getWorkTag());
            schoolToggle.setSelected(lt.getSchoolTag());
            familyToggle.setSelected(lt.getFamilyTag());

            lt.transferTagsToThumbnails();


    }

    public void setGestureStatus(String stat){ //sets status bar
        if(stat.equals("UNREC"))
        {
            status.setText("Status: Unrecognized Gesture");
        } else if (stat.equals("RIGHTANGLE")){
            status.setText("Next");
        } else if (stat.equals("LEFTANGLE")){
            status.setText("Previous");
        } else if (stat.equals("PIGTAIL")){
            status.setText("Deleted:");
        } else if (stat.equals("FAMILY")){
            status.setText("Tagged: Family");
        } else if (stat.equals("WORK")){
            status.setText("Tagged: Work");
        } else if (stat.equals("VACATION")){
            status.setText("Tagged: Vacation");
        } else if (stat.equals("SCHOOL")){
            status.setText("Tagged: School");
        } else {
            status.setText("Status:");
        }


    }
}
