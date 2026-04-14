package ma.chaghaf.boisson.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class BoissonDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ConsumeBoissonRequest {
        @NotBlank private String boissonType; // CAFE, ICE_COFFEE, THE, EAU
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BoissonSessionResponse {
        private Long id;
        private Long userId;
        private String boissonType;
        private String status;
        private LocalDateTime consumedAt;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BoissonInfo {
        private String id;
        private String name;
        private String emoji;
        private boolean included;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CafeGuideStep {
        private int step;
        private String text;
    }
}
