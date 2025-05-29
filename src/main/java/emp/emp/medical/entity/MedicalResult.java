package emp.emp.medical.entity;

import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.common.entity.Image;
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
public class MedicalResult extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "result_id")
  private Long resultId; // 진료결과ID

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id")
  private CalendarEvent calendarEvent; // 캘린더이벤트와 일댕일 조인

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 회원과 다대일 조인

  @Column(columnDefinition = "TEXT")
  private String memo; //메모

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "prescription_image_id")
  private Image prescriptionImage; // 처방전 이미지 다대일 조징

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medicine_image_id")
  private Image medicineImage; // 약이미지 다대일 조인

  private boolean isPublic; // 가족 공개 여부
}
