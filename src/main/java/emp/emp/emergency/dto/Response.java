package emp.emp.emergency.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class EmergencyRoomXmlResponse {
    private EmergencyRoomXmlBody body;

    @XmlElement
    public EmergencyRoomXmlBody getBody() {
        return body;
    }

    public void setBody(EmergencyRoomXmlBody body) {
        this.body = body;
    }
}
