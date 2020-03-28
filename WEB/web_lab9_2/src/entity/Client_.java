package entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Vera Shavela
 * @version 1.0.0
 */
@StaticMetamodel(Client.class)
public class Client_ {
    public static volatile SingularAttribute<Client, Integer> id;

    public static volatile SingularAttribute<Client, String> fullName;

    public static volatile SingularAttribute<Client, Integer> paidOrdersAmount;

    public static volatile SingularAttribute<Client, Integer> personalDiscount;
}
