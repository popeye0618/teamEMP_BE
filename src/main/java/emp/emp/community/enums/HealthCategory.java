package emp.emp.community.enums;

public enum HealthCategory {
    NUTRITION("영양"),
    EXERCISE("운동"),
    MENTAL_HEALTH("정신 건강"),
    DISEASE_PREVENTION("질병 예방"),
    MEDICATION("약물"),
    SLEEP("수면"),
    STRESS("스트레스 관리"),
    GENERAL("일반");

    private final String displayName;

    HealthCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
