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

	// Hola esto es una prueba

	/**
	 * @param expertService The service that will be used to save the user.
	 * @param roleService   The service that will be used to save the roles.
	 * @return A CommandLineRunner object.
	 */
	// @Bean
	// CommandLineRunner run(ExpertService expertService, RoleService roleService) {
	// 	return args -> {

	// 		expertService.saveUser(new Expert(null, null, null, null, "izanarcos", "izan", "arcos", "izanarcos",
	// 				"izan.arcos@gmail.com", "MU", "655699964", "+34", null, null));
	// 	};
	// }
}
