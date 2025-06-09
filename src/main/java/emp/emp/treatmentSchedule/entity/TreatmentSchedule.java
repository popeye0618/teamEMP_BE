package emp.emp.treatmentSchedule.entity;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.member.entity.Member;
import emp.emp.treatmentSchedule.dto.request.TreatmentScheduleRequest;
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
@Table(name="treatment_schedule")
public class TreatmentSchedule extends BaseEntity {

  @Id
  @GeneratedValue
  @Column(name = "treatment_id")
  private Long treatmentId; // 진료일정 시퀀스ID

  @OneToOne
  @JoinColumn(name = "event_id")
  private CalendarEvent calendarEvent;  // 연결된 캘린더 이벤트

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;  // 회원 정보

  @Column(name = "is_public", nullable = false)
  private Boolean isPublic = false;  // 진료일정 공개 여부

  @Column(name = "location", nullable = false)
  private String location;  // 진료 장소

  @Column(name = "time", nullable = false)
  private LocalDateTime time;  // 진료 시간

  @Column(name = "memo")
  private String memo;  // 추가 메모

  // 진료일정 업데이트 메소드
  public void update(TreatmentScheduleRequest request) {
    this.isPublic = request.getIsPublic();
    this.location = request.getLocation();
    this.time = request.getTime();
    this.memo = request.getMemo();
  }



}
