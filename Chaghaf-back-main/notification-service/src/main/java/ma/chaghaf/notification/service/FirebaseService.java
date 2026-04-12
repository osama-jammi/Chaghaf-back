package ma.chaghaf.notification.service;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FirebaseService {

    public boolean sendPushNotification(String fcmToken, String title, String body) {
        if (fcmToken == null || fcmToken.isBlank()) {
            log.debug("No FCM token, skipping push");
            return false;
        }
        try {
            Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(com.google.firebase.messaging.Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build())
                .putData("click_action", "FLUTTER_NOTIFICATION_CLICK")
                .build();

            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Push sent: {}", response);
            return true;
        } catch (FirebaseMessagingException e) {
            log.error("Push failed: {}", e.getMessage());
            return false;
        }
    }
}
