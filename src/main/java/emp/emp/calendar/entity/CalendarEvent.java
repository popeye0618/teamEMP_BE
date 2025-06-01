package emp.emp.calendar.entity;

import emp.emp.calendar.dto.request.CalendarEventRequest;
import emp.emp.calendar.enums.CalendarEventType;
import emp.emp.medical.entity.MedicalResult;
import emp.emp.medication.enums.MedicationManagement;
import emp.emp.member.entity.Member;
import emp.emp.util.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarEvent extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "event_id")
  private Long eventId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @Enumerated(EnumType.STRING)
  private CalendarEventType eventType;

  private String title;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private Integer priority;

  // 진료결과와 일대일 관계
  @OneToOne(mappedBy = "calendarEvent", cascade = CascadeType.ALL, orphanRemoval = true)
  private MedicalResult medicalResult;

  // 복약관리와 일대일 과계
  @OneToOne(mappedBy="calendarEvent", cascade = CascadeType.ALL, orphanRemoval = true)
  private MedicationManagement medicaionManagement;

  public void update(CalendarEventRequest request) {
    this.eventType = request.getEventType(); // enum 타입으로 매핑됨
    this.title = request.getTitle();
    this.startDate = request.getStartDate();
    this.endDate = request.getEndDate();
    this.priority = request.getPriority();
  }
}
