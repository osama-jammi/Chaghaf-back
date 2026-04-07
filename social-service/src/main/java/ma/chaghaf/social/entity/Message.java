package ma.chaghaf.social.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "messages")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private Long senderId;
    @Column(nullable = false) private Long recipientId;
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)") private String content;
    @Builder.Default private Boolean isRead = false;
    @Column(length = 100) private String conversationKey;
    @CreationTimestamp private LocalDateTime sentAt;
}
