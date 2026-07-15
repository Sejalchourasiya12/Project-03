package in.co.rays.project_3.dto;

/**
 * AIRecommendationDTO - Data Transfer Object for AI Recommendation
 *
 * @author Sejal Chourasiya
 */
public class AIRecommendationDTO extends BaseDTO {

    private String recommendationCode;
    private String userName;
    private String recommendationType;
    private String status;

    public String getRecommendationCode() {
        return recommendationCode;
    }
    public void setRecommendationCode(String recommendationCode) {
        this.recommendationCode = recommendationCode;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecommendationType() {
        return recommendationType;
    }
    public void setRecommendationType(String recommendationType) {
        this.recommendationType = recommendationType;
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