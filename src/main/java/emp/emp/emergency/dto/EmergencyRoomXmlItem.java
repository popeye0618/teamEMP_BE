package emp.emp.emergency.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item")
public class EmergencyRoomXmlItem {

    private String dutyName;
    private String dutytel3;
    private String hvmriayn;
    private String hvctayn;
    private int hvec;

    @XmlElement
    public String getDutyName() { return dutyName; }
    public void setDutyName(String dutyName) { this.dutyName = dutyName; }

    @XmlElement
    public String getDutytel3() { return dutytel3; }
    public void setDutytel3(String dutytel3) { this.dutytel3 = dutytel3; }

    @XmlElement
    public String getHvmriayn() { return hvmriayn; }
    public void setHvmriayn(String hvmriayn) { this.hvmriayn = hvmriayn; }

    @XmlElement
    public String getHvctayn() { return hvctayn; }
    public void setHvctayn(String hvctayn) { this.hvctayn = hvctayn; }

    @XmlElement
    public int getHvec() { return hvec; }
    public void setHvec(int hvec) { this.hvec = hvec; }
}