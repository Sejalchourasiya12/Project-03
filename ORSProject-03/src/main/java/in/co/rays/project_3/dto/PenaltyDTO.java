package in.co.rays.project_3.dto;

import java.math.BigDecimal;

public class PenaltyDTO extends BaseDTO {

    private Long id;
    private String penaltyCode;
    private String penaltyReason;
    private BigDecimal penaltyAmount;
    private String penaltyStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPenaltyCode() {
        return penaltyCode;
    }

    public void setPenaltyCode(String penaltyCode) {
        this.penaltyCode = penaltyCode;
    }

    public String getPenaltyReason() {
        return penaltyReason;
    }

    public void setPenaltyReason(String penaltyReason) {
        this.penaltyReason = penaltyReason;
    }

    public BigDecimal getPenaltyAmount() {
        return penaltyAmount;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) {
        this.penaltyAmount = penaltyAmount;
    }

    public String getPenaltyStatus() {
        return penaltyStatus;
    }

    public void setPenaltyStatus(String penaltyStatus) {
        this.penaltyStatus = penaltyStatus;
    }

    @Override
    public String getKey() {
        return id + "";
    }

    @Override
    public String getValue() {
        return penaltyCode;
    }
}