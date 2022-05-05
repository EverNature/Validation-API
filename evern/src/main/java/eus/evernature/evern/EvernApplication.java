package eus.evernature.evern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvernApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvernApplication.class, args);
		System.out.println(org.hibernate.Version.getVersionString());
	}

}
