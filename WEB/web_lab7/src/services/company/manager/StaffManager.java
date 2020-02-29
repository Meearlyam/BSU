package services.company.manager;

import services.company.builder.Builder;
import model.staff.Staff;

/**
 * manager that provides creating new staff
 */
public class StaffManager {

    /**
     * provide accountant creating
     *
     * @param builder    to make some staff
     * @param name       name of staff
     * @param salary     salary of staff
     * @param experience staff's experience of work
     * @param department department name
     * @param reports    reports amount per month
     * @param calcAreas  calculation areas of staff
     */
    public Staff provideAccountant(Builder builder, String name, double salary, double experience,
            String department, int reports, int calcAreas) {
        builder.setName(name);
        builder.setSalary(salary);
        builder.setExperience(experience);
        builder.setDepartment(department);
        builder.setReportsAmount(reports);
        builder.setCalcAreasAmount(calcAreas);
        builder.setPhysicality(false);
        builder.setSociality(true);
        return builder.makeEmployee();
    }

    /**
     * provide receptionist creating
     *
     * @param builder       to make some staff
     * @param name          name of staff
     * @param salary        salary of staff
     * @param experience    staff's experience of work
     * @param department    department name
     * @param reports       reports amount per month
     * @param calls         calls amount per month
     */
    public Staff provideReceptionist(Builder builder, String name, double salary, double experience,  String department,
                                     int reports, int calls) {
        builder.setName(name);
        builder.setSalary(salary);
        builder.setExperience(experience);
        builder.setDepartment(department);
        builder.setReportsAmount(reports);
        builder.setCallsAmount(calls);
        builder.setPhysicality(false);
        builder.setSociality(true);
        return builder.makeEmployee();
    }

    /**
     * provide foreman creating
     *
     * @param builder            to make some staff
     * @param name               name of staff
     * @param salary             salary of staff
     * @param experience         experience of staff
     * @param department         department of staff
     * @param shifts             amount of worked shifts
     * @param subordinated       amount of subordinated
     */
    public Staff provideForeman(Builder builder, String name, double salary, double experience, String department,
                                int shifts, int subordinated) {
        builder.setName(name);
        builder.setSalary(salary);
        builder.setExperience(experience);
        builder.setDepartment(department);
        builder.setShiftsAmount(shifts);
        builder.setSubordinatedAmount(subordinated);
        builder.setPhysicality(true);
        builder.setSociality(true);
        return builder.makeEmployee();
    }

    /**
     * provide loader creating
     *
     * @param builder       to make some staff
     * @param name          name of staff
     * @param salary        salary of staff
     * @param experience    experience of staff
     * @param department    department name
     * @param shifts        amount of worked shifts
     * @param maxKilos      maximum kilos loader able to raise
     */
    public Staff provideLoader(Builder builder, String name, double salary, double experience, String department,
                               int shifts, int maxKilos) {
        builder.setName(name);
        builder.setSalary(salary);
        builder.setExperience(experience);
        builder.setDepartment(department);
        builder.setShiftsAmount(shifts);
        builder.setMaxKilos(maxKilos);
        builder.setPhysicality(true);
        builder.setWeightlifting(true);
        builder.setSociality(false);
        return builder.makeEmployee();
    }

    /**
     * provide welder creating
     *
     * @param builder           to make some staff
     * @param name              name of staff
     * @param salary            salary of staff
     * @param experience        experience of staff
     * @param department        department name
     * @param shifts            amount of worked shifts
     * @param isShipbuilder     shows whether welder able to build ships
     */
    public Staff provideWelder(Builder builder, String name, double salary, double experience, String department,
                               int shifts, boolean isShipbuilder) {
        builder.setName(name);
        builder.setSalary(salary);
        builder.setExperience(experience);
        builder.setDepartment(department);
        builder.setShiftsAmount(shifts);
        builder.setIsShipbuilder(isShipbuilder);
        builder.setPhysicality(true);
        builder.setSociality(false);
        builder.setWeightlifting(false);
        return builder.makeEmployee();
    }
}
