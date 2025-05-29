package emp.emp.health.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emp.emp.health.entity.HealthComment;
import emp.emp.health.enums.Type;
import emp.emp.member.entity.Member;

@Repository
public interface HealthCommentRepository extends JpaRepository<HealthComment, Long> {

	Optional<HealthComment> findByYearAndMonthAndWeekIsNullAndMemberAndTypeAndDataLength(int year, int month, Member member, Type type, int dataLength);
	Optional<HealthComment> findByYearAndMonthAndWeekAndMemberAndTypeAndDataLength(int year, int month, Integer week, Member member, Type type, int dataLength);
}
