package emp.emp.community.controller;


import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.community.dto.request.PostRequest;
import emp.emp.community.entity.Post;
import emp.emp.community.service.CommentService;
import emp.emp.community.service.LikeService;
import emp.emp.community.service.PostService;
import emp.emp.member.entity.Member;
import emp.emp.util.security.SecurityUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommunityController {

    private SecurityUtil securityUtil;
    private PostService postService;
    private LikeService likeService;
    private CommentService commentService;

    // 0. 초기화면
    @GetMapping("community/index")
    public ResponseEntity<List<Post>> getAllPosts(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Member member = securityUtil.getCurrentMember();


    }


    // 1. 게시글 작성
    @PostMapping("community/createPost")
    public ResponseEntity<> createPost()(@RequestBody PostRequest postRequest) {

        return ResponseEntity.ok().build();
    }



    // 2. 게시글 조회
    @GetMapping("community/get/{postId}")
    public ResponseEntity<> getPost(@PathVariable int postId) {

    }


// 3. 좋아요 누르기
    @PostMapping
    public ResponseEntity<> getPost() {

    }

// 4. 게시글 수정
    @PatchMapping("community/patch/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable int postId, @RequestBody PostRequest postRequest) {

    }

// 5. 게시글 삭제
    @DeleteMapping("community/delete/{postId}")
    public ResponseEntity<> getPost() {

    }


// 6. 카테코리별 글 조회
    @GetMapping
    public ResponseEntity<> getPost() {

    }

// 7. 댓글 달기
    @PostMapping
    public ResponseEntity<> getPost() {

    }



// 8. 댓글 수정
    @PatchMapping("community/comment/moldify/{postId}")
    public ResponseEntity<> getPost() {

    }


// 9. 댓글 삭제
    @DeleteMapping
    public ResponseEntity<> getPost() {

    }


}
