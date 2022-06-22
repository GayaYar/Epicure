package com.moveo.epicure.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.moveo.epicure.dto.RegisterInfo;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.entity.UserType;
import com.moveo.epicure.exception.AlreadyExistsException;
import com.moveo.epicure.exception.NotFoundException;
import com.moveo.epicure.mock.MockUser;
import com.moveo.epicure.repo.AttemptRepo;
import com.moveo.epicure.repo.UserRepo;
import com.moveo.epicure.util.LoginResponseMaker;
import java.time.LocalDateTime;
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
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;
    private LocalDateTime now;

    @BeforeEach
    void initialiseTest() {
        service = new UserService(userRepo, passwordEncoder, attemptRepo, loginResponseMaker);
        now = LocalDateTime.now();
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
