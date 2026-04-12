package ma.chaghaf.snack.repository;
import ma.chaghaf.snack.entity.SnackOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SnackOrderRepository extends JpaRepository<SnackOrder, Long> {
    List<SnackOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<SnackOrder> findByOrderRef(String orderRef);
    List<SnackOrder> findByStatus(SnackOrder.Status status);
}
