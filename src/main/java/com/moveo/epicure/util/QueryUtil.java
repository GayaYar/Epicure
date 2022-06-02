package com.moveo.epicure.util;

public class QueryUtil {
    public static final String getCurrentCartWithMeals = "SELECT c from Cart c join FETCH c.chosenMeals where c.id=:id";
    public static final String getRestaurantsByParams =
            "SELECT r FROM Restaurant r WHERE r.price >= :minPrice and r.price <= :maxPrice  and "
                    + "SQRT((r.longitude-:longitude)*(r.longitude-:longitude) + (r.latitude-:latitude)*(r.latitude-:latitude))"
                    + "<=:distance and (r.open = true or r.open = :open) and r.rating = :rating";
  
    public static final String getChefWithRestaurants = "SELECT c FROM Chef c JOIN FETCH c.restaurants WHERE c.id=:id";

    public static final String restaurantByIdWithMeals = "SELECT r FROM Restaurant r JOIN FETCH r.meals WHERE r.id=:id";

    public static final String searchByName =
            "SELECT r FROM Restaurant r JOIN r.chef c JOIN r.meals m WHERE r.name=:name or c.name=:name or m.name=:name";
}
