package ma.chaghaf.subscription.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "day_accesses")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DayAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccessType accessType;   // HALF_DAY, FULL_DAY

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate accessDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean used = false;

    @Column(length = 500)
    private String qrToken;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum AccessType {
        HALF_DAY(new BigDecimal("20.00")),
        FULL_DAY(new BigDecimal("30.00"));

        private final BigDecimal price;
        AccessType(BigDecimal price) { this.price = price; }
        public BigDecimal getPrice() { return price; }
    }
}
