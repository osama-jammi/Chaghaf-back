package ma.chaghaf.notification.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class NotificationDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SendNotificationRequest {
        @NotNull  private Long userId;
        @NotBlank private String title;
        @NotBlank private String body;
        @NotBlank private String type;
        private String referenceId;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class BroadcastRequest {
        @NotBlank private String title;
        @NotBlank private String body;
        @NotBlank private String type;
        private List<Long> userIds;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class NotificationResponse {
        private Long id;
        private Long userId;
        private String title;
        private String body;
        private String type;
        private Boolean sent;
        private Boolean read;
        private String referenceId;
        private LocalDateTime createdAt;
    }
}
