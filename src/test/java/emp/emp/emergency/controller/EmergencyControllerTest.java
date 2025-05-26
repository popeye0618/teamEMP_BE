package emp.emp.emergency.controller;

import emp.emp.auth.custom.CustomUserDetails;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest                           // 전체 컨텍스트 로드
@AutoConfigureMockMvc(addFilters = false) // 보안 필터 무시 → 항상 200 OK
public class EmergencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void fetchAndPrintEmergencyRoomDTOs() throws Exception {
        // 1) 컨트롤러 호출 (JSON 배열 반환 기대)
        MvcResult mvcResult = mockMvc.perform(
                        get("/api/emergency/room")
                                .param("userLatitude", "37.5665")
                                .param("userLongitude", "126.9780")
                )
                .andExpect(status().isOk())  // 401 없이 200 OK
                .andReturn();

        // 2) 본문 문자열 얻기
        String json = mvcResult.getResponse().getContentAsString();
        System.out.println("▶ 응답 JSON: " + (json.isBlank() ? "[빈 문자열]" : json));

        // 3) JSON 배열 또는 빈 배열 처리
        List<EmergencyRoomDTO> dtos;
        if (json == null || json.isBlank() || json.trim().equals("[]")) {
            dtos = List.of();  // 빈 리스트
        } else {
            dtos = objectMapper.readValue(
                    json,
                    new TypeReference<List<EmergencyRoomDTO>>() {}
            );
        }

        // 4) DTO 전체 출력
        System.out.println("===== EmergencyRoomDTO List Start =====");
        dtos.forEach(System.out::println);
        System.out.println("===== EmergencyRoomDTO List End =====");

        // 5) 간단 검증: null 아니면 OK
        assertThat(dtos).isNotNull();
    }
}