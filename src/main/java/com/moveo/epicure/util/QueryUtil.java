package com.moveo.epicure.util;

public class QueryUtil {
    public static String getCurrentCartWithMeals() {
        return "SELECT c from Cart c join FETCH c.chosenMeals where c.id=:id";
    }
}
