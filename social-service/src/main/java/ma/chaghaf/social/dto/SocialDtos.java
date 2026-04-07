package ma.chaghaf.social.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

public class SocialDtos {

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class CreatePostRequest {
        @NotBlank private String content;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class PostResponse {
        private Long id;
        private Long authorId;
        private String authorName;
        private String authorAvatar;
        private String authorRole;
        private String content;
        private Integer likesCount;
        private Integer commentsCount;
        private Boolean isOfficial;
        private Boolean likedByCurrentUser;
        private String timeAgo;
        private LocalDateTime createdAt;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class SendMessageRequest {
        @NotBlank private String content;
        private Long recipientId;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class MessageResponse {
        private Long id;
        private Long senderId;
        private Long recipientId;
        private String content;
        private Boolean isRead;
        private String sender;
        private LocalDateTime sentAt;
    }

    @Data @NoArgsConstructor @AllArgsConstructor
    public static class ConversationSummary {
        private String conversationKey;
        private Long otherUserId;
        private String otherUserName;
        private String otherUserAvatar;
        private String lastMessage;
        private LocalDateTime lastMessageAt;
        private long unreadCount;
    }
}
