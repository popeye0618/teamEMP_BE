package emp.emp.member.entity;

import java.time.LocalDate;
import java.time.Period;

import emp.emp.member.enums.Role;
import emp.emp.util.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long id;

	private String provider;

	private String verifyId;

	private String username;

	private String email;

	private String password;

	@Enumerated(value = EnumType.STRING)
	private Role role;

	private String gender;

	private LocalDate birthDate;

	@Builder
	public Member(String provider, String verifyId, String username, String email, String password, Role role,
		String gender, LocalDate birthDate) {
		this.provider = provider;
		this.verifyId = verifyId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.gender = gender;
		this.birthDate = birthDate;
	}

	/**
	 * 생년월일 기준으로 만나이 계산
	 *
	 * @return 만나이
	 */
	public int getAge() {
		return Period.between(this.birthDate, LocalDate.now()).getYears();
	}
}