package cm.amcloud.platform.notification.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Import for ArgumentCaptor
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions; // Import for ReflectionTestUtils
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit tests for the NotificationService class.
 * This class uses Mockito to isolate the NotificationService from its
 * JavaMailSender dependency and test its mail sending logic in isolation.
 */
@ExtendWith(MockitoExtension.class) // Integrates Mockito with JUnit 5
class NotificationServiceUnitTest {

    @Mock // Creates a mock instance of JavaMailSender.
          // This mock will simulate the behavior of sending emails without actually connecting to an SMTP server.
    private JavaMailSender mailSender;

    @InjectMocks // Injects the mock objects (mailSender) into this instance of
                 // NotificationService. This is the class under test.
    private NotificationService notificationService;

    // Reusable test data
    private final String SENDER_EMAIL = "test.sender@example.com";
    private final String RECIPIENT_EMAIL = "recipient@example.com";
    private final String SUBJECT = "Test Subject";
    private final String CONTENT = "This is a test notification content.";

    /**
     * Sets up common test data and mock behaviors before each test method runs.
     */
    @BeforeEach
    void setUp() {
        // Manually set the 'senderEmail' field using ReflectionTestUtils.
        // This is necessary because @Value injection does not occur in pure unit tests.
        ReflectionTestUtils.setField(notificationService, "senderEmail", SENDER_EMAIL);
    }

    /**
     * Tests the 'sendNotification' method for a successful email dispatch.
     * Verifies that a SimpleMailMessage is correctly constructed and
     * that JavaMailSender's send method is invoked with the correct message.
     */
    @Test
    void givenMailDetails_whenSendNotification_thenMailSenderIsCalledWithCorrectMessage() {
        // GIVEN:
        // No specific behavior needed from mailSender mock for this test, as we only verify its invocation.

        // WHEN:
        // Call the sendNotification method on the service.
        notificationService.sendNotification(RECIPIENT_EMAIL, SUBJECT, CONTENT);

        // THEN:
        // 1. Create an ArgumentCaptor to capture the SimpleMailMessage passed to mailSender.send().
        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        // 2. Verify that mailSender.send() was called exactly once and capture the message.
        verify(mailSender, times(1)).send(messageCaptor.capture());

        // 3. Get the captured message.
        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        // 4. Assert that the captured message has the correct properties.
        assertEquals(RECIPIENT_EMAIL, capturedMessage.getTo()[0]); // getTo() returns an array
        assertEquals(SUBJECT, capturedMessage.getSubject());
        assertEquals(CONTENT, capturedMessage.getText());
        assertEquals(SENDER_EMAIL, capturedMessage.getFrom());

        // Verify that no other interactions happened with mailSender (optional, but good for strictness)
        verifyNoMoreInteractions(mailSender);
    }

 }
