package ma.chaghaf.social.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity @Table(name = "posts")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private Long authorId;
    @Column(nullable = false, length = 100) private String authorName;
    @Column(nullable = false, length = 5) private String authorAvatar;
    @Column(nullable = false, length = 20) @Builder.Default private String authorRole = "MEMBER";
    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)") private String content;
    @Builder.Default private Integer likesCount = 0;
    @Builder.Default private Integer commentsCount = 0;
    @Builder.Default private Boolean isOfficial = false;
    @Builder.Default private Boolean active = true;
    @CreationTimestamp private LocalDateTime createdAt;
}
