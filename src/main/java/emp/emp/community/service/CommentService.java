package emp.emp.community.service;

import emp.emp.community.dto.request.CommentRequest;
import emp.emp.community.entity.Comment;
import emp.emp.community.entity.Post;
import emp.emp.community.repository.CommentRepository;
import emp.emp.community.repository.LikeRepository;
import emp.emp.community.repository.PostRepository;
import emp.emp.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;



    public String registerComment(long postId, Member member, CommentRequest commentRequest) {
        Optional<Post> post = postRepository.findById(postId);
        Comment comment = new Comment();

        if (post.isEmpty()) {
            return "게사글이 존재하지 않습니다";
        } else {
            comment.setPost(post.get());
            comment.setMember(member);
            comment.setContent(commentRequest.getComment());
            commentRepository.save(comment);
            return "댓글 등록 완료";
        }




    }

    public String deleteComment(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            commentRepository.delete(comment.get());
            return "삭제완료";
        } else {
            return "삭제할 댓글이 없습니다";
        }


    }
//    addComment(Long postId, CommentRequest dto, Member member)
//    getCommentsByPost(Long postId)
//    updateComment(Long commentId, CommentRequest dto, Member member)
//    deleteComment(Long commentId, Member member)
}
