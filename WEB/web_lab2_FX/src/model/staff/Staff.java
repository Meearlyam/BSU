package model.staff;

import java.util.ArrayList;
import java.util.List;

/**
 * abstract class which describes staff
 *
 * @author Vera Shavela
 * @version 1.0.0
 */

public abstract class Staff {

    /**
     * name of the staff
     */
    protected String name;

    /**
     * getter of name
     *
     * @return name of staff
     */
    public String getName() {
        return name;
    }

    /**
     * years of work in company
     */
    protected double experience;

    /**
     * getter of experience
     *
     * @return experience of staff
     */
    public double getExperience() {
        return experience;
    }

    /**
     * monthly salary of the staff
     */
    protected double salary;

    /**
     * getter of staff salary
     *
     * @return staff salary
     */
    public double getSalary() {
        return salary;
    }

    /**
     * name of the department staff works
     */
    protected String department;

    /**
     * getter of department
     *
     * @return name od department staff works
     */
    public String getDepartment() {
        return department;
    }

    /**
     * social skills of staff
     */
    protected boolean isSocial;

    /**
     * getter of social skills
     *
     * @return social skills of staff
     */
    public boolean getSociality() {
        return isSocial;
    }

    /**
     * Constructor, creates staff with name, salary and dates
     *
     * @param name            name of staff
     * @param salary          salary of staff
     * @param department      department name staff works
     * @param experience      experience of staff
     * @throws IllegalArgumentException if experience or salary is bellow 0
     */
    protected Staff(String name, double salary, String department, double experience) {
        if (salary <= 0 || experience <= 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.salary = salary;
        this.department = department;
        this.experience = experience;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Staff staff = (Staff) obj;

        return salary == staff.salary
                && experience == staff.experience
                && name.equals(staff.name)
                && department.equals(staff.department);
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nSalary: %.2f\nDepartment: %s\nExperience: %.1f\n",
                name, salary, department, experience);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result += department != null ? department.hashCode() : 0;
        result += 31 * (result + salary + experience);
        return result;
    }

    /**
     * perform some work
     *
     * @return string of working process
     */
    public abstract String performWork();

}
