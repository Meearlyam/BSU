package entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Vera Shavela
 * @version 1.0.0
 */
@StaticMetamodel(Tour.class)
public class Tour_ {
    public static volatile SingularAttribute<Tour, Integer> id;

    public static volatile SingularAttribute<Tour, String> name;

    public static volatile SingularAttribute<Tour, Double> cost;

    public static volatile SingularAttribute<Tour, TourType> type;

    public static volatile SingularAttribute<Tour, String> location;

    public static volatile SingularAttribute<Tour, Boolean> isBurning;

}
