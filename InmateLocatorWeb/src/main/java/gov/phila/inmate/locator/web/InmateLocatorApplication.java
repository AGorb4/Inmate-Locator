package gov.phila.inmate.locator.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "gov.phila.inmate")
public class InmateLocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmateLocatorApplication.class, args);
	}

}
