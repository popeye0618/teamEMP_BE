package emp.emp.medication.entity;

import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationDrug extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "drug_id")
  private Long drugId; // 약물 시퀀스 Id

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medicaion_id")
  private MedicationManagement medicationManagement; // 복약관리와 다대일 관계

  @Column(name = "drug_name", nullable = false, length = 50)
  private String drugName; // 액물명

  @Column(name = "dosage", nullable = false, length = 100)
private String dosage; // 복용량
}
