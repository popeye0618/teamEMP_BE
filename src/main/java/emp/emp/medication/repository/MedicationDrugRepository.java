package emp.emp.medication.repository;

import emp.emp.medication.entity.MedicationDrug;
import emp.emp.medication.entity.MedicationManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationDrugRepository extends JpaRepository<MedicationDrug,Long> {

  /**
   * 특정 복약관리의 약물들을 조회
   * @param medicationManagement 복약관리
   * @return 약물목록
   */
  List<MedicationDrug> findByMedicationManagement(MedicationManagement medicationManagement);

}
