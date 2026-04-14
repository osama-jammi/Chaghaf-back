package ma.chaghaf.snack.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.chaghaf.snack.dto.SnackDtos.*;
import ma.chaghaf.snack.entity.OrderItem;
import ma.chaghaf.snack.entity.SnackOrder;
import ma.chaghaf.snack.repository.SnackOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Slf4j @Service @RequiredArgsConstructor
public class SnackService {
    private final SnackOrderRepository repo;

    private static final Map<String, SnackInfo> SNACK_CATALOG = new LinkedHashMap<>();
    static {
        SNACK_CATALOG.put("sn1", new SnackInfo("sn1", "Tacos",     "🌮", new BigDecimal("25"), "Plats"));
        SNACK_CATALOG.put("sn2", new SnackInfo("sn2", "Sandwich",  "🥪", new BigDecimal("18"), "Plats"));
        SNACK_CATALOG.put("sn3", new SnackInfo("sn3", "Frites",    "🍟", new BigDecimal("12"), "Accompagnements"));
        SNACK_CATALOG.put("sn4", new SnackInfo("sn4", "Salade",    "🥗", new BigDecimal("22"), "Plats"));
        SNACK_CATALOG.put("sn5", new SnackInfo("sn5", "Jus frais", "🧃", new BigDecimal("10"), "Boissons"));
        SNACK_CATALOG.put("sn6", new SnackInfo("sn6", "Pizza",     "🍕", new BigDecimal("35"), "Plats"));
        SNACK_CATALOG.put("sn7", new SnackInfo("sn7", "Croissant", "🥐", new BigDecimal("8"),  "Snacks"));
        SNACK_CATALOG.put("sn8", new SnackInfo("sn8", "Brownie",   "🍫", new BigDecimal("15"), "Desserts"));
    }

    public List<SnackInfo> getCatalog() { return new ArrayList<>(SNACK_CATALOG.values()); }

    @Transactional
    public OrderResponse createOrder(Long userId, CreateOrderRequest req) {
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : req.getItems().entrySet()) {
            SnackInfo info = SNACK_CATALOG.get(entry.getKey());
            if (info == null) throw new IllegalArgumentException("Unknown snack: " + entry.getKey());
            int qty = entry.getValue();
            BigDecimal lineTotal = info.getPrice().multiply(BigDecimal.valueOf(qty));
            total = total.add(lineTotal);
            items.add(OrderItem.builder()
                .snackId(info.getId()).snackName(info.getName())
                .quantity(qty).unitPrice(info.getPrice()).lineTotal(lineTotal).build());
        }

        String ref = "CH-" + String.format("%03d", (int)(Math.random() * 900) + 100);
        SnackOrder order = SnackOrder.builder()
            .userId(userId).total(total).note(req.getNote()).orderRef(ref).build();
        order = repo.save(order);
        final SnackOrder savedOrder = order;
        items.forEach(i -> i.setOrder(savedOrder));
        savedOrder.setItems(items);
        repo.save(savedOrder);
        log.info("Order {} created for user {}", ref, userId);
        return toResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        return repo.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).toList();
    }

    public OrderResponse getOrder(Long orderId) {
        return toResponse(repo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found")));
    }

    @Transactional
    public OrderResponse updateStatus(Long orderId, String status) {
        SnackOrder order = repo.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(SnackOrder.Status.valueOf(status.toUpperCase()));
        return toResponse(repo.save(order));
    }

    private OrderResponse toResponse(SnackOrder o) {
        List<OrderItemResponse> itemResponses = o.getItems() == null ? List.of() :
            o.getItems().stream().map(i -> new OrderItemResponse(
                i.getSnackId(), i.getSnackName(), i.getQuantity(), i.getUnitPrice(), i.getLineTotal()
            )).toList();
        return new OrderResponse(o.getId(), o.getUserId(), o.getOrderRef(), o.getTotal(),
            o.getStatus().name(), o.getNote(), itemResponses, o.getCreatedAt(), "~10 minutes");
    }
}
