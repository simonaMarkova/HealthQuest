package mk.ukim.finki.healthquiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;

@SpringBootApplication
public class HealthQuizApplication extends WebMvcAutoConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(HealthQuizApplication.class, args);
	}
}
