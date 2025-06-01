package emp.emp.community.service;

import emp.emp.community.dto.request.PostRequest;
import emp.emp.community.dto.response.PostResponse;
import emp.emp.community.entity.Comment;
import emp.emp.community.entity.Like;
import emp.emp.community.entity.Post;
import emp.emp.community.enums.HealthCategory;
import emp.emp.community.repository.CommentRepository;
import emp.emp.community.repository.LikeRepository;
import emp.emp.community.repository.PostRepository;
import emp.emp.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final S3Client s3Client;


//    0. 초기화면
    public List<Post> getPosts() {
        return postRepository.findAll();
    }


//    1. 게시글 작성
    public long createPost(Member member, PostRequest postRequest, MultipartFile image) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setBodyText(postRequest.getBodyText());
        post.setPostType(postRequest.getPostType());
        post.setMember(member);
        post.setHealthCategory(postRequest.getHealthCategory());

        // 이미지 업로드 방식
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                String filename = "post-images/" + UUID.randomUUID() + "-" + image.getOriginalFilename();

                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket("team-emp")
                        .key(filename)
                        .contentType(image.getContentType())
                        .build();

                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.getBytes()));

                imageUrl = s3Client.utilities().getUrl(builder -> builder.bucket("team-emp").key(filename)).toExternalForm();

            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드 실패", e);
            }
        }
        post.setImageUrl(imageUrl);
        LocalDateTime now = LocalDateTime.now();
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

//    2. 게시글 조회
    public PostResponse getPostById(long postId) {
        PostResponse postResponse = new PostResponse();
        Optional<Post> post = postRepository.findById(postId);

        if (post.isPresent()) {
            long likes = likeRepository.countByPost(post.get());
            postResponse.setTitle(post.get().getTitle()); // 제목 반환
            postResponse.setBodyText(post.get().getBodyText()); // 내용
            postResponse.setPostType(post.get().getPostType()); // 글 타입
            postResponse.setHealthCategory(post.get().getHealthCategory()); // 카테고리 타입
            postResponse.setMember(post.get().getMember()); // 멤버
            postResponse.setLikes(likes); // 좋아요 수
            postResponse.setImageUrl(post.get().getImageUrl()); // 이미지

            List<Comment> comments = commentRepository.findByPost(post.get());
            postResponse.setComments(comments); // 댓글


        }
        return postResponse;
    }




//    3. 좋아요 누르기
    public String createOrDeleteLike(Member member, Long postId) {
        String message = "";
        Optional<Post> post = postRepository.findById(postId);
        Optional<Like> like= likeRepository.findByMemberAndPost(member, post.get());

        if (like.isPresent()) { // 좋아요 테이블에 눌렀다는게 존재한다면?
            likeRepository.delete(like.get());// 좋아요 테이블에서 삭제
            message = "좋아요 삭제 완료";
        } else {
            Like newLike = new Like();
            newLike.setPost(post.get());
            newLike.setMember(member);
            likeRepository.save(newLike); // 안눌렀다면 좋아요 누르기
            message = "좋아요 추가 성공";
        }
        return message;
    }


//    4. 게시글 수정



//    5. 게시글 삭제
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }


//    6. 카테고리별 글 조회
    public List<Post> getPostsByHealthCategory(HealthCategory healthCategory) {
        List<Post> posts = postRepository.findByHealthCategory(healthCategory);
        return posts;
    }


}

