package emp.emp.medical.service;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.calendar.entity.CalendarEvent;
import emp.emp.calendar.enums.CalendarEventType;
import emp.emp.calendar.repository.CalendarRepository;
import emp.emp.exception.BusinessException;
import emp.emp.medical.dto.request.MedicalResultRequest;
import emp.emp.medical.dto.response.MedicalResultResponse;
import emp.emp.medical.exception.MedicalResultErrorCode;
import emp.emp.member.entity.Member;
import emp.emp.util.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalResultServiceImpl implements MedicalResultService {

  private final SecurityUtil securityUtil;
  private final CalendarRepository calendarRepository;

  @Override
  @Transactional
  public MedicalResultResponse createMedicalResult(CustomUserDetails userDetails, Long eventId, MedicalResultRequest request){
    try{
      // 현재 로그인한 회원의 정보 가져옴
      Member currentMember = securityUtil.getCurrentMember();

      // 캘린더 이벤트 조회 & 소유권 검증
      CalendarEvent calendarEvent = findEventByIdAndValidate(eventId, currentMember);

      // 이벤트 타입이 진료결과 인지 확인
      validateEventType(calendarEvent);



      return null;
    }catch(BusinessException e){
      throw e;
    }catch(Exception e){
      throw new BusinessException(MedicalResultErrorCode.DATABASE_ERROR);
    }
  }


  // =====================================================================

  /**
   * 캘린더 이벤트의 시퀀스ID로 이벤트 조회 & 현재 사용자의 소유인지 검증
   * @param eventId
   * @param currentMember
   * @return 검증된 캘린더 이벤트
   */
  private CalendarEvent findEventByIdAndValidate(Long eventId, Member currentMember){
    // 캘린더 이벤트 조회
    CalendarEvent calendarEvent = calendarRepository.findById(eventId)
            .orElseThrow(() -> new BusinessException(MedicalResultErrorCode.CALENDAR_EVENT_NOT_FOUND));

    // 로그인한 사용자의 일정인지 소유권 확인
    if(!calendarEvent.getMember().equals(currentMember)){
      throw new BusinessException(MedicalResultErrorCode.ACCESS_DENIED);
    }
    return calendarEvent;
  }

  /**
   * 이벤트 타입이 CHECKUP(진료 결과)인지 확인!
   * @param calendarEvent 확인할 캘린더 이벤트
   */
  private void validateEventType(CalendarEvent calendarEvent){
    if(calendarEvent.getEventType() != CalendarEventType.CHECKUP){
      throw new BusinessException(MedicalResultErrorCode.EVENT_TYPE_INVALID);
    }
  }


}
