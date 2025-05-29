package emp.emp.community.repository;

import emp.emp.community.entity.Like;
import emp.emp.community.entity.Post;
import emp.emp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByMemberAndPost(Member member, Post post);
}
