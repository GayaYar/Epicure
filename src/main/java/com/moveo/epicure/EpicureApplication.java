package com.moveo.epicure;

import com.moveo.epicure.repo.PermitRepo;
import com.moveo.epicure.repo.PermittedMethodRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class EpicureApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(EpicureApplication.class, args);
		PermitRepo permitRepo = context.getBean(PermitRepo.class);
		PermittedMethodRepo permittedMethodRepo = context.getBean(PermittedMethodRepo.class);
//		if(permitRepo.findAll().isEmpty()) {
//			PermittedMethod permittedMethod = permittedMethodRepo.findAll().isEmpty() ?
//					permittedMethodRepo.save(new PermittedMethod("add this part")) :
//					permittedMethodRepo.findById()
//		if(permittedMethodRepo.findAll().isEmpty()) {
//			permittedMethod = permittedMethodRepo.save(new PermittedMethod("add this part"));
//		}
//			Set<PermittedMethod> methods = new HashSet<>(1);
//			methods.add(new PermittedMethod("add this part"));
//			permitRepo.save(new Permit(PermittedType.ADMIN, methods));
//		}
	}

}
