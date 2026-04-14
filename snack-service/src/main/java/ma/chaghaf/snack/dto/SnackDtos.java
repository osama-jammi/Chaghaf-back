package ma.chaghaf.snack.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SnackDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CreateOrderRequest {
        @NotEmpty private Map<String, Integer> items; // snackId -> quantity
        private String note;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class OrderItemResponse {
        private String snackId;
        private String snackName;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal lineTotal;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class OrderResponse {
        private Long id;
        private Long userId;
        private String orderRef;
        private BigDecimal total;
        private String status;
        private String note;
        private List<OrderItemResponse> items;
        private LocalDateTime createdAt;
        private String estimatedDelivery;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SnackInfo {
        private String id;
        private String name;
        private String emoji;
        private BigDecimal price;
        private String category;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class UpdateStatusRequest {
        @NotBlank private String status;
    }
}
