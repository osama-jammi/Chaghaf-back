package ma.chaghaf.social.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.chaghaf.social.dto.SocialDtos.*;
import ma.chaghaf.social.entity.*;
import ma.chaghaf.social.repository.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j @Service @RequiredArgsConstructor
public class SocialService {
    private final PostRepository postRepo;
    private final PostLikeRepository likeRepo;
    private final MessageRepository messageRepo;

    public Page<PostResponse> getPosts(Long currentUserId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepo.findByActiveTrueOrderByCreatedAtDesc(pageable)
            .map(p -> toPostResponse(p, currentUserId));
    }

    @Transactional
    public PostResponse createPost(Long userId, String userName, String avatar, String role, CreatePostRequest req) {
        Post post = Post.builder()
            .authorId(userId).authorName(userName).authorAvatar(avatar)
            .authorRole(role).content(req.getContent()).build();
        post = postRepo.save(post);
        log.info("Post {} created by user {}", post.getId(), userId);
        return toPostResponse(post, userId);
    }

    @Transactional
    public PostResponse toggleLike(Long userId, Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        boolean alreadyLiked = likeRepo.existsByPostIdAndUserId(postId, userId);
        if (alreadyLiked) {
            likeRepo.findByPostIdAndUserId(postId, userId).ifPresent(likeRepo::delete);
            post.setLikesCount(Math.max(0, post.getLikesCount() - 1));
        } else {
            likeRepo.save(PostLike.builder().postId(postId).userId(userId).build());
            post.setLikesCount(post.getLikesCount() + 1);
        }
        return toPostResponse(postRepo.save(post), userId);
    }

    @Transactional
    public MessageResponse sendMessage(Long senderId, SendMessageRequest req) {
        long a = Math.min(senderId, req.getRecipientId());
        long b = Math.max(senderId, req.getRecipientId());
        String convKey = a + "_" + b;
        Message msg = Message.builder()
            .senderId(senderId).recipientId(req.getRecipientId())
            .content(req.getContent()).conversationKey(convKey).build();
        msg = messageRepo.save(msg);
        return toMessageResponse(msg);
    }

    public List<MessageResponse> getConversation(Long userId, Long otherId) {
        long a = Math.min(userId, otherId);
        long b = Math.max(userId, otherId);
        return messageRepo.findByConversationKeyOrderBySentAtAsc(a + "_" + b)
            .stream().map(this::toMessageResponse).toList();
    }

    private PostResponse toPostResponse(Post p, Long currentUserId) {
        boolean liked = currentUserId != null && likeRepo.existsByPostIdAndUserId(p.getId(), currentUserId);
        return new PostResponse(p.getId(), p.getAuthorId(), p.getAuthorName(), p.getAuthorAvatar(),
            p.getAuthorRole(), p.getContent(), p.getLikesCount(), p.getCommentsCount(),
            p.getIsOfficial(), liked, timeAgo(p.getCreatedAt()), p.getCreatedAt());
    }

    private MessageResponse toMessageResponse(Message m) {
        return new MessageResponse(m.getId(), m.getSenderId(), m.getRecipientId(),
            m.getContent(), m.getIsRead(), "user_" + m.getSenderId(), m.getSentAt());
    }

    private String timeAgo(LocalDateTime dt) {
        if (dt == null) return "";
        long mins = ChronoUnit.MINUTES.between(dt, LocalDateTime.now());
        if (mins < 60) return "il y a " + mins + " min";
        long hrs = ChronoUnit.HOURS.between(dt, LocalDateTime.now());
        if (hrs < 24) return "il y a " + hrs + "h";
        return "il y a " + ChronoUnit.DAYS.between(dt, LocalDateTime.now()) + "j";
    }
}
