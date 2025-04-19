package emp.emp.emergency.dto;


import lombok.Getter;

@Getter
public class UserLocationDTO {
    private String sido;
    private String sigungu;

    public UserLocationDTO(String sido, String sigungu) {
        this.sido = sido; // 시도
        this.sigungu = sigungu; // 시군구
    }
}


