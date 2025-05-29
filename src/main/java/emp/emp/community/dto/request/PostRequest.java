package emp.emp.community.dto.request;

import emp.emp.community.enums.HealthCategory;
import emp.emp.community.enums.PostType;
import emp.emp.member.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class PostRequest {
    private String title;
    private String bodyText;
    private PostType postType;
    private Member member;
    private MultipartFile image;
    private HealthCategory healthCategory;
}
