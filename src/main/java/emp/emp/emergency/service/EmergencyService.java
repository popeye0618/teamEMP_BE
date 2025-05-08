package emp.emp.emergency.service;

import emp.emp.emergency.client.EmergencyClient;
import emp.emp.emergency.dto.EmergencyAedDTO;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import emp.emp.emergency.dto.UserLocationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// 사용자의 위도 경도를 받아 도 이름과 시 이름을 추출하고 받아온 데이터를 자바 객체(DTO)로 변환해서 컨트롤러에 넘긴다.
@Service
public class EmergencyService {
    private final EmergencyClient emergencyClient;


    public EmergencyService(EmergencyClient emergencyClient) {
        this.emergencyClient = emergencyClient;
    }


    // 응급실 위치 및 병상정보를 받아오기
    public List<EmergencyRoomDTO> getEmergencyRoomInformation(String userLatitude, String userLongitude) throws IOException {
         // 시군구 받기
        UserLocationDTO userLocationDTO = emergencyClient.GetUserLocation(userLatitude, userLongitude);
        return emergencyClient.GetEmergencyRoomInformationApi(userLocationDTO);
    }


    public List<EmergencyAedDTO> getEmergencyAedInformation(String userLatitude, String userLongitude) throws IOException {
        UserLocationDTO userLocationDTO = emergencyClient.GetUserLocation(userLatitude, userLongitude);
        return emergencyClient.GetEmergencyAedInformationApi(userLocationDTO);
    }


    public Map<String, Object> getCombinedEmergencyInfo(String userLatitude, String userLongitude) throws IOException {
        // 매개변수 그대로 사용: userLatitude, userLongitude
        UserLocationDTO userLocationDTO = emergencyClient.GetUserLocation(userLatitude, userLongitude);

        List<EmergencyRoomDTO> emergencyRoomList = emergencyClient.GetEmergencyRoomInformationApi(userLocationDTO);
        List<EmergencyAedDTO> emergencyAedList = emergencyClient.GetEmergencyAedInformationApi(userLocationDTO);

        Map<String, Object> result = new HashMap<>();
        result.put("emergencyRooms", emergencyRoomList);
        result.put("aedLocations", emergencyAedList);

        return result;
    }
}





