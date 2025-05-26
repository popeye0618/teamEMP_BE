package emp.emp.emergency.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmergencyRoomDTO {
    private boolean isMriAvailable;; // MRI 가용여부
    private boolean isCtAvailable; // CT가용여부
    private int emergencyBedCount;; // 응급실 병상 수
    private String hospitalTel; // 응급실 전화
    private String hospitalName; // 병원 이름
    private String hospitalLatitude; // 병원 위도
    private String hospitalLongitude; // 병원 경도


    @Override
    public String toString() {
        return "EmergencyRoomDTO{" +
                "isMriAvailable=" + isMriAvailable +
                ", isCtAvailable=" + isCtAvailable +
                ", emergencyBedCount=" + emergencyBedCount +
                ", hospitalTel='" + hospitalTel + '\'' +
                ", hospitalName='" + hospitalName + '\'' +
                ", hospitalLatitude='" + hospitalLatitude + '\'' +
                ", hospitalLongitude='" + hospitalLongitude + '\'' +
                '}';
    }
}



