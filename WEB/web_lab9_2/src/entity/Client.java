package entity;

import javax.persistence.*;

/**
 * class that represent client entity
 * @author Vera Shavela
 * @version 1.0.0
 */

@NamedQueries({
        @NamedQuery(
                name = "deleteClients",
                query = "DELETE FROM Client"
        ),
        @NamedQuery(
                name = "deleteClient",
                query = "DELETE FROM Client c WHERE c.id = :id"
        ),
        @NamedQuery(
                name = "readClients",
                query = "SELECT c FROM Client c"
        ),
        @NamedQuery(
                name = "readClient",
                query = "SELECT c FROM Client c WHERE c.id = :id"
        ),
        @NamedQuery(
                name = "updateClients",
                query = "UPDATE Client c set c.personalDiscount = :personalDiscount WHERE c.paidOrdersAmount > :paidOrdersAmount"
        )
})
@Entity
@Table(name = Client.tableName)
public class Client implements DBObject {

    public static final String tableName = "clients";
    public static final String fullNameColumnName = "full_name";
    public static final String paidOrdersColumnName = "paid_orders_amount";
    public static final String personalDiscountColumnName = "personal_discount";

    /**
     * id of client
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * full name of client
     */
    @Column(name = Client.fullNameColumnName)
    private String fullName;

    /**
     * amount of paid orders
     */
    @Column(name = Client.paidOrdersColumnName)
    private int paidOrdersAmount;

    /**
     * personal discount
     */
    @Column(name = Client.personalDiscountColumnName)
    private int personalDiscount;

    public void setID(int id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
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

    @Override
    public String toString() {
        return String.format("Client:\n\tid - %d\n\tfullName - %s\n\tpaidOrdersAmount - %d\n\tpersonalDiscount - %d", id, fullName, paidOrdersAmount, personalDiscount);
    }
}
