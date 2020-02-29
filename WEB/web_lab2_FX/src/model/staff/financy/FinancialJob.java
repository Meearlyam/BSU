package model.staff.financy;

import model.staff.Staff;

/**
 * abstract class of financial job
 *
 * @author Vera Shavels
 * @version 1.0.0
 */
public abstract class FinancialJob extends Staff {

    /**
     * monthly reports amount
     */
    protected int reportsAmount;

    /**
     * getter of reports amount
     *
     * @return amount of monthly reports
     */
    public int getReportsAmount() {
        return reportsAmount;
    }

    /**
     * Constructor, creates staff with name, salary and experience
     *
     * @param name          name of staff
     * @param salary        salary of staff
     * @param experience    experience of staff
     * @param department    department name
     * @param reportsAmount monthly amount of reports
     * @throws IllegalArgumentException if experience or salary is bellow 0
     */
    protected FinancialJob(String name, double salary, String department, double experience, int reportsAmount) {
        super(name, salary, department, experience);
        if (experience < 0 || salary < 0)
            throw new IllegalArgumentException();
        this.reportsAmount = reportsAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;


        Staff staff = (Staff) obj;
        FinancialJob finantialJob = (FinancialJob) obj;

        return (finantialJob.reportsAmount == this.reportsAmount)
                && staff.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%sMonthly reports amount: %d\n", stringFromSuper, reportsAmount);
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + reportsAmount * 13;
    }

}
