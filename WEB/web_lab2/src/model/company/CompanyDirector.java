package model.company;

import model.staff.Staff;
import util.comparator.StaffExperienceComparator;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * this is company director that controls the company
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class CompanyDirector {

    /**
     * count total amount of staff in company
     *
     * @param staffs list of company staff
     * @return totalStaffAmount total amount of staff in company
     */
    public int countTotalStaffAmount(List<Staff> staffs) {
        return staffs.size();
    }

    /**
     * select staff with certain experience
     *
     * @param staffs staff list
     * @param minExp minimal years of work
     * @param maxExp maximum years of work
     * @return staffList list of staff with appropriate experience
     */
    public List<Staff> selectStaffByExperience(List<Staff> staffs, Double minExp, Double maxExp) {
        ArrayList<Staff> staffList = new ArrayList<>();
        for (Staff staff :
                staffs) {
            if(staff.getExperience() <= maxExp
                    && staff.getExperience() >= minExp) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    /**
     * select staff from certain department
     *
     * @param staffs staff list
     * @param department name of the required department
     * @return staffList list of staff with appropriate department name
     */
    public List<Staff> selectStaffByDepartment(List<Staff> staffs, String department) {
        ArrayList<Staff> staffList = new ArrayList<>();
        for (Staff staff :
                staffs) {
            if(staff.getDepartment().equals(department)) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    /**
     * select specific staff of company
     *
     * @param staffs list of staff
     * @param experience maximum years of work in company
     * @param department name of department staff work
     * @return staffList required staff list
     */
    public List<Staff> selectSpecificStaff(List<Staff> staffs, Double experience, String department) {
        ArrayList<Staff> staffList = new ArrayList<>();
        for (Staff staff :
                staffs) {
            if((staff.getExperience() <= experience) && (staff.getDepartment().equals(department))) {
                staffList.add(staff);
            }
        }
        return staffList;
    }

    /**
     * sort staff by work experience in company
     *
     * @param staffs staff list of company
     * @param isReversed is list should be sorted in reversed order
     * @return sorted staff list
     */
    public List<Staff> sortStaffByExperience(List<Staff> staffs, boolean isReversed) {
        Comparator<Staff> expComparator = new StaffExperienceComparator();
        if(isReversed) {
            expComparator = expComparator.reversed();
        }
        return staffs.stream()
                .sorted(expComparator)
                .collect(Collectors.toList());
    }
}