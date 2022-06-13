package com.moveo.epicure.service;

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
import com.moveo.epicure.exception.AccountBlockedException;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.CartRepo;
import com.moveo.epicure.repo.ChosenMealRepo;
import com.moveo.epicure.repo.CustomerRepo;
import com.moveo.epicure.repo.MealRepo;
import com.moveo.epicure.util.DtoMapper;
import com.moveo.epicure.util.LoginResponseMaker;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerDetail detail;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private MealRepo mealRepo;
    @Autowired
    private ChosenMealRepo chosenMealRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AttemptRepo attemptRepo;

    /**
     * Gets the customer's current cart.
     * If the customer doesn't have one, saves an empty cart as his current and returns it.
     * @return the customer's current cart.
     */
    private Cart getCurrentCart(boolean withMeals) {
        Integer customerId = detail.getId();
        Optional<Cart> optionalCart = withMeals ? cartRepo.findByCustomerIdAndCurrentTrue(customerId) :
                cartRepo.findCurrentWithMeals(customerId);
        if(optionalCart.isPresent()) {
            return optionalCart.get();
        }
        return cartRepo.save(new Cart(true, new Customer(customerId)));
    }

    public CartDTO getCart() {
        return DtoMapper.cartToDto(getCurrentCart(true));
    }

    public CartDTO updateCartComment(String comment) {
        Cart currentCart = getCurrentCart(false);
        currentCart.setComment(comment);
        return DtoMapper.cartToDto(cartRepo.save(currentCart));
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
     * @param info
     * @return the login response (optional) if the email is not blocked and the info is correct
     */
    public Optional<LoginResponse> login(LoginInfo info) {
        String email = info.getEmail();
        LocalDateTime now = LocalDateTime.now();

        if (customerRepo.existsByEmail(email)) {
            if(accountIsBlocked(email, now)) {
                throw new AccountBlockedException();
            }
            Optional<Customer> optionalCustomer = customerRepo.findByEmailAndPassword(email
                    , passwordEncoder.encode(info.getPassword()));
            if (optionalCustomer.isPresent()) {
                return Optional.of(LoginResponseMaker.make(optionalCustomer.get()));
            }else {
                attemptRepo.save(new LoginAttempt(email, now));
            }
        }

        return Optional.empty();
    }

    private boolean accountIsBlocked(String email, LocalDateTime now) {
        List<LoginAttempt> last9attempts = attemptRepo.findTop9ByMailOrderByTimeDesc(email);
        return last9attempts.size()>=9 && Duration.between(last9attempts.get(8).getTime(), now).toMinutes()<30;
    }

    public LoginResponse signup(RegisterInfo info) {
        LoginInfo loginInfo = info.getLoginInfo();
        Customer customer = customerRepo.save(new Customer(info.getName(), loginInfo.getEmail()
                , passwordEncoder.encode(loginInfo.getPassword())));
        return LoginResponseMaker.make(customer);
    }
}
