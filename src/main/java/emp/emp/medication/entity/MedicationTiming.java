package emp.emp.medication.entity;

import emp.emp.medication.enums.MedicationTimingType;
import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "medicaion_timing")
public class MedicationTiming extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "timing_id")
  private Long timingId; // 복약시기의 시퀀스ID

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medication_id")
  private MedicationManagement medicationManagement; // 복약관리와 다대일 관계

  @Enumerated(EnumType.STRING)
  @Column(name = "timing_type", nullable = false)
  private MedicationTimingType timingType; // 복약시기(MORNING, LUNCH, EVENING)

  @Column(name = "precaution", columnDefinition = "TEXT")
  private String precaution; // 주의사항

}
