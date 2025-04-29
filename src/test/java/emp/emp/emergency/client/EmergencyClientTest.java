package emp.emp.emergency.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import emp.emp.emergency.dto.UserLocationDTO;
import emp.emp.emergency.service.EmergencyService;
import emp.emp.emergency.client.EmergencyClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EmergencyClientTest {
    @Mock
    private RestTemplate restTemplate;  // 실제 API 호출을 mock

    @InjectMocks
    private EmergencyService emergencyService;  // 테스트할 서비스 클래스

    @Mock
    private EmergencyClient emergencyClient;

    @Mock
    private ResponseEntity<JsonNode> responseEntity;

    @Mock
    private JsonNode jsonNode;

    @Mock
    private JsonNode documents;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Mockito 초기화
    }

    @Test
    public void testGetUserLocation() {
        // Given: mock으로 반환할 응답을 설정
        String userLatitude = "37.648092";
        String userLongitude = "126.626675";
        String expectedSido = "경기도";
        String expectedSigungu = "김포시";

        // Mock JSON 응답 설정
        when(responseEntity.getBody()).thenReturn(jsonNode);
        when(jsonNode.get("documents")).thenReturn(documents);
        when(documents.isArray()).thenReturn(true);
        when(documents.size()).thenReturn(1);

        JsonNode first = mock(JsonNode.class);
        when(first.get("region_1depth_name").asText()).thenReturn(expectedSido);
        when(first.get("region_2depth_name").asText()).thenReturn(expectedSigungu);
        when(documents.get(0)).thenReturn(first);

        // Mock RestTemplate의 응답을 mock
        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(JsonNode.class)
        )).thenReturn(responseEntity);

        // When: 메서드 호출
        UserLocationDTO result = emergencyClient.GetUserLocation(userLatitude, userLongitude);

        // Then: 반환된 값이 예상한 값과 일치하는지 확인
        assertNotNull(result);
        assertEquals(expectedSido, result.getSido());  // "경기도"가 반환되어야 함
        assertEquals(expectedSigungu, result.getSigungu()); // "김포시"가 반환되어야 함
}
}
