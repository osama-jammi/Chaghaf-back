package ma.chaghaf.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class AuthDtos {

    // ── Register Request ────────────────────────────────────────
    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Full name is required")
        @Size(min = 2, max = 100)
        private String fullName;

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        private String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;

        private String phone;
        private String fcmToken;
    }

    // ── Login Request ───────────────────────────────────────────
    @Data
    public static class LoginRequest {
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;

        private String fcmToken;
    }

    // ── Auth Response ───────────────────────────────────────────
    @Data
    public static class AuthResponse {
        private Long userId;
        private String fullName;
        private String email;
        private String phone;
        private String avatarLetter;
        private String role;
        private String accessToken;
        private String refreshToken;
        private long expiresIn;

        public AuthResponse(Long userId, String fullName, String email, String phone,
                           String avatarLetter, String role,
                           String accessToken, String refreshToken, long expiresIn) {
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.avatarLetter = avatarLetter;
            this.role = role;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
            this.expiresIn = expiresIn;
        }
    }

    // ── Refresh Token Request ────────────────────────────────────
    @Data
    public static class RefreshRequest {
        @NotBlank
        private String refreshToken;
    }

    // ── Update FCM Token ─────────────────────────────────────────
    @Data
    public static class FcmTokenRequest {
        @NotBlank
        private String fcmToken;
    }

    // ── User Profile ─────────────────────────────────────────────
    @Data
    public static class UserProfileResponse {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private String avatarLetter;
        private String role;
        private Boolean active;
        private String memberSince;

        public UserProfileResponse(Long id, String fullName, String email, String phone,
                                   String avatarLetter, String role, Boolean active, String memberSince) {
            this.id = id;
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.avatarLetter = avatarLetter;
            this.role = role;
            this.active = active;
            this.memberSince = memberSince;
        }
    }
}
