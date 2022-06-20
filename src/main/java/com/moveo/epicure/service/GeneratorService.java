package com.moveo.epicure.service;

import com.moveo.epicure.generated.GeneratedUser;
import com.moveo.epicure.generated.Name;
import com.moveo.epicure.generated.UserList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeneratorService {
    private RestTemplate restTemplate;
    private String userUrl;

    public GeneratorService() {
        this.restTemplate = new RestTemplate();
        userUrl = "https://randomuser.me/api/";
    }

    private void sortList(List<GeneratedUser> users) {
        users.sort((u1, u2) -> {
            Name name1 = getNameShortcut(u1);
            Name name2 = getNameShortcut(u2);
            int lastCompared = name1.getLast().compareTo(name2.getLast());
            return lastCompared == 0 ? name1.getFirst().compareTo(name2.getFirst()) : lastCompared;
        });
    }

    private Name getNameShortcut(GeneratedUser user) {
        return user.getResults().get(0).getName();
    }

    public List<GeneratedUser> generatedUsers(int amount) {
        List<GeneratedUser> users = restTemplate.getForObject(userUrl + "?results=" + amount, UserList.class)
                .getUsers();
        sortList(users);
        return users;
    }
}
