package com.moveo.epicure;

import com.moveo.epicure.entity.Permit;
import com.moveo.epicure.repo.PermitRepo;
import java.util.Arrays;
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
	}

}
