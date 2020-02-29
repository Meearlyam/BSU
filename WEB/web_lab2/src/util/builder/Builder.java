package util.builder;

import model.staff.Staff;

/**
 * Builder interface
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public interface Builder {

    void setName(String name);

    void setSalary(double salary);

    void setDepartment(String department);

    void setExperience(double experience);

    void setPhysicality(boolean isPhysical);

    void setSociality(boolean isSocial);

    void setWeightlifting(boolean isStrong);

    void setShiftsAmount(int shiftsAmount);

    void setReportsAmount(int reportsAmount);

    void setIsShipbuilder(boolean isShipbuilder);

    void setMaxKilos(int maxKilos);

    void setSubordinatedAmount(int subordinatedAmount);

    void setCallsAmount(int callsAmount);

    void setCalcAreasAmount(int calcAreasAmount);

    void reset();

    Staff makeEmployee();
}
