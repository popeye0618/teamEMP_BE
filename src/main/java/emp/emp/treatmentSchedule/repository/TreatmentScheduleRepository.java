package emp.emp.treatmentSchedule.repository;

import emp.emp.member.entity.Member;
import emp.emp.treatmentSchedule.entity.TreatmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TreatmentScheduleRepository extends JpaRepository<TreatmentSchedule, Long> {

  /**
   * 특정 회원의 모든 진료일정을 조회하는 메서드
   * @param member 회원 엔티티
   * @return 진료일정 목록
   */
  List<TreatmentSchedule> findByMember(Member member);

  /**
   * 특정 일정(z캘린더)ID에 해당하는 진료일정을 조회하는 메서드
   * @param calendarEventId 캘린더 이벤트 ID
   * @return 진료일정 (Optional)
   */
  Optional<TreatmentSchedule> findByCalendarEvent_EventId(Long calendarEventId);

  /**
   * 특정 기간 내의 진료일정을 조회하는 메서드
   * @param start 시작시간
   * @param end 종료시간
   * @return 진료일정 목록
   */
  List<TreatmentSchedule> findByTimeBetween(LocalDateTime start, LocalDateTime end);

  /**
   * 특정 회원의 특정 기간 내 진료일정을 조회하는 메서드
   * @param member 회원Entity
   * @param start 시작시간
   * @param end 종료시간
   * @return 진료일정 목록
   */
  List<TreatmentSchedule> findByMemberAndTimeBetween(Member member, LocalDateTime start, LocalDateTime end);

  /**
   * 공개된 진료일정만 조회하는 메서드
   * @return 공개된 진료일정 목록
   */
  List<TreatmentSchedule> findByIsPublicTrue();

  /**
   * 특정 위치에서의 진료일정을 조회하는 메서드
   * @param location 장소
   * @return 진료일정 목록
   */
  List<TreatmentSchedule> findByLocation(String location);

}
