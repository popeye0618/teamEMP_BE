package emp.emp.member.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import emp.emp.member.entity.HealthTag;
import emp.emp.member.entity.Member;

@Repository
public interface HealthTagRepository extends JpaRepository<HealthTag, Long> {

	long countByMemberAndIsPublicTag(Member member, boolean isPublic);

	List<HealthTag> findAllByMember(Member member);

	List<HealthTag> findAllByMemberAndIsPublicTag(Member member, boolean isPublic);
}
