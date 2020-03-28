package entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author Vera Shavela
 * @version 1.0.0
 */
@StaticMetamodel(TourType.class)
public class TourType_ {
    public static volatile SingularAttribute<TourType, Integer> id;

    public static volatile SingularAttribute<TourType, String> name;
}
