package emp.emp.community.entity;


import emp.emp.member.entity.Member;
import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "body_text", nullable = false)
    private String bodyText;

    @Column(name = "post_type", nullable = false)
    private String postType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 좋아요 어케하지





}
