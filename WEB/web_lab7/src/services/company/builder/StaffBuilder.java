package services.company.builder;

import model.staff.Staff;
import model.staff.workman.Foreman;
import model.staff.workman.Loader;
import model.staff.workman.Welder;
import model.staff.financy.Accountant;
import model.staff.financy.Receptionist;

/**
 * Staff builder class that manage employees
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class StaffBuilder implements Builder {

    /**
     * staff name
     */
    private String name;

    /**
     * staff salary
     */
    private Double salary;

    /**
     * staff work experience
     */
    private Double experience;

    /**
     * department staff works in
     */
    private String department;

    /**
     * physical work or not
     */
    private Boolean isPhysical;

    /**
     * social staff or not
     */
    private Boolean isSocial;

    /**
     * weightlifting skills of staff
     */
    private Boolean isStrong;

    /**
     * number of worked shifts
     */
    protected Integer shiftsAmount;

    /**
     * monthly reports amount
     */
    protected Integer reportsAmount;

    /**
     * skill of shipbuilding
     */
    protected Boolean isShipbuilder;

    /**
     * maximum kilos loader is able to lift
     */
    protected Integer maxKilos;

    /**
     * number of subordinated
     */
    protected Integer subordinatedAmount;

    /**
     * received calls amount
     */
    private Integer callsAmount;

    /**
     * areas of calculation amount
     */
    private Integer calcAreasAmount;

    /**
     * set name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set salary
     */
    @Override
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * set experience
     */
    @Override
    public void setExperience(double experience) {
        this.experience = experience;
    }

    /**
     * set department
     */
    @Override
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * set physicality of work
     */
    @Override
    public void setPhysicality(boolean isPhysical) {
        this.isPhysical = isPhysical;
    }

    /**
     * set social skills
     */
    @Override
    public void setSociality(boolean isSocial) {
        this.isSocial = isSocial;
    }

    /**
     * set weightlifting skills
     */
    @Override
    public void setWeightlifting(boolean isStrong) {
        this.isStrong = isStrong;
    }

    /**
     * set amount of shifts
     */
    @Override
    public void setShiftsAmount(int shiftsAmount) {
        this.shiftsAmount = shiftsAmount;
    }

    /**
     * set amount of reports
     */
    @Override
    public void setReportsAmount(int reportsAmount) {
        this.reportsAmount = reportsAmount;
    }

    /**
     * set shipbuilding skills
     */
    @Override
    public void setIsShipbuilder(boolean isShipbuilder) {
        this.isShipbuilder = isShipbuilder;
    }

    /**
     * set max kilos amount
     */
    @Override
    public void setMaxKilos(int maxKilos) {
        this.maxKilos = maxKilos;
    }

    /**
     * set subordinated amount
     */
    @Override
    public void setSubordinatedAmount(int subordinatedAmount) {
        this.subordinatedAmount = subordinatedAmount;
    }

    /**
     * set calls amount
     */
    @Override
    public void setCallsAmount(int callsAmount) {
        this.callsAmount = callsAmount;
    }

    /**
     * set calculation areas amount
     */
    @Override
    public void setCalcAreasAmount(int calcAreasAmount) {
        this.calcAreasAmount = calcAreasAmount;
    }


    /**
     * make all fields as null
     */
    @Override
    public void reset() {
        name = null;
        salary = null;
        experience = null;
        department = null;
        isPhysical = null;
        isSocial = null;
        isStrong = null;
        shiftsAmount = null;
        reportsAmount = null;
        isShipbuilder = null;
        maxKilos = null;
        subordinatedAmount = null;
        callsAmount = null;
        calcAreasAmount = null;
    }

    /**
     * make new employee
     *
     * @return company employee with configured fields
     * @throws IllegalArgumentException if some field doesn't set
     */
    @Override
    public Staff makeEmployee() {
        if (name == null
                || salary == null
                || experience == null) {
            throw new IllegalArgumentException();
        }
        else {
            if(shiftsAmount != null) {
                if(isPhysical) {
                    if(isSocial && (subordinatedAmount != null)) {
                        return new Foreman(name, salary, "Workman department", experience,
                                shiftsAmount, subordinatedAmount);
                    }
                    else if(isStrong && (maxKilos != null)) {
                        return new Loader(name, salary, "Workman department", experience,
                                shiftsAmount, maxKilos);
                    }
                    else if(isShipbuilder != null) {
                        return new Welder(name, salary, "Workman department", experience,
                                shiftsAmount, isShipbuilder);
                    }
                }
            }
            else if(reportsAmount != null) {
                if(isSocial && (callsAmount != null)) {
                    return new Receptionist(name, salary, "Finance department", experience,
                            reportsAmount, callsAmount);
                }
                else if(calcAreasAmount != null) {
                    return new Accountant(name, salary, "Finance department", experience,
                            reportsAmount, calcAreasAmount);
                }
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        throw new IllegalArgumentException();
    }
}
