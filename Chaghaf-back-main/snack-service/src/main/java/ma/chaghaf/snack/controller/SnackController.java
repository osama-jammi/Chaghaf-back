package ma.chaghaf.snack.controller;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.chaghaf.snack.dto.SnackDtos.*;
import ma.chaghaf.snack.service.SnackService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController @RequestMapping("/api/snacks") @RequiredArgsConstructor
public class SnackController {
    private final SnackService service;

    @GetMapping("/catalog")
    public ResponseEntity<List<SnackInfo>> getCatalog() {
        return ResponseEntity.ok(service.getCatalog());
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderResponse> createOrder(@RequestHeader("X-User-Id") Long uid,
        @Valid @RequestBody CreateOrderRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(uid, req));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@RequestHeader("X-User-Id") Long uid) {
        return ResponseEntity.ok(service.getUserOrders(uid));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id,
        @RequestBody UpdateStatusRequest req) {
        return ResponseEntity.ok(service.updateStatus(id, req.getStatus()));
    }
}
