package emp.emp.emergency.service;

import emp.emp.emergency.dto.EmergencyAedDTO;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
public class EmergencyServiceTest {
    @Autowired
    private EmergencyService emergencyService;

    @Test
    void fetchAndPrintEmergencyRoomDTOs_Integration() throws Exception {  // 메서드 이름 변경
        // 서울 시청 근처 위경도 예시
        try {
            String latitude = "37.5665";
            String longitude = "126.9780";

            // emergencyClient에서 실제 데이터를 받아오는 방식 사용
            List<EmergencyRoomDTO> emergencyRoomDtos = emergencyService.getEmergencyRoomInformation(latitude, longitude);
            List<EmergencyAedDTO> emergencyAedDTOS = emergencyService.getEmergencyAedInformation(latitude, longitude);



            System.out.println("===== EmergencyRoomDTO List Start =====");
            for (EmergencyRoomDTO roomDTO : emergencyRoomDtos) {
                System.out.println(roomDTO);
            }
            System.out.println("===== EmergencyRoomDTO List End =====");
            System.out.println(" ");
            System.out.println(" ");

            System.out.println("===== EmergencyRoomDTO List Start =====");
            for (EmergencyAedDTO aedDTO : emergencyAedDTOS) {
                System.out.println(aedDTO);
            }
            System.out.println("===== EmergencyRoomDTO List End =====");

            Assertions.assertNotNull(emergencyRoomDtos); // Null 아님
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test


    void BothTest() throws IOException {
        String latitude = "37.5665";
        String longitude = "126.9780";

        Map<String, Object> result = emergencyService.getCombinedEmergencyInfo(latitude, longitude);

        Assertions.assertNotNull(result);
    }

}