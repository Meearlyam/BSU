package model.staff.workman;

/**
 * Class of foreman
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Foreman extends Workman {

    /**
     * number of subordinated
     */
    protected int subordinatedAmount;

    /**
     * getter of subordinated number
     */
    public int getSubordinatedAmount() {
        return subordinatedAmount;
    }


    /**
     * Constructor, creates staff with name, salary, experience and department
     *
     * @param name               name of staff
     * @param salary             salary of staff
     * @param experience         experience of staff
     * @param department         department of staff
     * @param shiftsAmount       amount of worked shifts
     * @param subordinatedAmount amount of subordinated
     * @throws IllegalArgumentException if salary, experience, shiftAmount or subordinated is below 0
     */
    public Foreman(String name, double salary, String department, double experience,
                      int shiftsAmount, int subordinatedAmount) {
        super(name, salary, department, experience, shiftsAmount);
        if(salary < 0 || experience < 0
                || shiftsAmount < 0 || subordinatedAmount < 0) {
            throw new IllegalArgumentException();
        }
        this.subordinatedAmount = subordinatedAmount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;


        Workman workman = (Workman) obj;
        Foreman foreman = (Foreman) obj;

        return (foreman.subordinatedAmount == this.subordinatedAmount)
                && workman.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%sSubordinated workers amount: %d\n", stringFromSuper, subordinatedAmount);
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + subordinatedAmount * 13;
    }

    @Override
    public String performWork() {
        return String.format("Foreman manages" +
                "a group of loaders or welders up to %d people.", subordinatedAmount);
    }
}
