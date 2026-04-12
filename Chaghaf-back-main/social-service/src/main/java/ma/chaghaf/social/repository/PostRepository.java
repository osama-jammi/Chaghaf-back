package ma.chaghaf.social.repository;
import ma.chaghaf.social.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByActiveTrueOrderByCreatedAtDesc(Pageable pageable);
    Page<Post> findByAuthorIdAndActiveTrueOrderByCreatedAtDesc(Long authorId, Pageable pageable);
}
