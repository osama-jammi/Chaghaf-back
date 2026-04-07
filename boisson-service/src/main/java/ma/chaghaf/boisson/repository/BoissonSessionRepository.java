package ma.chaghaf.boisson.repository;
import ma.chaghaf.boisson.entity.BoissonSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoissonSessionRepository extends JpaRepository<BoissonSession, Long> {
    List<BoissonSession> findByUserId(Long userId);
    @Query("SELECT COUNT(b) FROM BoissonSession b WHERE b.userId = :userId AND b.consumedAt BETWEEN :start AND :end")
    long countByUserIdAndDateRange(Long userId, LocalDateTime start, LocalDateTime end);
}
