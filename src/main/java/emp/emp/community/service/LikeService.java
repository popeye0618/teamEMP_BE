package emp.emp.community.service;

import emp.emp.community.entity.Like;
import emp.emp.community.entity.Post;
import emp.emp.community.repository.LikeRepository;
import emp.emp.community.repository.PostRepository;
import emp.emp.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private LikeRepository likeRepository;
    private PostRepository postRepository;

    //    3. 좋아요 누르기
    public String createOrDeleteLike(Member member, Long postId) {
        String message = "";
        Post post = postRepository.findById(postId).get();
        Optional<Like> like= likeRepository.findByMemberAndPost(member, post);

        if (like.isPresent()) { // 좋아요 테이블에 눌렀다는게 존재한다면?
            likeRepository.delete(like.get());// 좋아요 테이블에서 삭제
            message = "좋아요 삭제 완료";
        } else {
            Like newLike = new Like();
            newLike.setPost(post);
            newLike.setMember(member);
            likeRepository.save(newLike); // 안눌렀다면 좋아요 누르기
            message = "좋아요 추가 성공";
        }
        return message;
    }
}
