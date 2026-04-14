package ma.chaghaf.notification.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "notifications")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private Long userId;
    @Column(nullable = false, length = 100) private String title;
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)") private String body;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30)
    private NotificationType type;
    @Builder.Default private Boolean sent = false;
    @Builder.Default private Boolean read = false;
    private String referenceId;
    @CreationTimestamp private LocalDateTime createdAt;

    public enum NotificationType {
        POST_LIKE, NEW_MESSAGE, RESERVATION_CONFIRMED, RESERVATION_REMINDER,
        ORDER_UPDATE, SUBSCRIPTION_EXPIRING, SUBSCRIPTION_EXPIRED, SYSTEM
    }
}
