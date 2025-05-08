package emp.emp.emergency.dto;

import java.util.ArrayList;
import java.util.List;

import static org.hibernate.boot.model.process.spi.MetadataBuildingProcess.build;
public class EmergencyAedXmlParser {
    public static List<EmergencyAedDTO> parse(String xml) {
        List<EmergencyAedDTO> result = new ArrayList<>();

        String[] items = xml.split("<item>");
        for (int i = 1; i < items.length; i++) {
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

            // 요일별 운영 시간 (XML 축약 태그 그대로)
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
        int end = xml.indexOf(close);
        if (start != -1 && end != -1) {
            return xml.substring(start + open.length(), end).trim();
        }
        return "";
    }
}
