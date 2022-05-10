package eus.evernature.evern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EvernApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvernApplication.class, args);
		System.out.println(org.hibernate.Version.getVersionString());
	}

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

	/** 
	 * @param expertService The service that will be used to save the user.
	 * @param roleService The service that will be used to save the roles.
	 * @return A CommandLineRunner object.
	 */
	// @Bean
	// CommandLineRunner run(ExpertService expertService, RoleService roleService) {
	// 	return args -> {
	// 		roleService.saveRole(new Role(null, "ROLE_USER"));
	// 		roleService.saveRole(new Role(null, "ROLE_MANAGER"));
	// 		roleService.saveRole(new Role(null, "ROLE_ADMIN"));
	// 		roleService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

	// 		expertService.saveUser(new Expert(null, "", null, null, null, "izanarcos", "izan", "arcos", "izanarcos", "izan.arcos@gmail.com", "MU", "655699964", "+34", null, null));
	// 		expertService.saveUser(new Expert(null, "", null, null, null, "aritzdomaika", "aritz", "domaika", "aritzdomaika", "aritz.domaika@gmail.com", "MU", "2948723984", "+23", null, null));
	// 		expertService.saveUser(new Expert(null, "", null, null, null, "jorgedasivla", "jorge", "dasilva", "jorgedasilva", "jorge.dasilva@gmail.com", "MU", "2342881712", "+34", null, null));

	// 		expertService.addRoleToUser("izanarcos", "ROLE_SUPER_ADMIN");
	// 		expertService.addRoleToUser("aritzdomaika", "ROLE_USER");
	// 		expertService.addRoleToUser("jorgedasivla", "ROLE_MANAGER");
	// 	};
	// }
}
