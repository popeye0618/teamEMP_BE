package emp.emp.medication.enums;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.member.entity.Member;
import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

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



}
