package model.entity;

/**
 * class that represent client entity
 * @author Vera Shavela
 * @version 1.0.0
 */
public class Client {

    /**
     * id of client
     */
    private int id;

    /**
     * full name of client
     */
    private String fullName;

    /**
     * amount of paid orders
     */
    private int paidOrdersAmount;

    /**
     * personal discount
     */
    private int personalDiscount;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setPersonalDiscount(int personalDiscount) {
        this.personalDiscount = personalDiscount;
    }

    public int getPersonalDiscount() {
        return personalDiscount;
    }

    public void setPaidOrdersAmount(int paidOrdersAmount) {
        this.paidOrdersAmount = paidOrdersAmount;
    }

    public int getPaidOrdersAmount() {
        return paidOrdersAmount;
    }

    /**
     * constructor to create client
     * @param id id of client
     * @param fullName full name of client
     * @param paidOrdersAmount amount of paid orders
     * @param personalDiscount personal discount of client
     */
    public Client(int id, String fullName, int paidOrdersAmount, int personalDiscount) {
        setId(id);
        setFullName(fullName);
        setPaidOrdersAmount(paidOrdersAmount);
        setPersonalDiscount(personalDiscount);
    }

    @Override
    public String toString() {
        return String.format("Client:\n\tid - %d\n\tfullName - %s\n\tpaidOrdersAmount - %d\n\tpersonalDiscount - %d", id, fullName, paidOrdersAmount, personalDiscount);
    }
}
