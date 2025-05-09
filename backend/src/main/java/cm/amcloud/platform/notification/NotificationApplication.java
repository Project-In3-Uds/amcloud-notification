package cm.amcloud.platform.notification;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {

	public static void main(String[] args) {
		// Load .env file
		 Dotenv dotenv = Dotenv.configure()
		          .load();

		// Set environment variables
		 dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		SpringApplication.run(NotificationApplication.class, args);
	}

}
