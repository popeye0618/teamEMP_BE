package emp.emp.community.controller;


import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.community.dto.request.CommentRequest;
import emp.emp.community.dto.request.PostRequest;
import emp.emp.community.dto.response.PostResponse;
import emp.emp.community.entity.Comment;
import emp.emp.community.entity.Post;
import emp.emp.community.enums.HealthCategory;
import emp.emp.community.service.CommentService;
import emp.emp.community.service.LikeService;
import emp.emp.community.service.PostService;
import emp.emp.member.entity.Member;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommunityController {

    private SecurityUtil securityUtil;
    private PostService postService;
    private LikeService likeService;
    private CommentService commentService;

    // 0. 초기화면 (완료)
    @GetMapping("/community")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getPosts();
        return ResponseEntity.ok(posts);
    }


    // 1. 게시글 작성
    @PostMapping("community/createPost")
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal CustomUserDetails customUserDetails, MultipartFile image) {
        Member member = securityUtil.getCurrentMember();
        long postId = postService.createPost(member, postRequest, image);
        URI redirectUrl = URI.create("/community/get/" + postId);
        return ResponseEntity.created(redirectUrl).build();
    }



    // 2. 게시글 조회 (완료)
    @GetMapping("/community/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable int postId) {
        PostResponse post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }


// 3. 좋아요 누르기 (완료)
    @PostMapping("/community/{postId}/like")
    public ResponseEntity<String> createOrDeleteLike(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Member member = securityUtil.getCurrentMember();
        String message = likeService.createOrDeleteLike(member, postId);
        return ResponseEntity.ok(message);
    }

//// 4. 게시글 수정
//    @PatchMapping("/community/patch/{postId}")
//    public ResponseEntity<Post> updatePost(@PathVariable int postId, @RequestBody PostRequest postRequest) {
//
//    }


//    4-1 게시글 수정 폼 불러오기

// 5. 게시글 삭제
// 빈환값 제외 구현 완료
    @DeleteMapping("/community/delete/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }


// 6. 카테코리별 글 조회 (완료)
    @GetMapping("/community/{healthCategory}")
    public ResponseEntity<List<Post>> getPost(@PathVariable HealthCategory healthCategory) {
        List<Post> post = postService.getPostsByHealthCategory(healthCategory);
        return ResponseEntity.ok(post);
    }
//
//// 7. 댓글 달기
    @PostMapping("/community/{postId}/comments")
    public ResponseEntity<String> registerComment(@PathVariable long postId, @AuthenticationPrincipal CustomUserDetails customUserDetails,@RequestBody CommentRequest commentRequest) {
        Member member = securityUtil.getCurrentMember();
        String message = commentService.registerComment(postId, member, commentRequest);

        return ResponseEntity.ok(message);
    }



//// 8. 댓글 수정
//    @PatchMapping("community/{postId}/comment/modify/{commentId}")
//    public ResponseEntity<> patchComment() {
//
//    }
//
//
//    // 8. 댓글 수정
//    @GetMapping("community/{postId}/comment/modify/{commentId}")
//    public ResponseEntity<> getPatchCommentForm() {
//
//    }
//
//
//
    // 9. 댓글 삭제
    @DeleteMapping("/community/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        String message = commentService.deleteComment(commentId);
        return ResponseEntity.ok(message);
    }


}
