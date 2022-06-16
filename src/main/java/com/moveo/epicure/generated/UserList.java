package com.moveo.epicure.generated;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class UserList {
    private List<GeneratedUser> users;

    public UserList() {
        this.users = new ArrayList<>();
    }
}
