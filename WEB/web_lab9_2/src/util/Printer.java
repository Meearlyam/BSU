package util;

import java.util.List;
import java.util.Map;

public class Printer {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * print list of the object
     *
     * @param list  list of some values
     * @param title some text to printed before list
     */
    public static <T extends Iterable<E>, E> void printList(T list, String title) {
        System.out.println(ANSI_GREEN + "------------" + title + "------------" + ANSI_RESET);
        for (E element : list) {
            System.out.println(element);
        }
        System.out.println();
    }

    /**
     * print list of Map.Entry
     *
     * @param list  list of some values
     * @param title some text to printed before list
     */
    public static <T extends Iterable<Map.Entry<E, K>>, E, K> void printListOfTuples(T list, String title) {
        System.out.println(ANSI_GREEN + "------------" + title + "------------" + ANSI_RESET);
        for (Map.Entry<E, K> element : list) {
            System.out.println(element.getKey());
            System.out.println(element.getValue());
        }
        System.out.println();
    }

    /**
     * print map with list as value
     *
     * @param map   map of some values
     * @param title some text to printed before list
     */
    public static<E, K> void printMapWithList(Map<E, List<K>> map, String title) {
        System.out.println(ANSI_GREEN + "------------" + title + "------------" + ANSI_RESET);
        for(Map.Entry<E, List<K>> element : map.entrySet()) {
            System.out.println(element.getKey());
            for(K valueElement : element.getValue()) {
                System.out.println(valueElement);
            }
        }
        System.out.println();
    }


    /**
     * print header
     *
     * @param title some text to print like a header
     */
    public static void printHeader(String title) {
        System.out.println(ANSI_GREEN + "------------" + title + "------------" + ANSI_RESET);
    }
}
