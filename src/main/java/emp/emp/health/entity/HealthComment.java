package emp.emp.health.entity;

import emp.emp.health.enums.Type;
import emp.emp.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "health_comment_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	private int year;

	private int month;

	private Integer week;

	@Enumerated(EnumType.STRING)
	private Type type;

	private String content;

	private int dataLength;

	@Builder
	public HealthComment(Member member, int year, int month, Integer week, Type type, String content, int dataLength) {
		this.member = member;
		this.year = year;
		this.month = month;
		this.week = week;
		this.type = type;
		this.content = content;
		this.dataLength = dataLength;
	}
}
