package com.moveo.epicure;

import com.moveo.epicure.entity.Permit;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.repo.PermitRepo;
import com.moveo.epicure.repo.UserRepo;
import com.moveo.epicure.service.UserService;
import java.util.Arrays;
import java.util.Optional;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EpicureApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EpicureApplication.class, args);
		PermitRepo permitRepo = context.getBean(PermitRepo.class);
		if(permitRepo.findAll().isEmpty()) {
			permitRepo.save(new Permit("ADMIN", Arrays.asList("addRestaurant")));
		}

		//saving admin for test
//		UserService userService = context.getBean(UserService.class);
//		if(userService.login("admin1@mail.com", "test-admin").isEmpty()) {
//			userService.saveAdmin("admin1@mail.com", "test-admin", "admin1");
//		}

	}

}
