package emp.emp.medical.repository;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.medical.entity.MedicalResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalResultRepository extends JpaRepository<MedicalResult, Long> {

  /**
   * 캘란더 이벤트로 진료 결과 조회
   * @param calendarEvent 조회할 캘린더 이벤트
   * @return 진료결과
   */
  Optional<MedicalResult> findByCalendarEvent(CalendarEvent calendarEvent);

}
