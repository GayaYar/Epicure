package com.moveo.epicure.mock;

import com.moveo.epicure.entity.LoginAttempt;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockCustomer {

    //necessary for limit login test
    public List<LoginAttempt> getBlockedAttempt() {
        List<LoginAttempt> attempts = new ArrayList<>(9);
        for(int i=1; i<=9; i++) {
            attempts.add(new LoginAttempt("blocked@mail.com", LocalDateTime.now().minusMinutes((long) i)));
        }
        return attempts;
    }
}
