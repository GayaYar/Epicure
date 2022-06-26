package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.moveo.epicure.aws.EmailSender;
import com.moveo.epicure.dto.LoginResponse;
import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.entity.LoginAttempt;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.UserType;
import com.moveo.epicure.exception.AccountBlockedException;
import com.moveo.epicure.exception.AlreadyExistsException;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.mock.MockUser;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.UserRepo;
import com.moveo.epicure.util.LoginResponseMaker;
import java.time.Duration;
import java.time.LocalDateTime;
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
public class UserServiceTest {
    private UserService service;
    @Mock
    private UserRepo userRepo;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AttemptRepo attemptRepo;
    @Mock
    private LoginResponseMaker loginResponseMaker;
    @Mock
    private EmailSender emailSender;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    @Captor
    private ArgumentCaptor<LoginAttempt> attemptArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    private LocalDateTime now;

    @BeforeEach
    void initialiseTest() {
        service = new UserService(userRepo, passwordEncoder, attemptRepo, loginResponseMaker, emailSender);
        now = LocalDateTime.now();
    }

    @Test
    void loginReturnsEmptyWhenEmailDoesNotExist() {
        Mockito.when(userRepo.findByEmail("notexisting@mail.com")).thenReturn(Optional.empty());
        assertEquals(service.login("notexisting@mail.com", "a-password", now), Optional.empty());
    }

    @Test
    void loginWhenBlockedThrowsExceptionAndDoesNotSaveAttemptAndAlertsAdmin() {
        String email = "blocked@mail.com";
        String password = "a-password";
        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(new User(2, "blocker", email, password, UserType.CUSTOMER)));
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

//    @Test
//    void loginSuccessful() {
//        String email = "mockCus@gmail.com";
//        String password = "12345678";
//        Mockito.when(attemptRepo.countByMailInTime(email, now.minusMinutes(30), now))
//                .thenReturn(2l);
//        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(true);
//        Mockito.when(userRepo.findByEmail(email))
//                .thenReturn(Optional.of(new User(5, "mocky", email, password, UserType.CUSTOMER)));
//        assertEquals(service.login(email, password, now), Optional.of(MockUser.getMockResponse()));
//    }

    @Test
    void loginFailedReturnsEmptyAndSavesAttempt() {
        String email = "mockCus@gmail.com";
        String password = "12345678";
        Mockito.when(attemptRepo.countByMailInTime(email, now.minusMinutes(30), now))
                .thenReturn(2l);
        Mockito.when(passwordEncoder.matches(password, password)).thenReturn(false);
        Mockito.when(userRepo.findByEmail(email)).thenReturn(Optional.of(new User(2, "mock cus", email, password, UserType.CUSTOMER)));
        Optional<LoginResponse> response = service.login(email, password, now);
        Mockito.verify(attemptRepo, Mockito.times(1)).save(attemptArgumentCaptor.capture());
        LoginAttempt captorValue = attemptArgumentCaptor.getValue();
        LoginAttempt expected = new LoginAttempt(email, LocalDateTime.now());
        assertTrue(captorValue.getMail().equals(expected.getMail()) &&
                Duration.between(captorValue.getTime(), expected.getTime()).toMinutes() < 1);
        assertEquals(response, Optional.empty());
    }

    @Test
    void signUpThrowsExceptionWhenEmailExists() {
        RegisterInfo registerInfo = new RegisterInfo("existy@customer.com", "passssss", "mocky");
        Mockito.when(userRepo.existsByEmail(registerInfo.getEmail())).thenReturn(true);
        assertThatThrownBy(()->{service.signup(registerInfo);}).isInstanceOf(AlreadyExistsException.class)
                .hasMessage("This email already exists in the system");
    }

    @Test
    void signUpSavesCustomerAndReturnsResponse() {
        RegisterInfo info = new RegisterInfo("mockCus@gmail.com", "12345678", "mocky");
        Mockito.when(userRepo.existsByEmail(info.getEmail())).thenReturn(false);
        Mockito.when(passwordEncoder.encode(info.getPassword())).thenReturn(info.getPassword());
        Mockito.when(userRepo.save(new User(info.getName(), info.getEmail(), info.getPassword(), UserType.CUSTOMER)))
                .thenReturn(new User(5, info.getName(), info.getEmail(), info.getPassword(), UserType.CUSTOMER));
        Mockito.when(loginResponseMaker.make(new User(5, info.getName(), info.getEmail(), info.getPassword(), UserType.CUSTOMER)))
                        .thenReturn(MockUser.getMockResponse());
        service.signup(info);
        Mockito.verify(userRepo, Mockito.times(1)).save(userArgumentCaptor.capture());
        assertEquals(new User(info.getName(), info.getEmail(), info.getPassword(), UserType.CUSTOMER), userArgumentCaptor.getValue());
        assertEquals(service.signup(info), MockUser.getMockResponse());
    }

    @Test
    void saveAdminThrowsExceptionWhenEmailExists() {
        Mockito.when(userRepo.existsByEmail("exister@mail.com")).thenReturn(true);
        assertThatThrownBy(()->{service.saveAdmin("exister@mail.com", "blah1234", "existy");})
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessage("This email already exists in the system");
    }

    @Test
    void saveAdminSavesAdmin() {
        String email = "admin@mail.com";
        String password = "passssta";
        String name = "adminer";
        Mockito.when(userRepo.existsByEmail(email)).thenReturn(false);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        service.saveAdmin(email, password, name);
        Mockito.verify(userRepo, Mockito.times(1)).save(userArgumentCaptor.capture());
        assertEquals(userArgumentCaptor.getValue(), new User(name, email, password, UserType.ADMIN));
    }
}
