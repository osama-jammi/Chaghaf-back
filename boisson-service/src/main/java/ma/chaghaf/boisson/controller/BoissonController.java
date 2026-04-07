package ma.chaghaf.boisson.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.chaghaf.boisson.dto.BoissonDtos.*;
import ma.chaghaf.boisson.service.BoissonService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/boissons") @RequiredArgsConstructor
public class BoissonController {
    private final BoissonService service;

    @GetMapping
    public ResponseEntity<List<BoissonInfo>> getBoissons() {
        return ResponseEntity.ok(service.getAvailableBoissons());
    }

    @PostMapping("/consume")
    public ResponseEntity<BoissonSessionResponse> consume(@RequestHeader("X-User-Id") Long uid,
        @Valid @RequestBody ConsumeBoissonRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.consume(uid, req));
    }

    @GetMapping("/history")
    public ResponseEntity<List<BoissonSessionResponse>> history(@RequestHeader("X-User-Id") Long uid) {
        return ResponseEntity.ok(service.getHistory(uid));
    }

    @GetMapping("/cafe-guide")
    public ResponseEntity<List<CafeGuideStep>> cafeGuide() {
        return ResponseEntity.ok(service.getCafeGuide());
    }
}
