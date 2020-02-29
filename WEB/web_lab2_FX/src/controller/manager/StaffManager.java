package controller.manager;

import controller.builder.Builder;
import model.staff.Staff;
import model.staff.financy.Accountant;

import java.util.Random;

/**
 * manager that provides creating new staff
 */
public class StaffManager {

    private Random random = new Random();

    /**
     * provide accountant creating
     *
     * @param builder    to make some staff
     * @param name       name of staff
     * @param department department name
     */
    public Staff provideAccountant(Builder builder, String name, String department) {
        builder.setName(name);
        builder.setSalary(random.nextDouble() % 1500 + 400);
        builder.setExperience(random.nextDouble() % 10 + 1);
        builder.setDepartment(department);
        builder.setReportsAmount(random.nextInt() % 5 + 6);
        builder.setCalcAreasAmount(random.nextInt() % 3 + 1);
        builder.setPhysicality(false);
        builder.setSociality(false);
        return builder.makeEmployee();
    }

    /**
     * provide receptionist creating
     *
     * @param builder       to make some staff
     * @param name          name of staff
     * @param department    department name
     */
    public Staff provideReceptionist(Builder builder, String name, String department) {
        builder.setName(name);
        builder.setSalary(random.nextDouble() % 1000 + 200);
        builder.setExperience(random.nextDouble() % 4 + 1);
        builder.setDepartment(department);
        builder.setReportsAmount(random.nextInt() % 6 + 7);
        builder.setCallsAmount(random.nextInt() % 200 + 50);
        builder.setPhysicality(false);
        builder.setSociality(true);
        return builder.makeEmployee();
    }

    /**
     * provide foreman creating
     *
     * @param builder       to make some staff
     * @param name          name of staff
     * @param department    department name
     */
    public Staff provideForeman(Builder builder, String name, String department) {
        builder.setName(name);
        builder.setSalary(random.nextDouble() % 1500 + 200);
        builder.setExperience(random.nextDouble() % 6 + 15);
        builder.setDepartment(department);
        builder.setShiftsAmount(random.nextInt() % 5 + 15);
        builder.setSubordinatedAmount(random.nextInt() % 2 + 8);
        builder.setPhysicality(true);
        builder.setSociality(true);
        return builder.makeEmployee();
    }

    /**
     * provide loader creating
     *
     * @param builder       to make some staff
     * @param name          name of staff
     * @param department    department name
     */
    public Staff provideLoader(Builder builder, String name, String department) {
        builder.setName(name);
        builder.setSalary(random.nextDouble() % 1000 + 400);
        builder.setExperience(random.nextDouble() % 6 + 15);
        builder.setDepartment(department);
        builder.setShiftsAmount(random.nextInt() % 5 + 12);
        builder.setMaxKilos(random.nextInt() % 15 + 15);
        builder.setPhysicality(true);
        builder.setWeightlifting(true);
        builder.setSociality(false);
        return builder.makeEmployee();
    }

    /**
     * provide welder creating
     *
     * @param builder       to make some staff
     * @param name          name of staff
     * @param department    department name
     */
    public Staff provideWelder(Builder builder, String name, String department) {
        builder.setName(name);
        builder.setSalary(random.nextDouble() % 1500 + 200);
        builder.setExperience(random.nextDouble() % 6 + 15);
        builder.setDepartment(department);
        builder.setShiftsAmount(random.nextInt() % 5 + 15);
        builder.setIsShipbuilder(random.nextBoolean());
        builder.setPhysicality(true);
        builder.setSociality(false);
        builder.setWeightlifting(false);
        return builder.makeEmployee();
    }
}
