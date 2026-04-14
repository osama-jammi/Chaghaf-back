package ma.chaghaf.social.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.chaghaf.social.dto.SocialDtos.*;
import ma.chaghaf.social.service.SocialService;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequiredArgsConstructor
public class SocialController {
    private final SocialService service;

    @GetMapping("/api/posts")
    public ResponseEntity<Page<PostResponse>> getPosts(
        @RequestHeader(value = "X-User-Id", required = false) Long uid,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getPosts(uid, page, size));
    }

    @PostMapping("/api/posts")
    public ResponseEntity<PostResponse> createPost(
        @RequestHeader("X-User-Id") Long uid,
        @RequestHeader(value = "X-User-Name", defaultValue = "Membre") String name,
        @RequestHeader(value = "X-User-Avatar", defaultValue = "?") String avatar,
        @RequestHeader(value = "X-User-Role", defaultValue = "MEMBER") String role,
        @Valid @RequestBody CreatePostRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createPost(uid, name, avatar, role, req));
    }

    @PostMapping("/api/posts/{id}/like")
    public ResponseEntity<PostResponse> toggleLike(
        @RequestHeader("X-User-Id") Long uid, @PathVariable Long id) {
        return ResponseEntity.ok(service.toggleLike(uid, id));
    }

    @PostMapping("/api/messages")
    public ResponseEntity<MessageResponse> sendMessage(
        @RequestHeader("X-User-Id") Long uid,
        @Valid @RequestBody SendMessageRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.sendMessage(uid, req));
    }

    @GetMapping("/api/messages/{otherId}")
    public ResponseEntity<List<MessageResponse>> getConversation(
        @RequestHeader("X-User-Id") Long uid, @PathVariable Long otherId) {
        return ResponseEntity.ok(service.getConversation(uid, otherId));
    }
}
