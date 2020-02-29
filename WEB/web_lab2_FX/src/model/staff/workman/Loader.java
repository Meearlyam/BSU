package model.staff.workman;

/**
 * Class of Loader
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Loader extends Workman {

    /**
     * maximum kilos loader is able to lift
     */
    protected int maxKilos;

    /**
     * getter of max kilos amount
     */
    public int getMaxKilos() {
        return maxKilos;
    }


    /**
     * Constructor, creates staff with name, salary, experience and department
     *
     * @param name         name of staff
     * @param salary       salary of staff
     * @param experience   experience of staff
     * @param department   department of staff
     * @param shiftsAmount amount of worked shifts
     * @param maxKilos     amount of subordinated
     * @throws IllegalArgumentException if salary, experience, shiftAmount or maxKilos is below 0
     */
    public Loader(String name, double salary, String department, double experience,
                      int shiftsAmount, int maxKilos) {
        super(name, salary, department, experience, shiftsAmount);
        if(salary < 0 || experience < 0
                || shiftsAmount < 0 || maxKilos < 0) {
            throw new IllegalArgumentException();
        }
        this.maxKilos = maxKilos;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;


        Workman workman = (Workman) obj;
        Loader loader = (Loader) obj;

        return (loader.maxKilos == this.maxKilos)
                && workman.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%sMax kilos to lift: %d\n", stringFromSuper, maxKilos);
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + maxKilos * 31;
    }

    @Override
    public String performWork() {
        return String.format("Loader can lift heavy" +
                "load up to %d kilos.", maxKilos);
    }
}

