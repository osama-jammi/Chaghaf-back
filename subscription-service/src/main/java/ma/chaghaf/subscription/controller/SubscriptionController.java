package ma.chaghaf.subscription.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.chaghaf.subscription.dto.SubscriptionDtos.*;
import ma.chaghaf.subscription.entity.Subscription;
import ma.chaghaf.subscription.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService service;

    @GetMapping("/active")
    public ResponseEntity<SubscriptionResponse> getActive(
        @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.getActiveSubscription(userId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<SubscriptionResponse>> getHistory(
        @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.getUserHistory(userId));
    }

    @PostMapping
    public ResponseEntity<SubscriptionResponse> subscribe(
        @RequestHeader("X-User-Id") Long userId,
        @Valid @RequestBody SubscribeRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.subscribe(userId, req));
    }

    @PostMapping("/renew")
    public ResponseEntity<SubscriptionResponse> renew(
        @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.renew(userId));
    }

    @PostMapping("/day-access")
    public ResponseEntity<DayAccessResponse> purchaseDayAccess(
        @RequestHeader("X-User-Id") Long userId,
        @Valid @RequestBody DayAccessRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.purchaseDayAccess(userId, req));
    }

    @GetMapping("/packs")
    public ResponseEntity<List<PackInfo>> getAvailablePacks() {
        List<PackInfo> packs = List.of(
            new PackInfo("ONE_PERSON", "1 Personne",
                new BigDecimal("350.00"), new BigDecimal("3500.00"),
                1, List.of("Accès illimité", "1 boisson/session", "WiFi premium"), false),
            new PackInfo("TWO_PERSONS", "2 Personnes",
                new BigDecimal("600.00"), new BigDecimal("6000.00"),
                2, List.of("Accès illimité", "2 boissons/session", "WiFi premium", "Réservations prioritaires"), true),
            new PackInfo("THREE_PERSONS", "3 Personnes",
                new BigDecimal("800.00"), new BigDecimal("8000.00"),
                3, List.of("Accès illimité", "3 boissons/session", "WiFi premium", "Réservations prioritaires", "Salle podcast 1h/mois"), false)
        );
        return ResponseEntity.ok(packs);
    }
}
