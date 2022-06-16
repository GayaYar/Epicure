package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.entity.LoginAttempt;
import com.moveo.epicure.exception.AccountBlockedException;
import com.moveo.epicure.mock.MockCustomer;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
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
    @Captor
    private ArgumentCaptor<LoginAttempt> attemptArgumentCaptor;
    private LocalDateTime now;

    /**
     * instantiates the necessary fields for the tests
     */
    @BeforeEach
    void initialiseTest() {
        service = new CustomerService(detail, customerRepo, cartRepo, mealRepo, chosenMealRepo, passwordEncoder, attemptRepo);
        mockCustomer = new MockCustomer();
        now = LocalDateTime.now();
    }

    //login tests with limit
    @Test
    void loginReturnsEmptyWhenEmailDoesNotExist() {
        Mockito.when(customerRepo.existsByEmail("notexisting@mail.com")).thenReturn(false);
        assertEquals(service.login("notexisting@mail.com", "a-password", now), Optional.empty());
    }

    //login tests with limit
    @Test
    void loginWhenBlockedThrowsExceptionAndDoesNotSaveAttempt() {
        String email = "blocked@mail.com";
        String password = "a-password";
        Mockito.when(customerRepo.existsByEmail(email)).thenReturn(true);
        Mockito.when(attemptRepo.countByMailInTime(email, Timestamp.valueOf(now.minusMinutes(30)), Timestamp.valueOf(now)))
                .thenReturn(12l);
        try {
            service.login(email, password, now);
        }catch (Exception e) {
            assertEquals(e.getClass(), AccountBlockedException.class);
            Mockito.verify(attemptRepo, Mockito.times(0)).save(Mockito.any());
        }
    }

    //login tests with limit
    @Test
    void loginSuccessful() {
        String email = "mockCus@gmail.com";
        String password = "12345678";
        LoginInfo info = new LoginInfo(email, password);
        Mockito.when(customerRepo.existsByEmail(email)).thenReturn(true);
        Mockito.when(attemptRepo.countByMailInTime(email, Timestamp.valueOf(now.minusMinutes(30)), Timestamp.valueOf(now)))
                .thenReturn(2l);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        Mockito.when(customerRepo.findByEmailAndPassword(email, password)).thenReturn(Optional.of(new Customer(1, "bob", email, password)));
        assertEquals(service.login(email, password, now), Optional.of(mockCustomer.mockResponse()));
    }

    //login tests with limit
    @Test
    void loginFailedReturnsEmptyAndSavesAttempt() {
        String email = "mockCus@gmail.com";
        String password = "12345678";
        LoginInfo info = new LoginInfo(email, password);
        Mockito.when(customerRepo.existsByEmail(email)).thenReturn(true);
        Mockito.when(attemptRepo.countByMailInTime(email, Timestamp.valueOf(now.minusMinutes(30)), Timestamp.valueOf(now)))
                .thenReturn(2l);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        Mockito.when(customerRepo.findByEmailAndPassword(email, password)).thenReturn(Optional.empty());
        service.login(email, password, now);
        Mockito.verify(attemptRepo, Mockito.times(1)).save(attemptArgumentCaptor.capture());
        LoginAttempt captorValue = attemptArgumentCaptor.getValue();
        LoginAttempt expected = new LoginAttempt(email, LocalDateTime.now());
        assertTrue(captorValue.getMail().equals(expected.getMail()) &&
                Duration.between(captorValue.getTime(), expected.getTime()).toMinutes()<1);
        assertEquals(service.login(email, password, now), Optional.empty());
    }
}