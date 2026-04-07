package ma.chaghaf.notification.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.chaghaf.notification.dto.NotificationDtos.*;
import ma.chaghaf.notification.entity.Notification;
import ma.chaghaf.notification.repository.NotificationRepository;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j @Service @RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository repo;
    private final FirebaseService firebase;

    @Transactional
    public NotificationResponse send(SendNotificationRequest req) {
        Notification n = Notification.builder()
            .userId(req.getUserId()).title(req.getTitle()).body(req.getBody())
            .type(Notification.NotificationType.valueOf(req.getType().toUpperCase()))
            .referenceId(req.getReferenceId()).build();
        n = repo.save(n);
        // Fire-and-forget push (FCM token lookup would come from auth-service in real scenario)
        log.info("Notification {} saved for user {}", n.getId(), req.getUserId());
        return toResponse(n);
    }

    @Transactional
    public void broadcast(BroadcastRequest req) {
        if (req.getUserIds() == null || req.getUserIds().isEmpty()) return;
        req.getUserIds().forEach(uid -> send(
            new SendNotificationRequest(uid, req.getTitle(), req.getBody(), req.getType(), null)));
    }

    public Page<NotificationResponse> getUserNotifications(Long userId, int page, int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return repo.findByUserIdOrderByCreatedAtDesc(userId, p).map(this::toResponse);
    }

    public long getUnreadCount(Long userId) {
        return repo.countByUserIdAndReadFalse(userId);
    }

    @Transactional
    public void markAllRead(Long userId) {
        repo.markAllReadByUserId(userId);
    }

    // Process pending notifications every 30s
    @Scheduled(fixedDelay = 30000)
    @Transactional
    public void processPendingNotifications() {
        List<Notification> pending = repo.findBySentFalseOrderByCreatedAt();
        if (pending.isEmpty()) return;
        log.debug("Processing {} pending notifications", pending.size());
        pending.forEach(n -> {
            n.setSent(true);
            repo.save(n);
        });
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(n.getId(), n.getUserId(), n.getTitle(), n.getBody(),
            n.getType().name(), n.getSent(), n.getRead(), n.getReferenceId(), n.getCreatedAt());
    }
}
