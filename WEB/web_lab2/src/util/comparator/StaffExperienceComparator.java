package util.comparator;

import model.staff.Staff;

import java.util.Comparator;


/**
 * Comparator class of staff by work experience in company
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class StaffExperienceComparator implements Comparator<Staff> {

    @Override
    public int compare(Staff st1, Staff st2) {
        return Double.compare(st1.getExperience(), st2.getExperience());
    }

}
