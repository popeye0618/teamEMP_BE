package emp.emp.medication.entity;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.member.entity.Member;
import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "medication_management")
public class MedicationManagement extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "medication_id")
  private Long medicatonId; // 복약관리 시퀀스 Id

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private CalendarEvent calendarEvent; // 캘린더 이벤트와 일대일 관계

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 회원과 다대일 관계

  @Column(name = "disease_name", nullable = false, length = 10)
  private String diseaseName; // 병명(필수, 최대10자)

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate; // 복약 시작일

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate; // 복약 종료일

  @Column(name = "is_publid")
  @Builder.Default
  private Boolean isPublic = false; // 가족 공개여부

  @OneToMany(mappedBy = "medicationManagement", cascade = CascadeType.ALL, orphanRemoval = true)
  @Builder.Default
  private List<MedicationDrug> drugs = new ArrayList<>(); // 약물정보와 일대다 관계

}
