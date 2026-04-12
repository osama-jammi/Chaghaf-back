package ma.chaghaf.reservation.repository;
import ma.chaghaf.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    List<Reservation> findByUserIdAndStatus(Long userId, Reservation.Status status);
    List<Reservation> findBySalleIdAndReservationDate(String salleId, LocalDate date);
}
