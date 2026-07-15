package in.co.rays.project_3.dto;

import java.util.Date;

/**
 * PolicyDTO - Data Transfer Object for Policy
 * 
 * @author Sejal Chourasiya
 */
public class PolicyDTO extends BaseDTO {

    private String policyCode;
    private String policyName;       // String rakha - naam always String hota hai
    private String premiumAmount;
    private String policyStatus;

    public String getPolicyCode() {
        return policyCode;
    }
    public void setPolicyCode(String policyCode) {
        this.policyCode = policyCode;
    }

    public String getPolicyName() {
        return policyName;
    }
    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPremiumAmount() {
        return premiumAmount;
    }
    public void setPremiumAmount(String premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public String getPolicyStatus() {
        return policyStatus;
    }
    public void setPolicyStatus(String policyStatus) {
        this.policyStatus = policyStatus;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return policyStatus;
    }
}