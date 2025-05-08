package emp.emp.emergency.service;


import emp.emp.emergency.client.EmergencyClient;
import emp.emp.emergency.dto.UserLocationDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FuckingTest {
    @Autowired
    EmergencyService emergencyService;

    @Autowired
    EmergencyClient emergencyClient;

    @Test
    void sibal() {
        UserLocationDTO userLocationDTO = emergencyClient.GetUserLocation("36.3465", "127.4148");
        System.out.println(userLocationDTO.getSido());
        System.out.println(userLocationDTO.getSigungu());
    }


}
