package emp.emp.emergency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyRoomDTO {
    private boolean hvmriayn; // MRI 가용여부
    private boolean hvctayn; // CT가용여부
    private int hvec; // 응급실 병상 수
    private String dutytel3; // 응급실 전화
    private String dutyName; // 병원 이름
    private double hospitalLatitude; // 병원 위도
    private double hospitalLongitude; // 병원 경도
}



