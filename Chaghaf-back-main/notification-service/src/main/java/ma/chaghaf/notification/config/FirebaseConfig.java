package ma.chaghaf.notification.config;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j @Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials-path:classpath:firebase-service-account.json}")
    private String credentialsPath;

    @PostConstruct
    public void initFirebase() {
        try {
            InputStream serviceAccount;
            if (credentialsPath.startsWith("classpath:")) {
                String cp = credentialsPath.substring(10);
                serviceAccount = getClass().getClassLoader().getResourceAsStream(cp);
            } else {
                serviceAccount = Files.newInputStream(Paths.get(credentialsPath));
            }

            if (serviceAccount == null) {
                log.warn("Firebase credentials not found at {}. Push notifications disabled.", credentialsPath);
                return;
            }

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
                FirebaseApp.initializeApp(options);
                log.info("Firebase initialized successfully");
            }
        } catch (Exception e) {
            log.warn("Firebase init failed (push notifications disabled): {}", e.getMessage());
        }
    }
}
