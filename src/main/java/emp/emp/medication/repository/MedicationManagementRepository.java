package emp.emp.medication.repository;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.medication.entity.MedicationManagement;
import emp.emp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationManagementRepository extends JpaRepository<MedicationManagement, Long> {

  /**
   * 캘린더 이벤트로 복약관리 조회
   * @param calendarEvent 조회할 캘린더 이벤트
   * @return 복약관리 정보
   */
  Optional<MedicationManagement> findByCalendarEvent(CalendarEvent calendarEvent);

  /**
   * 특정 회원의 모든 복약관리를 조회(-> 복약 시작일 기준으로 내림차순 정렬)
   * @param member 조회핳 회원
   * @return 복약관리 목록
   */
  List<MedicationManagement> findByMemberOrderByStartDateDesc(Member member);

  /**
   * 특정 회원의 공개된 복약관리를 조회
   * @param member 조회할 회원
   * @param isPublic 공개 여부
   * @return 공개된 복약관리의 목록
   */
  List<MedicationManagement> findByMemberAndIsPublic(Member member, Boolean isPublic);

}
