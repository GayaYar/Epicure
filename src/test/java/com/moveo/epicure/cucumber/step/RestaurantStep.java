package com.moveo.epicure.cucumber.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.entity.Chef;
import com.moveo.epicure.entity.Restaurant;
import com.moveo.epicure.mock.MockRestaurant;
import com.moveo.epicure.repo.ChefRepo;
import com.moveo.epicure.repo.RestaurantRepo;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class RestaurantStep {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestaurantRepo repo;
    @Autowired
    private ChefRepo chefRepo;
    private List<RestaurantBriefDTO> expected;
    private List<RestaurantBriefDTO> actual;
    private static MockRestaurant mockRestaurant;

    @BeforeAll
    public static void initialiseTest() {
        mockRestaurant = new MockRestaurant();
    }

    @Before
    public void beforeEach() {
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        repo.deleteAll();
    }

    @Given("^all the restaurants are in the database$")
    public void givenAllRestaurantsInDatabase() {
        Chef chef = chefRepo.save(new Chef(null, "yossi", "desc", "https://camo.githubusercontent.com/56ea24702a43a27f55794275849e38c16cd393e244a59297a71266b9b34e3e53/68747470733a2f2f617368616c6c656e64657369676e2e636f2e756b2f696d616765732f637573746f6d2f73686f72742d75726c2d6c6f676f2e706e67"
                , new ArrayList<>(), 1, new Date()));
        repo.saveAll(mockRestaurant.noMealsRestaurants(chef));
    }

    @When("^the user requests the popular restaurants$")
    public void whenUserRequestsPopularRestaurants() {
        actual.addAll(testRestTemplate.getForEntity("http://localhost:8080/restaurants/popular", List.class).getBody());
    }

    @Then("^the user gets the top 3 popular restaurants$")
    public void thenUserGetsMostPopularsRestaurants() {
        expected.add(new RestaurantBriefDTO(null, "name6", "yossi", 4, "img"));
        expected.add(new RestaurantBriefDTO(null, "name9", "yossi", 2, "img"));
        expected.add(new RestaurantBriefDTO(null, "name8", "yossi", 2, "img"));
        assertTrue(equalLists(expected, actual));
    }

    private boolean equalLists(List<RestaurantBriefDTO> l1, List<RestaurantBriefDTO> l2) {
        boolean equal = l1.size() == l2.size();
        int index = 0;
        while (equal && index<l1.size()) {
            equal = equalRestaurants(l1.get(index), l2.get(index));
            index++;
        }
        return equal;
    }

    private boolean equalRestaurants(RestaurantBriefDTO r1, RestaurantBriefDTO r2) {
        return r1.getName().equals(r2.getName())
                && r1.getChefName().equals(r2.getChefName())
                && r1.getRating()==r2.getRating()
                && r1.getImg().equals(r2.getImg());
    }
}
