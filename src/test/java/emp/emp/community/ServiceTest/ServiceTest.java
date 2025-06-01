package emp.emp.community.ServiceTest;


import emp.emp.community.dto.request.PostRequest;
import emp.emp.community.entity.Post;
import emp.emp.community.enums.HealthCategory;
import emp.emp.community.enums.PostType;
import emp.emp.community.repository.PostRepository;
import emp.emp.community.service.PostService;
import emp.emp.member.entity.Member;
import emp.emp.member.enums.Role;
import emp.emp.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;




@SpringBootTest
@Transactional
public class ServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;
    private PostRequest validPostRequest;


        @Test
        void createPost () {
            testMember = Member.builder()
                    .provider("test")
                    .verifyId("test123")
                    .username("테스트사용자")
                    .email("test@example.com")
                    .password("password123")
                    .role(Role.ROLE_USER)
                    .gender("M")
                    .birthDay(LocalDate.of(1990, 1, 1))
                    .address("서울시 강남구")
                    .build();

            testMember = memberRepository.save(testMember);
            assertThat(testMember).isNotNull();


            // PostRequest 생성 - 모든 필수 필드 설정
            validPostRequest = new PostRequest();
            validPostRequest.setTitle("테스트 게시글 제목");
            validPostRequest.setBodyText("테스트 게시글 내용입니다.");
            validPostRequest.setPostType(PostType.QUESTION);
            validPostRequest.setHealthCategory(HealthCategory.NUTRITION);
            MultipartFile mockFile = new MockMultipartFile(
                    "file",                         // name
                    "test-image.jpg",               // original filename
                    "image/jpeg",                   // content type
                    "test-image-content".getBytes() // content
            );

            try {
                Long id = postService.createPost(testMember, validPostRequest, mockFile);
                assertThat(id).isNotNull();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }
    }
