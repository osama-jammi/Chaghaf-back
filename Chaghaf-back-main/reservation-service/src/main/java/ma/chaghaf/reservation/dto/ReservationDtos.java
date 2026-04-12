package ma.chaghaf.reservation.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReservationDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CreateReservationRequest {
        @NotBlank private String salleId;
        @NotNull  private LocalDate reservationDate;
        @NotBlank private String duration; // HALF_DAY | FULL_DAY
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ReservationResponse {
        private Long id;
        private Long userId;
        private String salleId;
        private String salleName;
        private LocalDate reservationDate;
        private String duration;
        private BigDecimal price;
        private String status;
        private LocalDateTime createdAt;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SalleInfo {
        private String id;
        private String name;
        private String emoji;
        private String capacity;
        private List<String> features;
        private BigDecimal halfDayPrice;
        private BigDecimal fullDayPrice;
    }
}
