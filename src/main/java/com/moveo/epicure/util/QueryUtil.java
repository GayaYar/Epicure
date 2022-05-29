package com.moveo.epicure.util;

public class QueryUtil {
    public static final String getChefWithRestaurants = "SELECT c FROM Chef c JOIN FETCH c.restaurants WHERE c.id=:id";
}
