package cm.amcloud.platform.notification.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader; // Import added
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cm.amcloud.platform.notification.dto.NotificationRequest;
import cm.amcloud.platform.notification.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public String send(@RequestBody NotificationRequest request,
                       @RequestHeader(name = "X-User-ID", required = false) String userId,
                       @RequestHeader(name = "X-User-Roles", required = false) String userRoles,
                       @RequestHeader(name = "X-User-Scopes", required = false) String userScopes) {
        System.out.println("User ID: " + userId + ", Roles: " + userRoles + ", Scopes: " + userScopes + " is sending a notification.");
        notificationService.sendNotification(request.getTo(), request.getSubject(), request.getContent());
        return "Notification sent!";
    }
}
