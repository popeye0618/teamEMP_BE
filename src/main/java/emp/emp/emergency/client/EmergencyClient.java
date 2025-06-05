package emp.emp.emergency.client;

import com.fasterxml.jackson.databind.JsonNode;
import emp.emp.emergency.dto.EmergencyRoomDTO;
import emp.emp.emergency.dto.*;
import emp.emp.emergency.dto.UserLocationDTO;
import emp.emp.health.apiKey.ApiKeyProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.util.List;


// 서비스 계층에서 받아온 도와 시의 이름으로 xml 데이터를 다시  서비스 계층에 리턴한다
@Component
public class EmergencyClient {
    private final ApiKeyProvider apiKeyProvider;

    public EmergencyClient(ApiKeyProvider apiKeyProvider) {
        this.apiKeyProvider = apiKeyProvider;
    }

    // 병원의 이름으로 위도경도를 받아오기

    // 사용자의 도, 시를 받아오는 메서드

    // 병원의 정보를 받아오는 메서드
    public List<EmergencyRoomDTO> GetEmergencyRoomInformationApi(UserLocationDTO userLocationDTO) throws IOException {
        String sido = userLocationDTO.getSido();
        String sigungu = userLocationDTO.getSigungu();

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/ErmctInfoInqireService/getEmrrmRltmUsefulSckbdInfoInqire");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + apiKeyProvider.getPublicDataKey());
        urlBuilder.append("&" + URLEncoder.encode("STAGE1", "UTF-8") + "=" + URLEncoder.encode(sido, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("STAGE2", "UTF-8") + "=" + URLEncoder.encode(sigungu, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("999999", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
//        System.out.println(sb.toString());

        List<EmergencyRoomDTO> emergencyRooms = EmergencyRoomXmlParser.parse(sb.toString());

        // 반복문 역 지오코딩으로 설절
        for (EmergencyRoomDTO emergencyRoom : emergencyRooms) {
            GetEmergencyRoomLocation(emergencyRoom);
        }

        return emergencyRooms;
    }


    // 9katrQUAyXDMZdQbJbRGbsPcK5u9PHVP3uyhr5oRBWOhNVYpE2J8TDjxr4eo%2F8qSQzwaa6nxunRdVP14ILSK1A%3D%3D


    public List<EmergencyAedDTO> GetEmergencyAedInformationApi(UserLocationDTO userLocationDTO) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/AEDInfoInqireService/getEgytAedManageInfoInqire"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + apiKeyProvider.getPublicDataKey()); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode(userLocationDTO.getSido(), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("Q1", "UTF-8") + "=" + URLEncoder.encode(userLocationDTO.getSigungu(), "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        List<EmergencyAedDTO> emergencyAeds = EmergencyAedXmlParser.parse(sb.toString());


        return emergencyAeds;
    }



    // 사용자의 위도 경도로 시, 도를 조회하는 메서드
    public UserLocationDTO GetUserLocation(String userLatitude, String userLongitude) {

        String url = String.format(
                "https://dapi.kakao.com/v2/local/geo/coord2regioncode.json?x=%s&y=%s",
                userLongitude, userLatitude
        );

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKeyProvider.getKakaoMapKey());

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
            String full2depth = first.get("region_2depth_name").asText();

            // 공백 기준으로 분리해서 첫 토큰만 sigungu에 할당
            if (full2depth.contains(" ")) {
                sigungu = full2depth.substring(0, full2depth.indexOf(" "));
                // 또는 sigungu = full2depth.split(" ")[0];
            } else {
                sigungu = full2depth;
            }

            // 세종만 예외로ㅎㅎ
            if (sido.equals("세종특별자치시")) {
                sigungu = "세종특별자치시";
            }

        }

        UserLocationDTO userLocationDTO = UserLocationDTO.builder()
                .sido(sido)
                .sigungu(sigungu)
                .build();

        return userLocationDTO;
    }


    public EmergencyRoomDTO GetEmergencyRoomLocation(EmergencyRoomDTO emergencyRoom) {
        final String KAKAO_API_KEY = apiKeyProvider.getKakaoMapKey();
        try {
            String query = emergencyRoom.getHospitalName(); // 병원 이름
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + encodedQuery;

            URL url = new URL(apiURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "KakaoAK " + KAKAO_API_KEY);

// 디버깅용으로 응답 코드 찍어보자
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String response = sb.toString();

            // 응답 JSON에서 위도(lat), 경도(lng) 추출
            int xIndex = response.indexOf("\"x\":\"");
            int yIndex = response.indexOf("\"y\":\"");

            if (xIndex != -1 && yIndex != -1) {
                String xStr = response.substring(xIndex + 5, response.indexOf("\"", xIndex + 5));
                String yStr = response.substring(yIndex + 5, response.indexOf("\"", yIndex + 5));

                String longitude = xStr;
                String latitude = yStr;

                emergencyRoom.setHospitalLatitude(latitude);
                emergencyRoom.setHospitalLongitude(longitude);
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("역지오코딩 실패: " + e.getMessage());
            emergencyRoom.setHospitalLatitude("0.0");
            emergencyRoom.setHospitalLongitude("0.0");
        }

        System.out.println(emergencyRoom.getHospitalName());
        return emergencyRoom;
    }
}
