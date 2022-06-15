package com.moveo.epicure;

import com.moveo.epicure.entity.Permit;
import com.moveo.epicure.entity.User;
import com.moveo.epicure.repo.PermitRepo;
import com.moveo.epicure.repo.UserRepo;
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
		UserRepo userRepo = context.getBean(UserRepo.class);
		Optional<User> userOptional = userRepo.findByEmailAndPassword("admin@mail.com", "test-admin");
		if(!(userOptional.isPresent() && userOptional.get().getUserType().equals("ADMIN"))){
			userRepo.save(new User("admin", "admin@mail.com", "test-admin", "ADMIN"));
		}
	}

}
