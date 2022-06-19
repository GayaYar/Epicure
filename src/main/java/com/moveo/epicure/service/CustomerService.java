package com.moveo.epicure.service;

import com.moveo.epicure.aws.EmailSender;
import com.moveo.epicure.dto.CartDTO;
import com.moveo.epicure.dto.CartMealDTO;
import com.moveo.epicure.dto.CustomerDetail;
import com.moveo.epicure.dto.LoginInfo;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.dto.MealDTO;
import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.entity.Cart;
import com.moveo.epicure.entity.ChosenMeal;
import com.moveo.epicure.entity.Customer;
import com.moveo.epicure.entity.LoginAttempt;
import com.moveo.epicure.entity.Meal;
import com.moveo.epicure.entity.Option;
import com.moveo.epicure.exception.AccountBlockedException;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.util.DtoMapper;
import com.moveo.epicure.util.LoginResponseMaker;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
    private CustomerDetail detail;
    private CustomerRepo customerRepo;
    private CartRepo cartRepo;
    private MealRepo mealRepo;
    private ChosenMealRepo chosenMealRepo;
    private PasswordEncoder passwordEncoder;
    private AttemptRepo attemptRepo;
    private EmailSender emailSender;
    private static final long ALLOWED_ATTEMPTS = 10;
    private static final long ATTEMPT_MINUTES = 30;

    public CustomerService(CustomerDetail detail, CustomerRepo customerRepo, CartRepo cartRepo, MealRepo mealRepo,
            ChosenMealRepo chosenMealRepo, PasswordEncoder passwordEncoder, AttemptRepo attemptRepo,
            EmailSender emailSender) {
        this.detail = detail;
        this.customerRepo = customerRepo;
        this.cartRepo = cartRepo;
        this.mealRepo = mealRepo;
        this.chosenMealRepo = chosenMealRepo;
        this.passwordEncoder = passwordEncoder;
        this.attemptRepo = attemptRepo;
        this.emailSender = emailSender;
    }

    /**
     * Gets the customer's current cart.
     * If the customer doesn't have one, saves an empty cart as his current and returns it.
     * @return the customer's current cart.
     */
    private Cart getCurrentCart(boolean withMeals) {
        Integer customerId = detail.getId();
        Optional<Cart> optionalCart = withMeals ? cartRepo.findCurrentWithMeals(customerId) :
                cartRepo.findByCustomerIdAndCurrentTrue(customerId);
        if(optionalCart.isPresent()) {
            return optionalCart.get();
        }
        return cartRepo.save(new Cart(true, new Customer(customerId, detail.getName())));
    }

    public CartDTO getCart() {
        return DtoMapper.cartToDto(getCurrentCart(true));
    }

    public String updateCartComment(String comment) {
        Cart currentCart = getCurrentCart(false);
        currentCart.setComment(comment);
        cartRepo.save(currentCart);
        return comment;
    }

    public void buyCart() {
        Cart currentCart = getCurrentCart(false);
        currentCart.setCurrent(false);
        cartRepo.save(currentCart);
    }

    /**
     * Saves the mealDto as chosenMeal, turns the new chosenMeal to cartMeal and returns it.
     * @param mealDTO - meal to add to cart
     * @return the added meal as CartMealDto
     */
    public CartMealDTO addToCart(MealDTO mealDTO) {
        Optional<Meal> optionalMeal = mealRepo.findById(mealDTO.getId());
        if(optionalMeal.isEmpty()) {
            throw new NotFoundException("meal");
        }
        Meal meal = optionalMeal.get();
        Cart currentCart = getCurrentCart(false);

        ChosenMeal chosenMeal = chosenMealRepo.save(
                new ChosenMeal(DtoMapper.choiceDtosToOptions(mealDTO.getChoices()), meal.getImg(), mealDTO.getPrice(),
                        mealDTO.getQuantity(), mealDTO.getPrice() * mealDTO.getQuantity(), meal, currentCart));

        return DtoMapper.chosenMealToCartMeal(chosenMeal);
    }

    public void deleteFromCart(Integer mealId) {
        chosenMealRepo.deleteByIdAndCart(mealId, getCurrentCart(false));
    }

    public void clearCart() {
        Cart currentCart = getCurrentCart(false);
        chosenMealRepo.deleteByCart(currentCart);
        currentCart.defaultValues();
        cartRepo.save(currentCart);
    }

    /**
     * Creates a login response if the info is correct and the email is not blocked.
     * Blocked emails are ones that have failed to log in at least 10 times in the last 30 minutes and are blocked for 30 minutes since the 10th time.
     * If the email exists but the password is incorrect (a failed login attempt)-saves the failed attempt, as well as if it blocks the email.
     * @param email
     * @param password
     * @return the login response (optional) if the email is not blocked and the info is correct
     */
    public Optional<LoginResponse> login(String email, String password) {
        return loginLogic(email, password, LocalDateTime.now());
    }

    public Optional<LoginResponse> login(String email, String password, LocalDateTime now) {
        return loginLogic(email, password, now);
    }

    private Optional<Customer> getValidCustomer(String email, LocalDateTime now) {
        Optional<Customer> optionalCustomer = customerRepo.findByEmail(email);
        if(optionalCustomer.isPresent()) {
            long attemptCount = attemptRepo.countByMailInTime(email, now.minusMinutes(ATTEMPT_MINUTES), now);
            if(attemptCount>=ALLOWED_ATTEMPTS) {
                emailSender.messageAdmin("Blocked user attempts to login", "User with email: "+email
                        +" has made more than "+ALLOWED_ATTEMPTS+" failed login attempts in the last "+ATTEMPT_MINUTES+" minutes.");
                throw new AccountBlockedException();
            }else {
                return optionalCustomer;
            }
        }
        return Optional.empty();
    }

    private Optional<LoginResponse> loginLogic(String email, String password, LocalDateTime now) {
        Optional<Customer> validCustomer = getValidCustomer(email, now);
        if (validCustomer.isPresent()) {
            Customer customer = validCustomer.get();
            if (passwordEncoder.matches(password, customer.getPassword())) {
                return Optional.of(LoginResponseMaker.make(customer));
            }else {
                attemptRepo.save(new LoginAttempt(email, now));
            }
        }
        return Optional.empty();
    }

    public LoginResponse signup(RegisterInfo info) {
        LoginInfo loginInfo = info.getLoginInfo();
        Customer customer = customerRepo.save(new Customer(info.getName(), loginInfo.getEmail()
                , passwordEncoder.encode(loginInfo.getPassword())));
        return LoginResponseMaker.make(customer);
    }

    public List<CartDTO> getHistory() {
        return cartRepo.findByCustomerIdAndCurrentFalse(detail.getId()).stream().map(DtoMapper::cartToDto).collect(
                Collectors.toList());
    }
}
