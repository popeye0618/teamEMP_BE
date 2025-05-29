package emp.emp.medication.entity;

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
public class MedicationManagement extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "medicaion_id")
  private Long medicationId;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private CalendarEvent calendarEvent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;


}
