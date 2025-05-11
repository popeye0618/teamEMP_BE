package emp.emp.treatmentSchedule.repository;

import emp.emp.member.entity.Member;
import emp.emp.treatmentSchedule.entity.TreatmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentScheduleRepository extends JpaRepository<TreatmentSchedule, Long> {


}
