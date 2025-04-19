package emp.emp.emergency.dto;

import jakarta.xml.bind.annotation.XmlElement;

public class EmergencyRoomXmlBody {
    private EmergencyRoomXmlItems items;

    @XmlElement
    public EmergencyRoomXmlItems getItems() {
        return items;
    }

    public void setItems(EmergencyRoomXmlItems items) {
        this.items = items;
    }
}
