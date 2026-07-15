package in.co.rays.project_3.dto;

public class QueueDTO extends BaseDTO {

    private String queueCode;
    private String queueName;
    private Integer queueSize;
    private String queueStatus;

    public String getQueueCode() {
        return queueCode;
    }

    public void setQueueCode(String queueCode) {
        this.queueCode = queueCode;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public String getQueueStatus() {
        return queueStatus;
    }

    public void setQueueStatus(String queueStatus) {
        this.queueStatus = queueStatus;
    }

    @Override
    public String getKey() {
        return String.valueOf(getId());
    }

    @Override
    public String getValue() {
        return queueName;
    }
}
