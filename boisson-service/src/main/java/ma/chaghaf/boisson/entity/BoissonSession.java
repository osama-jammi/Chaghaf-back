package ma.chaghaf.boisson.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "boisson_sessions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class BoissonSession {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private Long userId;
    @Column(nullable = false, length = 50) private String boissonType;
    @Column(nullable = false, length = 50) @Builder.Default private String status = "CONSUMED";
    @CreationTimestamp private LocalDateTime consumedAt;
}
