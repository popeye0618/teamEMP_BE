package emp.emp.emergency.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLocationDTO {
    private String sido;
    private String sigungu;



    public UserLocationDTO() {
        // 기본값으로 초기화하거나 아무 작업도 하지 않을 수 있습니다.
    }

    // 매개변수를 받는 생성자
    public UserLocationDTO(String sido, String sigungu) {
        this.sido = sido; // 시도
        this.sigungu = sigungu; // 시군구
    }

}


