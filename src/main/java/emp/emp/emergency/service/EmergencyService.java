package emp.emp.emergency.service;

import emp.emp.emergency.client.EmergencyClient;
import emp.emp.emergency.dto.EmergencyAedDTO;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import emp.emp.emergency.dto.UserLocationDTO;
import emp.emp.util.api_response.Response;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.ArrayList;
import java.util.List;





// 사용자의 위도 경도를 받아 도 이름과 시 이름을 추출하고 받아온 데이터를 자바 객체(DTO)로 변환해서 컨트롤러에 넘긴다.
@Service
public class EmergencyService {
    private final EmergencyClient emergencyClient;

    public EmergencyService(EmergencyClient emergencyClient) {
        this.emergencyClient = emergencyClient;
    }

    public UserLocationDTO GetUserLocation(double userLatitude, double userLongitude) {
        String url = String.format(
                "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=%f&y=%f",
                userLatitude, userLongitude
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + "kakaoApiKey");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // HTTP 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        // 응답 파싱
        JsonNode documents = response.getBody().get("documents");

        String sigungu = "";
        String sido = "";
        if (documents.isArray() && documents.size() > 0) {
            JsonNode first = documents.get(0);
            sido = first.get("region_1depth_name").asText();
            sigungu = first.get("region_2depth_name").asText();
        }

        UserLocationDTO userLocationDTO = new UserLocationDTO(sido, sigungu);


        return userLocationDTO;
    }


    public List<EmergencyRoomDTO> getEmergencyRoomInformation(double userLatitude, double userLongitude) throws IOException, JAXBException {
        UserLocationDTO userLocationDTO = GetUserLocation(userLatitude, userLongitude); // 시군구 받기
        return emergencyClient.GetEmergencyRoomInformationApi(userLocationDTO);
        }

    }


//    public List<EmergencyRoomDTO> getEmergencyRoomInformation(String sido, String sigungu) {
//        return
//    }

//    public List<EmergencyAedDTO> getEmergencyAedInformation(double userLatitude, double userLongitude) {
//
//    }

