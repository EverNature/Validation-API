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
	 * @param roleService The service that will be used to save the roles.
	 * @return A CommandLineRunner object.
	 */
	// @Bean
	// CommandLineRunner run(ExpertService expertService, RoleService roleService) {
	// 	return args -> {

	// 		for(int i = 0; 1 < 100; i++) {
	// 			byte[] array = new byte[7]; // length is bounded by 7
	// 			new Random().nextBytes(array);
	// 			String generatedString = new String(array, Charset.forName("UTF-8"));

	// 			expertService.saveUser(new Expert(null, null, null, null, "izanarcos", "izan", "arcos", "izanarcos", "izan.arcos@gmail.com", "MU", "655699964", "+34", null, null));
	// 		}


	// 		expertService.addRoleToUser("izanarcos", "ROLE_SUPER_ADMIN");
	// 		expertService.addRoleToUser("aritzdomaika", "ROLE_USER");
	// 		expertService.addRoleToUser("jorgedasivla", "ROLE_MANAGER");
	// 	};
	// }
}
