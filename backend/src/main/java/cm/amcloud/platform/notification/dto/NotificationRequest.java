package cm.amcloud.platform.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class NotificationRequest {
    private String to;
    private String subject;
    private String content;
}
