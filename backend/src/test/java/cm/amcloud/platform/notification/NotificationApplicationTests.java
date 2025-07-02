package cm.amcloud.platform.notification;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource; 

/**
 * Integration test for the Spring Boot Notification application context loading.
 * This test verifies that the application's Spring context loads successfully
 * with all its configurations, without requiring a real SMTP server
 * or a Spring Cloud Config Server during test execution.
 * All configuration properties are provided via @TestPropertySource
 * to simulate a complete deployment environment.
 *
 * It also includes a @TestConfiguration to mock the JavaMailSender bean,
 * preventing actual emails from being sent during the test.
 */
@SpringBootTest // Loads the full Spring application context.
                // Essential for integration tests that require Spring beans to be started.
@TestPropertySource(properties = {
    // --- Mail Configuration ---
    // Provides dummy values for mail server properties.
    // We mock JavaMailSender to prevent actual email sending.
    "spring.mail.host=smtp.test.com", // Dummy host
    "spring.mail.port=587",           // Dummy port
    "spring.mail.username=test.user@example.com", // Dummy username
    "spring.mail.password=test_password", // Dummy password
    "spring.mail.properties.mail.smtp.auth=false", // Disable SMTP authentication for testing
    "spring.mail.properties.mail.smtp.starttls.enable=false", // Disable STARTTLS for testing

    // --- Server Port Configuration ---
    "server.port=8085", // Dummy server port for the test context

    // --- Spring Cloud Config Server URL ---
    // If your application connects to a Spring Cloud Config Server.
    "spring.cloud.config.uri=http://localhost:8888", 
    "spring.cloud.config.enabled=false" 
})
@Import(NotificationApplicationTests.TestMailConfig.class) 
class NotificationApplicationTests {

    /**
     * Nested @TestConfiguration class to provide a mock JavaMailSender bean.
     * This prevents the application from attempting to connect to a real SMTP server
     * during the integration test, allowing the context to load without external dependencies.
     */
    @TestConfiguration
    static class TestMailConfig {
        /**
         * Defines a mock JavaMailSender bean.
         * This bean will override any real JavaMailSender bean that Spring Boot
         * might auto-configure based on mail properties.
         * It ensures that no actual emails are sent during the test execution.
         * @return A mock JavaMailSender instance.
         */
        @Bean
        public JavaMailSender javaMailSender() {
            return mock(JavaMailSender.class); 
        }
    }

    /**
     * This test method simply verifies that the Spring application context
     * loads successfully without any errors. It serves as a basic sanity check
     * for the application's overall configuration and bean wiring within a test environment.
     * No specific assertions are needed here, as the test passes if the context loads without exceptions.
     */
    @Test
    void contextLoads() {
        // The test passes if the application context loads without throwing exceptions.
        // This confirms that all required properties are resolved and beans can be created.
    }

}
