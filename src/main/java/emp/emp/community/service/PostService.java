package emp.emp.community.service;

import emp.emp.community.dto.request.PostRequest;
import emp.emp.community.entity.Like;
import emp.emp.community.entity.Post;
import emp.emp.community.enums.HealthCategory;
import emp.emp.community.repository.LikeRepository;
import emp.emp.community.repository.PostRepository;
import emp.emp.member.entity.Member;

import java.util.List;
import java.util.Optional;

public class PostService {
//    createPost(PostRequest dto, Member member)
//    getPostById(Long postId)
//    updatePost(Long postId, PostRequest dto, Member member)
//    deletePost(Long postId, Member member)
//    getPostsByCategory(HealthCategory category)
//    getAllPosts()
    private PostRepository postRepository;
    private LikeRepository likeRepository;

    public long createPost(Member member, PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setBodyText(postRequest.getBodyText());
        post.setPostType(postRequest.getPostType());
        post.setMember(member);
        post.setHealthCategory(postRequest.getHealthCategory());

        // 이미지 업로드 방식

        String imageUrl = "";
        post.setImageUrl(imageUrl);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }


    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public void createOrDeleteLike(Member member, Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        Optional<Like> like= likeRepository.findByMemberAndPost(member, post.get());

        if (like.isPresent()) { // 좋아요 테이블에 눌렀다는게 존재한다면?
            likeRepository.delete(like.get());// 좋아요 테이블에서 삭제
        } else {
            Like newLike = new Like();
            newLike.setPost(post.get());
            newLike.setMember(member);

            likeRepository.save(newLike); // 안눌렀다면 좋아요 누르기
        }


        // 그리고 반환값 리턴
        }


    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public List<Post> getPostsByHealthCategory(HealthCategory healthCategory) {
        List<Post> posts = postRepository.findByHealthCategory(healthCategory);
        return posts;
    }
}
}
