package model.company;

import model.staff.Staff;

import java.util.ArrayList;
import java.util.List;

/**
 * this is company that consists of several departments and staff
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Company {

    /**
     * list of working staff
     */
    private List<Staff> staffs;

    /**
     * getter of staff
     *
     * @return staff list
     */
    public List<Staff> getStaff() {
        return staffs;
    }

    /**
     * director of company
     */
    private CompanyDirector director;

    /**
     * getter of director
     *
     * @return director
     */
    public CompanyDirector getDirector() {
        return director;
    }

    /**
     * Constructor that create staff with staff builder
     * @param director company director
     */
    public Company(CompanyDirector director) {
        this.director = director;
        staffs = new ArrayList<>();
    }


    /**
     * add staff to staff list
     *
     * @param staff staff to add to list
     */
    public void addStaff(Staff staff) {
        staffs.add(staff);
    }

    /**
     * count total amount of staff in company
     *
     * @return totalStaffAmount total amount of staff in company
     */
    public int countTotalStaffAmount() {
        return this.director.countTotalStaffAmount(staffs);
    }

    /**
     * select staff with certain experience
     *
     * @param minExp minimal years of work
     * @param maxExp maximum years of work
     * @return list of staff with defined experience
     */
    public List<Staff> selectStaffByExperience(Double minExp, Double maxExp) {
        return director.selectStaffByExperience(staffs, minExp, maxExp);
    }

    /**
     * select staff of certain department
     *
     * @param department name of the department whose staff is required
     * @return list of staff from required department
     */
    public List<Staff> selectStaffByDepartment(String department) {
        return director.selectStaffByDepartment(staffs, department);
    }

    /**
     * select staff by both parameters(department and below certain work experience)
     *
     * @param experience maximum work experience
     * @param department name of the department staff works in
     * @return list of staff with required department with experience below parameter
     */
    public List<Staff> selectSpecificStaff(double experience, String department) {
        return director.selectSpecificStaff(staffs, experience, department);
    }


    /**
     * sort staff by work experience in company
     *
     * @param isReversed is list should be sorted in reversed order
     * @return sorted staff list
     */
    public List<Staff> sortStaffByExperience(boolean isReversed) {
        return director.sortStaffByExperience(staffs, isReversed);
    }

}
