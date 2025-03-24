package emp.emp.family.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import emp.emp.member.entity.Member;
import emp.emp.util.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Family extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "family_id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "family_head_id", unique = true)
	private Member head;

	@OneToMany(mappedBy = "family")
	private List<Member> members = new ArrayList<>();

	private String name;

	private String code;

	@Builder
	public Family(String name, Member head, String code) {
		this.name = name;
		this.head = head;
		this.code = code;
	}

	public void addMember(Member member) {
		members.add(member);
		member.setFamily(this);
	}
	public void changeName(String name) {
		this.name = name;
	}
}
