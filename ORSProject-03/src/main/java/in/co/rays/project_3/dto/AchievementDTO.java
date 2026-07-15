package in.co.rays.project_3.dto;

public class AchievementDTO extends BaseDTO {

    private Long id;
    private String achievementCode;
    private String achievementName;
    private String earnedBy;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAchievementCode() {
        return achievementCode;
    }

    public void setAchievementCode(String achievementCode) {
        this.achievementCode = achievementCode;
    }

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public String getEarnedBy() {
        return earnedBy;
    }

    public void setEarnedBy(String earnedBy) {
        this.earnedBy = earnedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return status;
    }
}