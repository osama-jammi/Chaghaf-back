package ma.chaghaf.notification.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.chaghaf.notification.dto.NotificationDtos.*;
import ma.chaghaf.notification.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/notifications") @RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;

    @GetMapping
    public ResponseEntity<Page<NotificationResponse>> getNotifications(
        @RequestHeader("X-User-Id") Long uid,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.getUserNotifications(uid, page, size));
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Map<String, Long>> unreadCount(@RequestHeader("X-User-Id") Long uid) {
        return ResponseEntity.ok(Map.of("count", service.getUnreadCount(uid)));
    }

    @PostMapping("/mark-read")
    public ResponseEntity<Map<String, String>> markAllRead(@RequestHeader("X-User-Id") Long uid) {
        service.markAllRead(uid);
        return ResponseEntity.ok(Map.of("message", "All notifications marked as read"));
    }

    @PostMapping("/send")
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody SendNotificationRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.send(req));
    }

    @PostMapping("/broadcast")
    public ResponseEntity<Map<String, String>> broadcast(@Valid @RequestBody BroadcastRequest req) {
        service.broadcast(req);
        return ResponseEntity.ok(Map.of("message", "Broadcast sent"));
    }
}
