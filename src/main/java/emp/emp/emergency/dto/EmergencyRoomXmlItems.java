package emp.emp.emergency.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "items")
public class EmergencyRoomXmlItems {
    private List<EmergencyRoomXmlItem> item;

    @XmlElement(name = "item")
    public List<EmergencyRoomXmlItem> getItem() {
        return item;
    }

    public void setItem(List<EmergencyRoomXmlItem> item) {
        this.item = item;
    }
}