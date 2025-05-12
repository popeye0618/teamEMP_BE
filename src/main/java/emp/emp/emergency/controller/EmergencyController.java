package emp.emp.emergency.controller;

import ch.qos.logback.core.model.Model;
import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.emergency.dto.EmergencyAedDTO;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import emp.emp.emergency.service.EmergencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class EmergencyController {
    private final EmergencyService emergencyService;


    @GetMapping("/api/emergency/room")
    public ResponseEntity<List<EmergencyRoomDTO>> emergencyRoom(
            @RequestParam String userLatitude,
            @RequestParam String userLongitude,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException, JAXBException {

        try {
            List<EmergencyRoomDTO> emergencyRooms = emergencyService.getEmergencyRoomInformation(userLatitude, userLongitude);

            if (emergencyRooms.isEmpty()) {
                // 데이터가 없을 경우 204 No Content 반환
                return ResponseEntity.noContent().build();
            }

            // 성공적으로 데이터를 가져왔을 경우 200 OK와 함께 데이터 반환
            return ResponseEntity.ok(emergencyRooms);
        } catch (Exception e) {
            // 예외 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/api/emergency/aed")
    public ResponseEntity<List<EmergencyAedDTO>> emergencyAed(
            @RequestParam String userLatitude,
            @RequestParam String userLongitude,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException, JAXBException {

        try {
            List<EmergencyAedDTO> emergencyAed = emergencyService.getEmergencyAedInformation(userLatitude, userLongitude);

            if (emergencyAed.isEmpty()) {
                // 데이터가 없을 경우 204 No Content 반환
                return ResponseEntity.noContent().build();
            }

            // 성공적으로 데이터를 가져왔을 경우 200 OK와 함께 데이터 반환
            return ResponseEntity.ok(emergencyAed);
        } catch (Exception e) {
            // 예외 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/api/emergency/both")
    public ResponseEntity<Map<String, Object>> emergencyAedAndRoom(
            @RequestParam String userLatitude,
            @RequestParam String userLongitude,
            @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException, JAXBException {

        try {
            Map<String, Object> result = emergencyService.getCombinedEmergencyInfo(userLatitude, userLongitude);
            if (result.isEmpty()) {
                // 데이터가 없을 경우 204 No Content 반환
                return ResponseEntity.noContent().build();
            }

            // 성공적으로 데이터를 가져왔을 경우 200 OK와 함께 데이터 반환
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // 예외 발생 시 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
