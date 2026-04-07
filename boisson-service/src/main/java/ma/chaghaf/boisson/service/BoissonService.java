package ma.chaghaf.boisson.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.chaghaf.boisson.dto.BoissonDtos.*;
import ma.chaghaf.boisson.entity.BoissonSession;
import ma.chaghaf.boisson.repository.BoissonSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j @Service @RequiredArgsConstructor
public class BoissonService {
    private final BoissonSessionRepository repo;
    private static final int MAX_BOISSONS_PER_DAY = 1;

    @Transactional
    public BoissonSessionResponse consume(Long userId, ConsumeBoissonRequest req) {
        LocalDateTime startOfDay = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime endOfDay   = startOfDay.plusDays(1);
        long count = repo.countByUserIdAndDateRange(userId, startOfDay, endOfDay);
        if (count >= MAX_BOISSONS_PER_DAY)
            throw new IllegalStateException("Daily boisson limit reached (1 per session)");

        BoissonSession session = BoissonSession.builder()
            .userId(userId).boissonType(req.getBoissonType().toUpperCase()).status("CONSUMED").build();
        session = repo.save(session);
        log.info("User {} consumed {}", userId, req.getBoissonType());
        return toResponse(session);
    }

    public List<BoissonSessionResponse> getHistory(Long userId) {
        return repo.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    public List<BoissonInfo> getAvailableBoissons() {
        return List.of(
            new BoissonInfo("b1", "Café Chaud", "☕", true),
            new BoissonInfo("b2", "Ice Coffee", "🧊", true),
            new BoissonInfo("b3", "Thé", "🍵", true),
            new BoissonInfo("b4", "Eau", "💧", true)
        );
    }

    public List<CafeGuideStep> getCafeGuide() {
        return List.of(
            new CafeGuideStep(1, "Vérifier le niveau d'eau (voyant bleu)"),
            new CafeGuideStep(2, "Insérer la capsule dans le compartiment"),
            new CafeGuideStep(3, "Placer ta tasse sous le bec verseur"),
            new CafeGuideStep(4, "Choisir taille (court · lungo · grand)"),
            new CafeGuideStep(5, "Appuyer → attendre 30 secondes ☕")
        );
    }

    private BoissonSessionResponse toResponse(BoissonSession s) {
        return new BoissonSessionResponse(s.getId(), s.getUserId(), s.getBoissonType(), s.getStatus(), s.getConsumedAt());
    }
}
