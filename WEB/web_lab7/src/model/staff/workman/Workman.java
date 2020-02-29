package model.staff.workman;

import model.staff.Staff;

/**
 * abstract class of workman
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public abstract class Workman extends Staff {

    /**
     * number of worked shifts
     */
    protected int shiftsAmount;

    /**
     * getter of worked shifts
     */
    public int getShiftsAmount() {
        return shiftsAmount;
    }


    /**
     * Constructor, creates staff with name, salary, experience and department
     *
     * @param name         name of staff
     * @param salary       salary of staff
     * @param experience   experience of staff
     * @param department   department of staff
     * @param shiftsAmount amount of worked shifts
     * @throws IllegalArgumentException if salary, experience or shiftAmount is below 0
     */
    protected Workman(String name, double salary, String department, double experience, int shiftsAmount) {
        super(name, salary, department, experience);
        if(salary < 0 || experience < 0 || shiftsAmount < 0) {
            throw new IllegalArgumentException();
        }
        this.shiftsAmount = shiftsAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;


        Staff staff = (Staff) obj;
        Workman workman = (Workman) obj;

        return (workman.shiftsAmount == this.shiftsAmount)
                && staff.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%sMonthly worked shifts amount: %d\n", stringFromSuper, shiftsAmount);
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + shiftsAmount * 31;
    }

}
