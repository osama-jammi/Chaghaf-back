package ma.chaghaf.social.repository;
import ma.chaghaf.social.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByConversationKeyOrderBySentAtAsc(String conversationKey);

    @Query("SELECT DISTINCT m.conversationKey FROM Message m WHERE m.senderId = :uid OR m.recipientId = :uid ORDER BY MAX(m.sentAt) DESC")
    List<String> findConversationKeysByUserId(Long uid);

    long countBySenderIdOrRecipientIdAndIsReadFalse(Long senderId, Long recipientId);
}
