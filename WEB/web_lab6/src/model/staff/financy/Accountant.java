package model.staff.financy;
/**
 * Accountant class
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Accountant extends FinancialJob {

    /**
     * areas of calculation amount
     */
    private int calcAreasAmount;

    /**
     * getter of calculationAreas amount
     *
     * @return calculation areas amount
     */
    public int getCalculationAreasAmount() {
        return calcAreasAmount;
    }

    /**
     * Constructor, creates staff with name, salary, experience and department
     *
     * @param name                      name of staff
     * @param salary                    salary of staff
     * @param experience                experience of staff
     * @param department                department name
     * @param reportsAmount             monthly amount of reports
     * @param calcAreasAmount           calculation areas amount
     * @throws IllegalArgumentException if experience or salary is bellow 0
     */
    public Accountant(String name, double salary, String department,
                        double experience, int reportsAmount, int calcAreasAmount) {
        super(name, salary, department, experience, reportsAmount);
        if(experience < 0 || salary < 0) {
            throw new IllegalArgumentException();
        }
        this.calcAreasAmount = calcAreasAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        FinancialJob financialJob = (FinancialJob) obj;
        Accountant accountant = (Accountant) obj;

        return (accountant.calcAreasAmount == this.calcAreasAmount)
                && financialJob.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%s Calculation areas amount: %d\n", stringFromSuper, calcAreasAmount);
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + (calcAreasAmount < 5 ? 1 : -1) * 13;
    }

    @Override
    public String performWork() {
        return String.format("Accountant calculates" +
                "financial data of company in %d spheres.", calcAreasAmount);
    }
}
