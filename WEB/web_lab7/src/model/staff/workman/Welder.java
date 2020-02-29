package model.staff.workman;

/**
 * Class of welder
 *
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Welder extends Workman {

    /**
     * skill of shipbuilding
     */
    protected Boolean isShipbuilder;

    /**
     * getter of shipbuilder skill
     */
    public boolean getIsShipbuiler() {
        return isShipbuilder;
    }


    /**
     * Constructor, creates staff with name, salary, experience and department
     *
     * @param name          name of staff
     * @param salary        salary of staff
     * @param experience    experience of staff
     * @param department    department of staff
     * @param shiftsAmount  amount of worked shifts
     * @param isShipbuilder shipbuilders skills
     * @throws IllegalArgumentException if salary, experience, shiftAmount or maxKilos is below 0
     */
    public Welder(String name, double salary, String department, double experience,
                     int shiftsAmount, boolean isShipbuilder) {
        super(name, salary, department, experience, shiftsAmount);
        if(salary < 0 || experience < 0 || shiftsAmount < 0) {
            throw new IllegalArgumentException();
        }
        this.isShipbuilder = isShipbuilder;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;


        Workman workman = (Workman) obj;
        Welder welder = (Welder) obj;

        return (welder.isShipbuilder == this.isShipbuilder)
                && workman.equals(this);
    }

    @Override
    public String toString() {
        String stringFromSuper = super.toString();
        return String.format("%sSkills to weld ships: %s\n", stringFromSuper, isShipbuilder ? "YES" : "NO");
    }

    @Override
    public int hashCode() {
        int hashCodeFromSuper = super.hashCode();
        return hashCodeFromSuper + isShipbuilder.hashCode() * 31 + 13;
    }

    @Override
    public String performWork() {
        return String.format("Loader can weld metal structures%s", isShipbuilder ? " even ships." : ".");
    }
}