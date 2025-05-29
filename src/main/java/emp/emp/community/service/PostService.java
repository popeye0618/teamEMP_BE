package emp.emp.community.service;

import emp.emp.community.dto.request.PostRequest;
import emp.emp.community.entity.Post;
import emp.emp.community.repository.PostRepository;
import emp.emp.member.entity.Member;

import java.util.List;

public class PostService {
//    createPost(PostRequest dto, Member member)
//    getPostById(Long postId)
//    updatePost(Long postId, PostRequest dto, Member member)
//    deletePost(Long postId, Member member)
//    getPostsByCategory(HealthCategory category)
//    getAllPosts()
    private PostRepository postRepository;

    public long createPost(Member member, PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setBodyText(postRequest.getBodyText());
        post.setPostType(postRequest.getPostType());
        post.setMember(member);
        post.setHealthCategory(postRequest.getHealthCategory());


        String imageUrl = "";
        post.setImageUrl(imageUrl);

        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }


    public List<Post> getPosts() {
        return postRepository.findAll();
    }

}
