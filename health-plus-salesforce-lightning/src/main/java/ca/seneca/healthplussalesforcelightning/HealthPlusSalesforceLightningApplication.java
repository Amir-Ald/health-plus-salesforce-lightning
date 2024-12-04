package ca.seneca.healthplussalesforcelightning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "ca.seneca.healthplussalesforcelightning.*")
@ComponentScan(basePackages = { "ca.seneca.healthplussalesforcelightning.*" })
@EntityScan("ca.seneca.healthplussalesforcelightning.*")
public class HealthPlusSalesforceLightningApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthPlusSalesforceLightningApplication.class, args);
	}

}
