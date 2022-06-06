package com.moveo.epicure.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SimpleTest {
   // private CustomerService service;
    @Mock
    private CustomerDetail detail;
    @Mock
    private CustomerRepo customerRepo;
    @Mock
    private CartRepo cartRepo;
    @Mock
    private MealRepo mealRepo;
    @Mock
    private ChosenMealRepo chosenMealRepo;
    @Mock
    private PasswordEncoder passwordEncoder;


    /**
     * instantiates the necessary fields for the tests
     */
    @BeforeAll
    static void initialiseTest() {
        //service = new CustomerService(detail, customerRepo, cartRepo, mealRepo, chosenMealRepo, passwordEncoder);
    }

    @Test
    void simple() {
        assertTrue(true);
    }
}
