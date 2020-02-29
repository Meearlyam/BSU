package util;

import java.util.Map;

public class Printer {
    public static <T extends Iterable<E>, E> void printList(T list) {
        for (E element : list) {
            System.out.println(element);
        }
    }

    public static <T extends Iterable<Map.Entry<E, K>>, E, K> void printListOfTuples(T list) {
        for (Map.Entry<E, K> element : list) {
            System.out.println(element.getKey());
            System.out.println(element.getValue());
        }
    }

    public static void printHeader(String header) {
        System.out.println("\n\t\t" + header.toUpperCase() + " :");
    }
}
