package ma.chaghaf.subscription.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SubscriptionDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SubscribeRequest {
        @NotBlank private String packType;   // ONE_PERSON | TWO_PERSONS | THREE_PERSONS
        @NotBlank private String duration;   // MONTHLY | ANNUAL
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class DayAccessRequest {
        @NotBlank private String accessType; // HALF_DAY | FULL_DAY
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SubscriptionResponse {
        private Long id;
        private Long userId;
        private String packType;
        private String duration;
        private String status;
        private BigDecimal price;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer personsCount;
        private Long daysLeft;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class DayAccessResponse {
        private Long id;
        private Long userId;
        private String accessType;
        private BigDecimal price;
        private LocalDate accessDate;
        private Boolean used;
        private String qrToken;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class PackInfo {
        private String id;
        private String name;
        private BigDecimal monthlyPrice;
        private BigDecimal annualPrice;
        private Integer personsCount;
        private java.util.List<String> features;
        private Boolean popular;
    }
}
