package com.moveo.epicure.cucumber.step;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.moveo.epicure.dto.RestaurantBriefDTO;
import com.moveo.epicure.mock.MockRestaurant;
import com.moveo.epicure.repo.RestaurantRepo;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class RestaurantStep {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private RestaurantRepo repo;
    private List<RestaurantBriefDTO> expected;
    private List<RestaurantBriefDTO> actual;
    private MockRestaurant mockRestaurant;

    @BeforeAll
    private void initialiseTest() {
        mockRestaurant = new MockRestaurant();
    }

    @Before
    private void beforeEach() {
        expected = new ArrayList<>();
        actual = new ArrayList<>();
        repo.deleteAll();
    }

    @Given("^all the restaurants are in the database$")
    public void givenAllRestaurantsInDatabase() {
        repo.saveAll(mockRestaurant.getAllRestaurants());
    }

    @When("^the user requests the popular restaurants$")
    public void whenUserRequestsPopularRestaurants() {
        actual.addAll(testRestTemplate.getForEntity("restaurants/popular", List.class).getBody());
    }

    @Then("^the user gets the top ([0-9]) popular restaurants$")
    public void thenUserGetsMostPopularsRestaurants(final Integer amount) {
        expected.addAll(mockRestaurant.getBriefIndexes(6, 9, 8));
        assertEquals(expected, actual);
    }
}
