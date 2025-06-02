package emp.emp.medication.repository;

import emp.emp.medication.entity.MedicationManagement;
import emp.emp.medication.entity.MedicationTiming;
import emp.emp.medication.enums.MedicationTimingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationTimingRepository extends JpaRepository<MedicationTiming,Long> {

  /**
   * 특정 복약관리의 복약시기들을 조회
   * @param medicationManagement 복약관리
   * @return 복약시기 목록
   */
  List<MedicationTiming> findByMedicationManagement(MedicationManagement medicationManagement);

  /**
   * 특정 복약관리의 특정타입의 복약시기들을 조회
   * @param medicationManagement 복약관리
   * @param timingType 복약시기의 타입
   * @return 복약시기 목록
   */
  List<MedicationTiming> findByMedicationManagementAndTimingType(MedicationManagement medicationManagement, MedicationTimingType timingType);

}
