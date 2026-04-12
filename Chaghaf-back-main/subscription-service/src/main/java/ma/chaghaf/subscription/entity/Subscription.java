package ma.chaghaf.subscription.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "subscriptions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PackType packType;   // ONE_PERSON, TWO_PERSONS, THREE_PERSONS

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Duration duration;   // MONTHLY, ANNUAL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private Integer personsCount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum PackType {
        ONE_PERSON, TWO_PERSONS, THREE_PERSONS;

        public int getPersonsCount() {
            return switch (this) {
                case ONE_PERSON -> 1;
                case TWO_PERSONS -> 2;
                case THREE_PERSONS -> 3;
            };
        }

        public BigDecimal getMonthlyPrice() {
            return switch (this) {
                case ONE_PERSON -> new BigDecimal("350.00");
                case TWO_PERSONS -> new BigDecimal("600.00");
                case THREE_PERSONS -> new BigDecimal("800.00");
            };
        }

        public BigDecimal getAnnualPrice() {
            return switch (this) {
                case ONE_PERSON -> new BigDecimal("3500.00");
                case TWO_PERSONS -> new BigDecimal("6000.00");
                case THREE_PERSONS -> new BigDecimal("8000.00");
            };
        }
    }

    public enum Duration { MONTHLY, ANNUAL }
    public enum Status { ACTIVE, EXPIRED, CANCELLED, PENDING }

    public long getDaysLeft() {
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}
