package emp.emp.emergency.controller;

import ch.qos.logback.core.model.Model;
import emp.emp.emergency.dto.EmergencyAedDTO;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import emp.emp.emergency.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EmergencyController {
    private final EmergencyService emergencyService;

    // 응급실 조회
    @GetMapping("/api/auth/emergency/room")
    public List<EmergencyRoomDTO> emergencyRoom(@RequestParam String userLatitude, @RequestParam String userLongitude) throws IOException, JAXBException { // 사용자의 위도 경도를 받아 서비스 계층에 넘긴다
        return emergencyService.getEmergencyRoomInformation(userLatitude, userLongitude); // 서비스 단에서 DTO로 변환한 뒤 컨트롤러로 받기
    }


//    AED 조회
//    public List<EmergencyAedDTO> emergencyAed(@RequestParam double userLatitude, @RequestParam double userLongitude, Model model) {
//        return
//    }
}
