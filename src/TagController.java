import javax.swing.*;

/**
 * Created by ralfpopescu on 11/13/16.
 */
public class TagController {

    JToggleButton vacationToggle, familyToggle, schoolToggle, workToggle;

    public TagController(JToggleButton vacation, JToggleButton family,
                         JToggleButton school, JToggleButton work) {

        vacationToggle = vacation;
        schoolToggle = school;
        familyToggle = family;
        workToggle = work;

    }
    public void updateTags(boolean[] tags){
        if(tags != null) {
            vacationToggle.setSelected(tags[0]);
            workToggle.setSelected(tags[1]);
            schoolToggle.setSelected(tags[2]);
            familyToggle.setSelected(tags[3]);
        }

    }
}
