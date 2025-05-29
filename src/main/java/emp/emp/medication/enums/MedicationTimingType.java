package emp.emp.medication.enums;

import lombok.Getter;

@Getter
public enum MedicationTimingType {
  
  MORNING("아침"),
  LUNCH("점심"),
  EVENING("저녁");

  private final String description;

  MedicationTimingType(String description) {
    this.description = description;
  }
}
