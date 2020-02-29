package model.staff.financy;

/**
 * Receptionist class
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Receptionist extends FinancialJob {

    /**
     * received calls amount
     */
    private int callsAmount;

    /**
     * getter of calls amount
     *
     * @return calls amount
     */
    public int getCallsAmount() {
        return callsAmount;
    }

    /**
     * Constructor, creates staff with name, salary, experience and department
     *
     * @param name          name of staff
     * @param salary        salary of staff
     * @param experience    experience of staff
     * @param department    department name
     * @param reportsAmount monthly amount of reports
     * @param callsAmount   calls amount
     * @throws IllegalArgumentException if experience or salary is bellow 0
     */
    public Receptionist(String name, double salary, String department,
                        double experience, int reportsAmount, int callsAmount) {
        super(name, salary, department, experience, reportsAmount);
        if(experience < 0 || salary < 0) {
            throw new IllegalArgumentException();
        }
        this.callsAmount = callsAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FinancialJob financialJob = (FinancialJob) obj;
        Receptionist receptionist = (Receptionist) obj;

        return (receptionist.callsAmount == this.callsAmount)
                && financialJob.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%s Calls amount: %d\n", stringFromSuper, callsAmount);
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + (callsAmount < 10 ? 1 : -1) * 31;
    }

    @Override
    public String performWork() {
        return String.format("Receptionist supports" +
                "the organisation of company's work and receives %d calls a month.", callsAmount);
    }

}
