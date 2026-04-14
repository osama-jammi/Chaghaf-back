package ma.chaghaf.snack.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "snack_orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SnackOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private Long userId;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal total;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30)
    @Builder.Default private Status status = Status.RECEIVED;
    @Column(length = 500) private String note;
    @Column(length = 20) private String orderRef;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> items;
    @CreationTimestamp private LocalDateTime createdAt;

    public enum Status { RECEIVED, PREPARING, ON_THE_WAY, DELIVERED, CANCELLED }
}
