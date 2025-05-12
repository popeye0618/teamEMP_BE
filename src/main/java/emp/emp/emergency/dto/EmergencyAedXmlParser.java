package emp.emp.emergency.dto;

import emp.emp.emergency.dto.EmergencyAedDTO;

import java.util.ArrayList;
import java.util.List;

public class EmergencyAedXmlParser {
    public static List<EmergencyAedDTO> parse(String xml) {
        List<EmergencyAedDTO> result = new ArrayList<>();

        // <body><items> 태그 안의 내용만 추출
        int bodyStart = xml.indexOf("<body>");
        int bodyEnd = xml.indexOf("</body>", bodyStart);

        if (bodyStart == -1 || bodyEnd == -1) {
            // body 태그가 없는 경우 처리
            return result;
        }

        String bodyContent = xml.substring(bodyStart, bodyEnd + 7);

        // <item> 태그 분리
        String[] items = bodyContent.split("<item>");
        for (int i = 1; i < items.length; i++) { // 첫 번째 항목은 분할 결과로 <items> 태그만 포함하므로 건너뜀
            String itemXml = items[i];
            EmergencyAedDTO dto = new EmergencyAedDTO();

            // 좌표
            dto.setAedLatitude(getTagValue(itemXml, "wgs84Lat"));
            dto.setAedLongitude(getTagValue(itemXml, "wgs84Lon"));

            // 설치 기관/장소/전화
            dto.setInstallationOrg(getTagValue(itemXml, "mfg"));           // 제조사
            dto.setBuildPlace(getTagValue(itemXml, "buildPlace"));         // 설치 위치
            dto.setTelNumber(getTagValue(itemXml, "clerkTel"));            // 담당자 전화
            dto.setManagerTelNumber(getTagValue(itemXml, "managerTel"));   // 관리자 전화

            // 요일별 운영 시간
            dto.setMondayStartDay(getTagValue(itemXml, "monSttTme"));
            dto.setMondayEndDay(getTagValue(itemXml, "monEndTme"));
            dto.setTuesdayStartDay(getTagValue(itemXml, "tueSttTme"));
            dto.setTuesdayEndDay(getTagValue(itemXml, "tueEndTme"));
            dto.setWednesdayStartDay(getTagValue(itemXml, "wedSttTme"));
            dto.setWednesdayEndDay(getTagValue(itemXml, "wedEndTme"));
            dto.setThursdayStartDay(getTagValue(itemXml, "thuSttTme"));
            dto.setThursdayEndDay(getTagValue(itemXml, "thuEndTme"));
            dto.setFridayStartDay(getTagValue(itemXml, "friSttTme"));
            dto.setFridayEndDay(getTagValue(itemXml, "friEndTme"));
            dto.setSaturdayStartDay(getTagValue(itemXml, "satSttTme"));
            dto.setSaturdayEndDay(getTagValue(itemXml, "satEndTme"));
            dto.setSundayStartDay(getTagValue(itemXml, "sunSttTme"));
            dto.setSundayEndDay(getTagValue(itemXml, "sunEndTme"));

            result.add(dto);
        }
        return result;
    }

    private static String getTagValue(String xml, String tagName) {
        String open = "<" + tagName + ">";
        String close = "</" + tagName + ">";
        int start = xml.indexOf(open);
        int end = xml.indexOf(close, start); // start 위치 이후부터 닫는 태그 검색

        if (start != -1 && end != -1) {
            return xml.substring(start + open.length(), end).trim();
        }
        return "";
    }
}