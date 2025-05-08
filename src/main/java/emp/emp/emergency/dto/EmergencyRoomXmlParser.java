package emp.emp.emergency.dto;

import java.util.ArrayList;
import java.util.List;

public class EmergencyRoomXmlParser {

    public static List<EmergencyRoomDTO> parse(String xml) {
        List<EmergencyRoomDTO> result = new ArrayList<>();

        String[] items = xml.split("<item>");
        for (int i = 1; i < items.length; i++) { // 첫 번째는 <response> 앞 부분이라 skip
            String itemXml = items[i];
            EmergencyRoomDTO dto = new EmergencyRoomDTO();

            dto.setHospitalName(getTagValue(itemXml, "dutyName"));
            dto.setHospitalTel(getTagValue(itemXml, "dutyTel3"));
            dto.setMriAvailable("Y".equals(getTagValue(itemXml, "hvmriayn")));
            dto.setCtAvailable("Y".equals(getTagValue(itemXml, "hvctayn")));

            String hvecStr = getTagValue(itemXml, "hvec");
            try {
                dto.setEmergencyBedCount(Integer.parseInt(hvecStr));
            } catch (NumberFormatException e) {
                dto.setEmergencyBedCount(0); // 값이 이상할 경우 0으로 설정
            }

            // hospitalLatitude, hospitalLongitude는 XML에 없으니 임시로 0.0
            dto.setHospitalLatitude("0.0");
            dto.setHospitalLongitude("0.0");

            result.add(dto);
        }

        return result;
    }

    private static String getTagValue(String xml, String tagName) {
        String openTag = "<" + tagName + ">";
        String closeTag = "</" + tagName + ">";
        int start = xml.indexOf(openTag);
        int end = xml.indexOf(closeTag);

        if (start != -1 && end != -1) {
            start += openTag.length();
            return xml.substring(start, end).trim();
        } else {
            return ""; // 태그가 없을 경우 빈 문자열 반환
        }
    }
}