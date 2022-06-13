package com.moveo.epicure.controller;

import com.moveo.epicure.generated.GeneratedUser;
import com.moveo.epicure.generated.Name;
import com.moveo.epicure.generated.UserList;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("generator")
public class GeneratorController {

    private RestTemplate restTemplate;
    private String userUrl;

    public GeneratorController() {
        this.restTemplate = new RestTemplate();
        userUrl = "https://randomuser.me/api/";
    }

    @GetMapping
    public ResponseEntity<List<GeneratedUser>> generate(@RequestParam @Min(1) @Max(15) int amount) {
        List<GeneratedUser> users = restTemplate.getForObject(userUrl + "?results=" + amount, UserList.class)
                .getUsers();
        sortList(users);
        return ResponseEntity.ok(users);
    }

    private Name getNameShortcut(GeneratedUser user) {
        return user.getResults().get(0).getName();
    }

    private void sortList(List<GeneratedUser> users) {
        users.sort((u1, u2) -> {
            Name name1 = getNameShortcut(u1);
            Name name2 = getNameShortcut(u2);
            int lastCompared = name1.getLast().compareTo(name2.getLast());
            return lastCompared == 0 ? name1.getFirst().compareTo(name2.getFirst()) : lastCompared;
        });
    }
}
