package emp.emp.medical.repository;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.medical.entity.MedicalResult;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicalResultRepository extends JpaRepository<MedicalResult, Long> {

  /**
   * 캘란더 이벤트로 진료 결과 조회
   * @param calendarEvent 조회할 캘린더 이벤트
   * @return 진료결과
   */
  Optional<MedicalResult> findByCalendarEvent(CalendarEvent calendarEvent);

  /**
   * 가족 회원들의 공개된 진료 결과 조회 (본인 제외)
   * @param familyId 가족 ID
   * @param currentMemberId 현재 회원 ID (본인은 제외)
   * @return 공개된 진료 결과 목록
   */
  @Query("SELECT mr FROM MedicalResult mr " +
          "JOIN FETCH mr.member m " +
          "JOIN FETCH mr.calendarEvent ce " +
          "LEFT JOIN FETCH mr.prescriptionImage " +
          "LEFT JOIN FETCH mr.medicineImage " +
          "WHERE m.family.id = :familyId " +
          "AND mr.isPublic = true " +
          "AND m.id != :currentMemberId " +
          "ORDER BY mr.createdAt DESC")
  List<MedicalResult> findByMemberFamilyIdAndIsPublicTrueAndMemberIdNot(
          @Param("familyId") Long familyId,
          @Param("currentMemberId") Long currentMemberId);
}
