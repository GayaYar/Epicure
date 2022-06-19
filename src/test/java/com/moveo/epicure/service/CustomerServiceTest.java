package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.moveo.epicure.aws.EmailSender;
import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.entity.LoginAttempt;
import com.moveo.epicure.exception.AccountBlockedException;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.mock.MockCustomer;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
public class CustomerServiceTest {

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
    @Mock
    private EmailSender emailSender;
    private MockCustomer mockCustomer;
    @Captor
    private ArgumentCaptor<Cart> cartArgumentCaptor;
    @Captor
    private ArgumentCaptor<ChosenMeal> chosenMealArgumentCaptor;
    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;
    @Captor
    private ArgumentCaptor<LoginAttempt> attemptArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    private LocalDateTime now;

    /**
     * instantiates the necessary fields for the tests
     */
    @BeforeEach
    void initialiseTest() {
        service = new CustomerService(detail, customerRepo, cartRepo, mealRepo, chosenMealRepo, passwordEncoder,
                attemptRepo, emailSender);
        mockCustomer = new MockCustomer();
        now = LocalDateTime.now();
    }

    @Test
    void loginReturnsEmptyWhenEmailDoesNotExist() {
        Mockito.when(customerRepo.findByEmail("notexisting@mail.com")).thenReturn(Optional.empty());
        assertEquals(service.login("notexisting@mail.com", "a-password", now), Optional.empty());
    }

    @Test
    void loginWhenBlockedThrowsExceptionAndDoesNotSaveAttemptAndAlertsAdmin() {
        String email = "blocked@mail.com";
        String password = "a-password";
        Mockito.when(customerRepo.findByEmail(email)).thenReturn(Optional.of(new Customer()));
        Mockito.when(attemptRepo.countByMailInTime(email, now.minusMinutes(30), now))
                .thenReturn(12l);
        try {
            service.login(email, password, now);
        } catch (Exception e) {
            assertEquals(e.getClass(), AccountBlockedException.class);
            Mockito.verify(attemptRepo, Mockito.times(0)).save(Mockito.any());
            Mockito.verify(emailSender, Mockito.times(1))
                    .messageAdmin(stringArgumentCaptor.capture(), stringArgumentCaptor.capture());
            List<String> bothValues = stringArgumentCaptor.getAllValues();
            assertEquals("Blocked user attempts to login", bothValues.get(0));
            assertEquals(
                    "User with email: " + email + " has made more than 10 failed login attempts in the last 30 minutes."
                    , bothValues.get(1));
        }
    }

    @Test
    void loginSuccessful() {
        String email = "mockCus@gmail.com";
        String password = "12345678";
        Mockito.when(attemptRepo.countByMailInTime(email, now.minusMinutes(30), now))
                .thenReturn(2l);
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);
        Mockito.when(customerRepo.findByEmail(email))
                .thenReturn(Optional.of(new Customer(9, "mock cus", email, password)));
        assertEquals(service.login(email, password, now), Optional.of(mockCustomer.mockResponse()));
    }

    @Test
    void loginFailedReturnsEmptyAndSavesAttempt() {
        String email = "mockCus@gmail.com";
        String password = "12345678";
        Mockito.when(attemptRepo.countByMailInTime(email, now.minusMinutes(30), now))
                .thenReturn(2l);
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(false);
        Mockito.when(customerRepo.findByEmail(email)).thenReturn(Optional.of(new Customer(2, "mock cus", email, password)));
        service.login(email, password, now);
        Mockito.verify(attemptRepo, Mockito.times(1)).save(attemptArgumentCaptor.capture());
        LoginAttempt captorValue = attemptArgumentCaptor.getValue();
        LoginAttempt expected = new LoginAttempt(email, LocalDateTime.now());
        assertTrue(captorValue.getMail().equals(expected.getMail()) &&
                Duration.between(captorValue.getTime(), expected.getTime()).toMinutes() < 1);
        assertEquals(service.login(email, password, now), Optional.empty());
    }

    /**
     * assures the method returns the correct cart
     */
    @Test
    void getCartFound() {
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(cartRepo.findCurrentWithMeals(3)).thenReturn(Optional.of(mockCustomer.getCurrentCart()));
        assertEquals(service.getCart(), mockCustomer.getCartDTO());
    }

    /**
     * assuring that if the customer doesn't have a current cart, a new empty cart will be saved for him
     */
    @Test
    void getCartNotFound() {
        Mockito.when(detail.getId()).thenReturn(5);
        Mockito.when(detail.getName()).thenReturn("cusName");
        Mockito.when(cartRepo.findCurrentWithMeals(5)).thenReturn(Optional.empty());
        Mockito.when(cartRepo.save(new Cart(true, new Customer(5, "cusName")))).thenReturn(mockCustomer.emptyCart());
        service.getCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), mockCustomer.emptyCart());
        assertEquals(service.getCart(), mockCustomer.emptyCartDto());
    }

    /**
     * makes sure that the method changes the "comment" field appropriately and saves the cart
     */
    @Test
    void updateCartComment() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Cart difCart = new Cart(5, true, "different", 58.9, new Customer(3, "cusName")
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.updateCartComment("different");
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), difCart);
    }

    /**
     * makes sure that the method converts the cart's "current" field to false and saves it
     */
    @Test
    void buyCart() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Cart notCurrentCart = new Cart(5, false, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.buyCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), notCurrentCart);
    }

    /**
     * checks that in an event where no such meal exists, a "NotFoundException" with a proper message is thrown
     */
    @Test
    void addToCartMealNotFound() {
        Mockito.when(mealRepo.findById(1)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> {
            service.addToCart(mockCustomer.mockMealDto());
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("Could not find the meal you were looking for.");
    }

    /**
     * checks that the method saves a converted meal (from mealDto to chosenMeal)
     */
    @Test
    void addToCartVerifySave() {
        Meal mockMeal = mockCustomer.mockMeal();
        Cart noMealsCart = mockCustomer.noMealsCart();
        Mockito.when(mealRepo.findById(1)).thenReturn(Optional.of(mockMeal));
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(noMealsCart));
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(chosenMealRepo.save(mockCustomer.convertedChosenMeal(mockMeal, noMealsCart)))
                .thenReturn(mockCustomer.mockChosenMeal(mockMeal, noMealsCart));
        service.addToCart(mockCustomer.mockMealDto());
        Mockito.verify(chosenMealRepo, Mockito.times(1)).save(chosenMealArgumentCaptor.capture());
        assertEquals(chosenMealArgumentCaptor.getValue(), mockCustomer.convertedChosenMeal(mockMeal, noMealsCart));
    }

    /**
     * checks that the returned value is converted (from mealDto to chosenMeal to cartMeal)
     */
    @Test
    void addToCartAssertReturnedType() {
        Meal mockMeal = mockCustomer.mockMeal();
        Cart noMealsCart = mockCustomer.noMealsCart();
        Mockito.when(mealRepo.findById(1)).thenReturn(Optional.of(mockMeal));
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(noMealsCart));
        Mockito.when(detail.getId()).thenReturn(3);
        Mockito.when(chosenMealRepo.save(mockCustomer.convertedChosenMeal(mockMeal, noMealsCart)))
                .thenReturn(mockCustomer.mockChosenMeal(mockMeal, noMealsCart));
        assertEquals(service.addToCart(mockCustomer.mockMealDto()), mockCustomer.mockCartMeal());
    }

    /**
     * verifies that the method calls the repository's "deleteByIdAndCart" method
     */
    @Test
    void deleteFromCart() {
        Cart noMealsCart = mockCustomer.noMealsCart();
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(noMealsCart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.deleteFromCart(6);
        Mockito.verify(chosenMealRepo, Mockito.times(1)).deleteByIdAndCart(6, noMealsCart);
    }

    /**
     * verifies that the method deletes all the chosen meals that are linked to the cart (calls the deleteByCart
     * method)
     */
    @Test
    void clearCartDeleteMeals() {
        Cart cart = new Cart(5, true, "yes", 58.9, new Customer(3, "cusName")
                , null);
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of(cart));
        Mockito.when(detail.getId()).thenReturn(3);
        service.clearCart();
        Mockito.verify(chosenMealRepo, Mockito.times(1)).deleteByCart(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), cart);
    }

    /**
     * verifies that the method updates the cart and saves it with default (empty) values
     */
    @Test
    void clearCart() {
        Customer customer = new Customer(3, "cusName");
        Mockito.when(cartRepo.findByCustomerIdAndCurrentTrue(3)).thenReturn(Optional.of
                (new Cart(5, true, "yes", 58.9, customer, null)));
        Mockito.when(detail.getId()).thenReturn(3);
        service.clearCart();
        Mockito.verify(cartRepo, Mockito.times(1)).save(cartArgumentCaptor.capture());
        assertEquals(cartArgumentCaptor.getValue(), new Cart(5, true, "", 0, customer, new ArrayList<>()));
    }

    /**
     * verifies the new user is saved with all relevant fields
     */
    @Test
    void signupSavesUser() {
        Mockito.when(passwordEncoder.encode("12345678")).thenReturn("12345678");
        Mockito.when(customerRepo.save(new Customer("mock cus", "mockCus@gmail.com", "12345678")))
                .thenReturn(new Customer(6, "mock cus", "mockCus@gmail.com", "12345678"));
        service.signup(mockCustomer.mockRegisterInfo());
        Mockito.verify(customerRepo, Mockito.times(1)).save(customerArgumentCaptor.capture());
        assertEquals(customerArgumentCaptor.getValue(),
                new Customer("mock cus", "mockCus@gmail.com", "12345678"));
    }

    /**
     * assures that the method returns a login response
     */
    @Test
    void signupReturnsLoginResponse() {
        Mockito.when(passwordEncoder.encode("12345678")).thenReturn("12345678");
        Mockito.when(customerRepo.save(new Customer("mock cus", "mockCus@gmail.com", "12345678")))
                .thenReturn(mockCustomer.mockCustomer());
        assertEquals(service.signup(mockCustomer.mockRegisterInfo()), mockCustomer.mockResponse());
    }

    /**
     * assures the method returns the list returned by "cartRepo.findByCustomerIdAndCurrentFalse" in a form of dto
     */
    @Test
    void getHistory() {
        Mockito.when(cartRepo.findByCustomerIdAndCurrentFalse(3)).thenReturn(mockCustomer.mockHistory());
        Mockito.when(detail.getId()).thenReturn(3);
        assertEquals(service.getHistory(), mockCustomer.mockHistoryDto());
    }
}