package emp.emp.emergency.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmergencyAedDTO {
    private String aedLatitude;
    private String aedLongitude;
    private String installationOrg;
    private String buildPlace;
    private String telNumber;
    private String managerTelNumber;
    private String mondayStartDay;
    private String mondayEndDay;
    private String tuesdayStartDay;
    private String tuesdayEndDay;
    private String wednesdayStartDay;
    private String wednesdayEndDay;
    private String thursdayStartDay;
    private String thursdayEndDay;
    private String fridayStartDay;
    private String fridayEndDay;
    private String saturdayStartDay;
    private String saturdayEndDay;
    private String sundayStartDay;
    private String sundayEndDay;


    @Override
    public String toString() {
        return "EmergencyAedDTO{" +
                "AedLatitude='" + aedLatitude + '\'' +
                ", AedLongitude='" + aedLongitude + '\'' +
                ", installationOrg='" + installationOrg + '\'' +
                ", buildPlace='" + buildPlace + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", managerTelNumber='" + managerTelNumber + '\'' +
                ", mondayStartDay='" + mondayStartDay + '\'' +
                ", mondayEndDay='" + mondayEndDay + '\'' +
                ", tuesdayStartDay='" + tuesdayStartDay + '\'' +
                ", tuesdayEndDay='" + tuesdayEndDay + '\'' +
                ", wednesdayStartDay='" + wednesdayStartDay + '\'' +
                ", wednesdayEndDay='" + wednesdayEndDay + '\'' +
                ", thursdayStartDay='" + thursdayStartDay + '\'' +
                ", thursdayEndDay='" + thursdayEndDay + '\'' +
                ", fridayStartDay='" + fridayStartDay + '\'' +
                ", fridayEndDay='" + fridayEndDay + '\'' +
                ", saturdayStartDay='" + saturdayStartDay + '\'' +
                ", saturdayEndDay='" + saturdayEndDay + '\'' +
                ", sundayStartDay='" + sundayStartDay + '\'' +
                ", sundayEndDay='" + sundayEndDay + '\'' +
                '}';
    }
}
