package com.hubay.mybatis.help;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**魔鬼预言
 * @author shuye
 * @time 2013-7-19
 */
public final class DemonPredict {

	private DemonPredict(){}
	
	/**
	 * @param expression
	 * @param message
	 * @param value
	 */
    public static void isTrue(boolean expression, String message, Object value) {
        if (expression == false) {
            throw new IllegalArgumentException(message + value);
        }
    }

    /**
     * @param expression
     * @param message
     * @param value
     */
    public static void isTrue(boolean expression, String message, long value) {
        if (expression == false) {
            throw new IllegalArgumentException(message + value);
        }
    }

   /**
    * @param expression
    * @param message
    * @param value
    */
    public static void isTrue(boolean expression, String message, double value) {
        if (expression == false) {
            throw new IllegalArgumentException(message + value);
        }
    }

   /**
    * @param expression
    * @param message
    */
    public static void isTrue(boolean expression, String message) {
        if (expression == false) {
            throw new IllegalArgumentException(message);
        }
    }

   /**
    * @param expression
    */
    public static void isTrue(boolean expression) {
        if (expression == false) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }

    /**
     * @param object
     */
    public static void notNull(Object object) {
        notNull(object, "The validated object is null");
    }

    /**
     * @param object
     * @param message
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param array
     * @param message
     */
    public static void notEmpty(Object[] array, String message) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param array
     */
    public static void notEmpty(Object[] array) {
        notEmpty(array, "The validated array is empty");
    }

    /**
     * @param collection
     * @param message
     */
    public static void notEmpty(Collection<Object> collection, String message) {
        if (collection == null || collection.size() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param collection
     */
    public static void notEmpty(Collection<Object> collection) {
        notEmpty(collection, "The validated collection is empty");
    }

    /**
     * @param map
     * @param message
     */
    public static void notEmpty(Map<Object,Object> map, String message) {
        if (map == null || map.size() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param map
     */
    public static void notEmpty(Map<Object,Object> map) {
        notEmpty(map, "The validated map is empty");
    }

    /**
     * @param string
     * @param message
     */
    public static void notEmpty(String string, String message) {
        if (string == null || string.length() == 0) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * @param string
     */
    public static void notEmpty(String string) {
        notEmpty(string, "The validated string is empty");
    }

    /**
     * @param array
     * @param message
     */
    public static void noNullElements(Object[] array, String message) {
        notNull(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * @param array
     */
    public static void noNullElements(Object[] array) {
        notNull(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                throw new IllegalArgumentException("The validated array contains null element at index: " + i);
            }
        }
    }

    /**
     * @param collection
     * @param message
     */
    public static void noNullElements(Collection<Object> collection, String message) {
        notNull(collection);
        for (Iterator<Object> it = collection.iterator(); it.hasNext();) {
            if (it.next() == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * @param collection
     */
    public static void noNullElements(Collection<Object> collection) {
        notNull(collection);
        int i = 0;
        for (Iterator<Object> it = collection.iterator(); it.hasNext(); i++) {
            if (it.next() == null) {
                throw new IllegalArgumentException("The validated collection contains null element at index: " + i);
            }
        }
    }

    /**
     * @param collection
     * @param clazz
     * @param message
     */
    public static <T> void allElementsOfType(Collection<Object> collection, Class<T> clazz, String message) {
        notNull(collection);
        notNull(clazz);
        for (Iterator<Object> it = collection.iterator(); it.hasNext(); ) {
            if (clazz.isInstance(it.next()) == false) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    /**
     * @param collection
     * @param clazz
     */
    public static <T> void allElementsOfType(Collection<Object> collection, Class<T> clazz) {
        notNull(collection);
        notNull(clazz);
        int i = 0;
        for (Iterator<Object> it = collection.iterator(); it.hasNext(); i++) {
            if (clazz.isInstance(it.next()) == false) {
                throw new IllegalArgumentException("The validated collection contains an element not of type "
                    + clazz.getName() + " at index: " + i);
            }
        }
    }
}
