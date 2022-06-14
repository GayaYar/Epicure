package com.moveo.epicure.service;

import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.mock.MockCustomer;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService service;
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
    @Mock
    private AttemptRepo attemptRepo;
    private MockCustomer mockCustomer;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    private ArgumentCaptor<ChosenMeal> chosenMealArgumentCaptor;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    /**
     * instantiates the necessary fields for the tests
     */
    @BeforeEach
    void initialiseTest() {
        service = new CustomerService(detail, customerRepo, cartRepo, mealRepo, chosenMealRepo, passwordEncoder, attemptRepo);
        mockCustomer = new MockCustomer();
    }

    //take this instead of other login methods
    @Test
    void loginReturnsEmptyWhenEmailDoesNotExist() {
    }
}