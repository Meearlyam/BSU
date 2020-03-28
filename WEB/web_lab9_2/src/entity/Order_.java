package entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Vera Shavela
 * @version 1.0.0
 */
@StaticMetamodel(Order.class)
public class Order_ {
    public static volatile SingularAttribute<Order, Integer> id;

    public static volatile SingularAttribute<Order, Tour> tour;

    public static volatile SingularAttribute<Order, Client> client;
}
